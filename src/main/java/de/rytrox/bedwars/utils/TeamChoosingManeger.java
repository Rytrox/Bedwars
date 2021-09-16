package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.team.Team;
import de.timeout.libs.item.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamChoosingManeger implements Listener {

    private final Inventory inventory;
    private List<Team> teams = new ArrayList<>();
    private Bedwars bw;

    public TeamChoosingManeger(Bedwars bw) {
        this.bw = bw;
        teams.add(new Team("Red", Material.RED_WOOL, 5, ChatColor.RED));
        teams.add(new Team("Blue", Material.BLUE_WOOL, 5, ChatColor.BLUE));
        teams.add(new Team("Green", Material.GREEN_WOOL, 5, ChatColor.GREEN));
        teams.add(new Team("Yellow", Material.YELLOW_WOOL, 5, ChatColor.YELLOW));
        inventory = Bukkit.createInventory(null, 3 * 9);
        inventory.setItem(10, teams.get(0).getTeamItem());
        inventory.setItem(13, teams.get(1).getTeamItem());
        inventory.setItem(16, teams.get(2).getTeamItem());
        inventory.setItem(17, teams.get(3).getTeamItem());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getInventory().equals(inventory)) {
            Player player = (Player) event.getWhoClicked();
            removeFromAllTeams(player);
            getTeamByItem(event.getCurrentItem()).ifPresent(team -> {
                player.sendMessage(String.format("DU bIsT Im TeAm %s" , team.getTeamName()));
            });
            event.setCancelled(true);
            player.closeInventory();
        }
    }

    @EventHandler
    public void OnRightClickListener(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("team")) {
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

                player.openInventory(this.getInventory());
            }
        }
        if(player.getInventory().getItemInMainHand().getType() == Material.WOODEN_HOE) {
            bw.start(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        removeFromAllTeams(event.getPlayer());
    }

    @EventHandler
    public void onPLayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().addItem(
                new ItemStackBuilder(Material.PAPER)
                .setDisplayName("team")
                .toItemStack()
        );
    }

    public Inventory getInventory() {
        return inventory;
    }

    private Optional<Team> getTeamByItem(ItemStack item) {
        return this.teams.stream()
                .filter(team -> team.getTeamItem().equals(item))
                .findAny();
    }

    private void removeFromAllTeams(Player player) {
        teams.forEach(team -> {
            team.removeMember(player);
        });
    }

    public List getTeams() {
        return teams;
    }

}
