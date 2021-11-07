package de.rytrox.bedwars.map;

import de.rytrox.bedwars.database.entity.Map;
import de.timeout.libs.item.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MapUtils implements Listener {

    private final java.util.Map<String, Map> mapsInEdit = new HashMap<>();

    /**
     * Erstellt eine Map, wenn nötig und gibt diese zurück
     * Ansonsten wird die vorhandene Map zurückgegeben
     *
     * @param name der name der Map, die gesucht wird oder erstellt werden soll
     * @return die gefundene oder erstellte Map
     */
    public Map getOrCreateMap(String name) {
        return mapsInEdit.computeIfAbsent(name, Map::new);
    }

    /**
     * Gibt die Namen alles Maps im EditMode aus
     *
     * @return die Namen aller Maps im EditMode
     */
    public List<String> getMapNames() {
        return new ArrayList<>(mapsInEdit.keySet());
    }

    /**
     * Gibt die HashMaps mit allen Maps im EditMode zurück
     *
     * @return die HashMaps mit allen Maps im EditMode
     */
    public java.util.Map<String, Map> getMapsInEdit() {
        return mapsInEdit;
    }

    /**
     * löscht eine Map aus dem EditMode
     *
     * @param name die zu löschende Map
     */
    public void deleteMap(String name) {
        mapsInEdit.remove(name);
    }

    public void showCheckInventory(Player player, Map map) {
        Inventory inventory;

        if (map.checkComplete()) inventory = Bukkit
                .createInventory(player, 9*6, ChatColor.translateAlternateColorCodes('&', "&aMap - " + map.getName() + " (Complete)"));
        else  inventory = Bukkit
                .createInventory(player, 9*6, ChatColor.translateAlternateColorCodes('&', "&cMap - " + map.getName() + " (Incomplete)"));

        ItemStack mapItem = new ItemStackBuilder(Material.BEACON)
                .setAmount(1)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bMap: " + map.getName()))
                .setLore(Arrays.asList("Enought Teams: " + (map.getTeams().size() < 2 ? "False" : "True"),
                        "Enought Spawner: " + (map.getSpawner().isEmpty() ? "False" : "True")))
                .toItemStack();
        ItemStack teamSize = new ItemStackBuilder(Material.BEACON)
                .setAmount(1)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bTeamsize: " + map.getTeamsize()))
                .toItemStack();
        ItemStack pos1 = new ItemStackBuilder(Material.ARMOR_STAND)
                .setAmount(1)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bPos1: " + map.getPos1()))
                .toItemStack();
        ItemStack pos2 = new ItemStackBuilder(Material.ARMOR_STAND)
                .setAmount(1)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bPos2: " + map.getPos2()))
                .toItemStack();

        inventory.addItem(mapItem);
        inventory.addItem(teamSize);
        inventory.addItem(pos1);
        inventory.addItem(pos2);

        if (map.getTeams() != null) map.getTeams().forEach(team -> {
            ItemStack teamItem = new ItemStackBuilder(Material.RED_BED)
                    .setAmount(1)
                    .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bTeam: " + team.getName()))
                    .setLore(Arrays.asList("Color: " + team.getColor().getChar(), "Villager: " + team.getVillager(),
                            "Spawn: " + team.getSpawn(), "Bed: " + team.getBed()))
                    .toItemStack();
            inventory.addItem(teamItem);
        });

        if (map.getSpawner() != null) map.getSpawner().forEach(spawner -> {
            ItemStack spawnerItem = new ItemStackBuilder(Material.SPAWNER)
                    .setAmount(1)
                    .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bSpawner: " + spawner.getId()))
                    .setLore(Arrays.asList("Material: " + spawner.getMaterial(), "Location: " + spawner.getLocation()))
                    .toItemStack();
            inventory.addItem(spawnerItem);
        });

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        mapsInEdit.values().forEach(map -> {
            if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&aMap - " + map.getName() + " (Complete)"))
            || event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&cMap - " + map.getName() + " (Incomplete)")))
                event.setCancelled(true);
        });
    }
}