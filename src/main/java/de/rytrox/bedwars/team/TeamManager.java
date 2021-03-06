package de.rytrox.bedwars.team;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.database.entity.Team;
import de.rytrox.bedwars.database.enums.TeamItem;
import de.timeout.libs.item.ItemStackBuilder;
import de.timeout.libs.item.ItemStacks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class TeamManager implements Listener {

    private final Bedwars main = JavaPlugin.getPlugin(Bedwars.class);

    private Map map;

    private final ItemStack teamChoosingItem = new ItemStackBuilder(Material.PAPER)
            .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4Teamauswahl"))
            .toItemStack();

    private Inventory genereateTeamChoosingInventory() {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, ChatColor.translateAlternateColorCodes('&', "&4Teamauswahl"));
        if (map != null) map.getTeams().forEach(team -> inventory.addItem(new ItemStackBuilder(TeamItem.findByChatColor(team.getColor()))
                    .setDisplayName(team.getColor() + team.getName())
                    .setAmount(Math.max(team.getMembers().size(), 1))
                    .writeNBTString("teamName", team.getName())
                    .toItemStack()));
        return inventory;
    }

    /**
     * Prüft, ob ein Spieler im TeamSelect Inventar ein Team ausgewählt hat
     * und fügt den Spieler diesem Team hinzu
     *
     * @param event The InventoryClickEvent, that is used
     */
    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        if (ChatColor.translateAlternateColorCodes('&', "&4Teamauswahl").equals(event.getView().getTitle())) {

            Player player = (Player) event.getWhoClicked();
            removeFromAllTeams(player);
            getTeamByItem(event.getCurrentItem()).ifPresent(team -> {
                event.setCancelled(true);

                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getMessages().getTeamSelected(team.getName())));
                team.addMember(player);
                player.closeInventory();
            });
        }
    }

    /**
     * Prüft, ob ein Spieler mit dem TeamSelectorItem Rechts klickt und
     * öffnet diesem Spieler das TeamSelector Inventar
     *
     * @param event The PlayerInteractEvent, that is used
     */
    @EventHandler
    public void onRightClickListener(@NotNull PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().equals(teamChoosingItem)) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                player.openInventory(genereateTeamChoosingInventory());
        }
    }

    /**
     * Prüft, ob ein Spieler das Spiel verlässt und entfernt ihn aus seinem Team
     *
     * @param event The PlayerQuitEvent, that is used
     */
    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        removeFromAllTeams(event.getPlayer());
    }

    /**
     * Prüft, ob ein Spieler das Spiel betritt und gibt ihm das TeamSelectorItem
     *
     * @param event The PlayerJoinEvent, that is used
     */
    @EventHandler
    public void onPLayerJoin(@NotNull PlayerJoinEvent event) {
        event.getPlayer().getInventory().clear();
        event.getPlayer().getInventory().setItem(4, teamChoosingItem);
        if (map == null) return;
        map.getTeams()
                .stream()
                .filter(team -> team.getMembers().isEmpty())
                .findAny()
                .ifPresentOrElse(team -> {
                    team.addMember(event.getPlayer());
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&fDu wurdest dem Team " + team.getColor() + team.getName() + " &fhinzugefügt!"));
                }, () -> {
                    List<Team> teams = new LinkedList<>(map.getTeams());
                    Collections.shuffle(teams);
                    teams
                            .stream()
                            .filter(team -> team.getMembers().size() < map.getTeamsize())
                            .min(Comparator.comparingInt(team -> team.getMembers().size()))
                            .ifPresentOrElse(team -> {
                                team.addMember(event.getPlayer());
                                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&fDu wurdest dem Team " + team.getColor() + team.getName() + " &fhinzugefügt!"));
                            }, () -> event.getPlayer().kickPlayer(ChatColor.RED + "Die Runde ist schon voll!"));
                });
    }

    /**
     * Sucht ein Team anhand dessen TeamItems zurück
     *
     * @param item das TeamItem
     * @return das Team
     */
    @NotNull
    private Optional<Team> getTeamByItem(@Nullable ItemStack item) {
        if (item == null) return Optional.empty();
        String name = ItemStacks.getNBTStringValue(item, "teamName");
        return map != null ? map.getTeams().stream()
                .filter(team -> team.getName().equals(name))
                .findAny() : Optional.empty();
    }

    @Nullable
    public Team getTeamByPlayer(Player player) {
        return map != null ? map.getTeams().stream()
                .filter(team -> team.getMembers().contains(player))
                .findAny().orElse(null) : null;
    }

    /**
     * Entfernt einen Spieler aus allen Teams
     *
     * @param player den zu entfernenden Spieler
     */
    private void removeFromAllTeams(@NotNull Player player) {
        if (map != null) map.getTeams().forEach(team -> team.removeMember(player));
    }

    public void checkForWin() {
        List<Team> livingTeams = map.getAliveTeams()
                .stream()
                .filter(team -> team.hasBed() || !team.getMembers().isEmpty())
                .collect(Collectors.toList());
        if (livingTeams.size() == 1) {
            Team winnerTeam = livingTeams.get(0);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                    "&f&lDas Team " + winnerTeam.getColor() + winnerTeam.getName() + " &f&lhat gewonnen!"));
            winnerTeam.getMembers().forEach(teamPlayer -> teamPlayer.sendTitle("Du hast gewonnen", "", 5, 20, 5));
            Bukkit.getServer().getScheduler().runTaskLater(main, () ->
                    main.getPhaseManager().next(), 20 * 10);
        }
    }

    /**
     * Gibt eine Liste aller Teams zurück
     *
     * @return die Liste aller Teams
     */
    @NotNull
    public List<Team> getAliveTeams() {
        return map != null ? new ArrayList<>(map.getAliveTeams()) : new ArrayList<>();
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
