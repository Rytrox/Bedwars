package de.rytrox.bedwars.team;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.database.entity.Team;
import de.rytrox.bedwars.database.enums.TeamItem;
import de.timeout.libs.item.ItemStackBuilder;
import de.timeout.libs.item.ItemStacks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
                    .setAmount(team.getMembers().size())
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

                player.sendMessage(main.getMessages().getTeamSelected(team.getName()));
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
        event.getPlayer().getInventory().addItem(teamChoosingItem);
        if (map == null) return;
        map.getTeams()
                .stream()
                .filter(team -> team.getMembers().isEmpty())
                .findAny()
                .ifPresentOrElse(team -> {
                    team.addMember(event.getPlayer());
                    event.getPlayer().sendMessage("Du wurdest dem Team " + team.getColor() + team.getName() + " hinzugefügt!");
                }, () -> map.getTeams()
                        .stream()
                        .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                            Collections.shuffle(collected);
                            return collected.stream();
                        }))
                        .findAny()
                        .ifPresentOrElse(team -> {
                            team.addMember(event.getPlayer());
                            event.getPlayer().sendMessage("Du wurdest dem Team " + team.getColor() + team.getName() + " hinzugefügt!");
                        }, () -> System.out.println("Kein Team gefunden")));
    }

    /**
     * Sucht ein Team anhand dessen TeamItems zurück
     *
     * @param item das TeamItem
     * @return das Team
     */
    @NotNull
    private Optional<Team> getTeamByItem(@Nullable ItemStack item) {
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

    /**
     * Gibt eine Liste aller Teams zurück
     *
     * @return die Liste aller Teams
     */
    @NotNull
    public List<Team> getTeams() {
        return map != null ? new ArrayList<>(map.getTeams()) : new ArrayList<>();
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
