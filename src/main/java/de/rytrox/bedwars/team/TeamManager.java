package de.rytrox.bedwars.team;

import de.rytrox.bedwars.Bedwars;
import de.timeout.libs.item.ItemStackBuilder;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TeamManager implements Listener {

    private final Bedwars main = JavaPlugin.getPlugin(Bedwars.class);

    private final Inventory inventory;
    private final List<Team> teams = new ArrayList<>();

    private final ItemStack teamChoosingItem = new ItemStackBuilder(Material.PAPER)
            .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4Teamauswahl"))
            .toItemStack();

    public TeamManager() {
        inventory = Bukkit.createInventory(null, 3 * 9);
    }

    /**
     * Prüft, ob ein Spieler im TeamSelect Inventar ein Team ausgewählt hat
     * und fügt den Spieler diesem Team hinzu
     *
     * @param event The InventoryClickEvent, that is used
     */
    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        if (event.getInventory().equals(inventory)) {

            Player player = (Player) event.getWhoClicked();
            removeFromAllTeams(player);
            getTeamByItem(event.getCurrentItem()).ifPresent(team -> {
                event.setCancelled(true);

                player.sendMessage(main.getMessages().getTeamSelected(team.getTeamName()));
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
                player.openInventory(this.getInventory());
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
    }

    /**
     * Gibt das TeamSelector Inventar zurück
     *
     * @return das TeamSelector Inventar
     */
    @NotNull
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sucht ein Team anhand dessen TeamItems zurück
     *
     * @param item das TeamItem
     * @return das Team
     */
    @NotNull
    private Optional<Team> getTeamByItem(@Nullable ItemStack item) {
        return teams.stream()
                .filter(team -> team.getTeamItem().equals(item))
                .findAny();
    }

    /**
     * Entfernt einen Spieler aus allen Teams
     *
     * @param player den zu entfernenden Spieler
     */
    private void removeFromAllTeams(@NotNull Player player) {
        teams.forEach(team -> team.removeMember(player));
    }

    public void destroyBed(@NotNull String teamName) {
        teams.stream()
                .filter(team -> teamName.equals(team.getTeamName()))
                .collect(Collectors.toList())
                .forEach(team -> team.setBed(false));
    }

    /**
     * Gibt eine Liste aller Teams zurück
     *
     * @return die Liste aller Teams
     */
    @NotNull
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }
}
