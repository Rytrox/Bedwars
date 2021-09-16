package de.rytrox.bedwars.team;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamManager implements Listener {

    private final Inventory inventory;
    private final List<Team> teams = new ArrayList<>();

    private final ItemStack teamChoosingItem = new ItemStackBuilder(Material.PAPER)
            .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4Teamauswahl"))
            .toItemStack();

    public TeamManager() {
        inventory = Bukkit.createInventory(null, 3 * 9);
    }

    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        if(event.getInventory().equals(inventory)) {

            Player player = (Player) event.getWhoClicked();
            removeFromAllTeams(player);
            getTeamByItem(event.getCurrentItem()).ifPresent((team) -> {
                event.setCancelled(true);

                player.sendMessage(String.format("DU bIsT Im TeAm %s" , team.getTeamName()));
                team.addMember(player);
                player.closeInventory();
            });
        }
    }

    @EventHandler
    public void onRightClickListener(@NotNull PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().equals(teamChoosingItem)) {
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                player.openInventory(this.getInventory());
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        removeFromAllTeams(event.getPlayer());
    }

    @EventHandler
    public void onPLayerJoin(@NotNull PlayerJoinEvent event) {
        event.getPlayer().getInventory().addItem(teamChoosingItem);
    }

    @NotNull
    public Inventory getInventory() {
        return inventory;
    }

    @NotNull
    private Optional<Team> getTeamByItem(@Nullable ItemStack item) {
        return teams.stream()
                .filter((team) -> team.getTeamItem().equals(item))
                .findAny();
    }

    private void removeFromAllTeams(@NotNull Player player) {
        teams.forEach(team -> {
            team.removeMember(player);
        });
    }

    @NotNull
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }
}
