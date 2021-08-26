package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TeamChoosingManeger implements Listener {

    private final Inventory inventory;
    private List<Team> teams = new ArrayList<>();

    public TeamChoosingManeger() {
        teams.add(new Team("Red", Material.RED_WOOL, 5));
        teams.add(new Team("Blue", Material.BLUE_WOOL, 5));
        teams.add(new Team("Green", Material.GREEN_WOOL, 5));
        inventory = Bukkit.createInventory(null, 3 * 9);
        inventory.setItem(10, teams.get(0).getTeamItem());
        inventory.setItem(13, teams.get(1).getTeamItem());
        inventory.setItem(16, teams.get(2).getTeamItem());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
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
    public void onPlayerQuit(PlayerQuitEvent event) {
        removeFromAllTeams(event.getPlayer());
    }

    public Inventory getInventory() {
        return inventory;
    }

    private Team getTeamByItem(ItemStack item) {
        for (Team team : teams) {
            if(team.getTeamItem().equals(item)) {
                return team;
            }
        }
        return null;
    }

    private void removeFromAllTeams(Player player) {
        teams.forEach(team -> {
            team.removeMember(player);
        });
    }

}
