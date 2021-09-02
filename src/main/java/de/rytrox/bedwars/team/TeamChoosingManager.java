package de.rytrox.bedwars.team;

import org.bukkit.Bukkit;
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

import java.util.ArrayList;
import java.util.List;

public class TeamChoosingManager implements Listener {

    private final Inventory inventory;
    private final List<Team> teams = new ArrayList<>();

    public TeamChoosingManager() {
        // Erstmal in Ordnung, muss sobald die Datenbank fertig ist von da geladen werden!
        teams.add(new Team("Red", Material.RED_WOOL, 5));
        teams.add(new Team("Blue", Material.BLUE_WOOL, 5));
        teams.add(new Team("Green", Material.GREEN_WOOL, 5));
        inventory = Bukkit.createInventory(null, 3 * 9);
        inventory.setItem(10, teams.get(0).getTeamItem());
        inventory.setItem(13, teams.get(1).getTeamItem());
        inventory.setItem(16, teams.get(2).getTeamItem());
    }

    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        if(event.getInventory().equals(inventory)) {
            Player player = (Player) event.getWhoClicked();
            removeFromAllTeams(player);
            if(getTeamByItem(event.getCurrentItem()).addMember(player)) {
                player.sendMessage(String.format("DU bIsT Im TeAm %s" , getTeamByItem(event.getCurrentItem()).getTeamName()));
            }
            event.setCancelled(true);
            player.closeInventory();
        }
    }

    @EventHandler
    public void OnRightClickListener(@NotNull PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().equals(new TeamChoosingItem())) {
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
        event.getPlayer().getInventory().addItem(new TeamChoosingItem());
    }

    public Inventory getInventory() {
        return inventory;
    }

    private Team getTeamByItem(ItemStack item) {
        return teams.stream()
                .filter((team) -> team.getTeamItem().equals(item))
                .findAny()
                .orElse(null);
    }

    private void removeFromAllTeams(Player player) {
        teams.forEach(team -> {
            team.removeMember(player);
        });
    }

}
