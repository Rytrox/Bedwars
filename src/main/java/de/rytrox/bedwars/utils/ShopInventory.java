package de.rytrox.bedwars.utils;

import de.timeout.libs.item.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopInventory {

    public static void openRush(Player player) {
        Inventory inventory = createBaseInventory(ChatColor.translateAlternateColorCodes('&', "&9Shop : Rush"), 1);
        ItemStack stick = new ItemStackBuilder(Material.STICK)
                .addEnchantment(Enchantment.KNOCKBACK, 2)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &38 Bronze"))
                .toItemStack();
        ItemStack sandstone = new ItemStackBuilder(Material.SANDSTONE)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &32 Bronze"))
                .setAmount(4)
                .toItemStack();
        ItemStack pickaxe = new ItemStackBuilder(Material.WOODEN_PICKAXE)
                .addEnchantment(Enchantment.DIG_SPEED, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &35 Bronze"))
                .toItemStack();
        ItemStack head = new ItemStackBuilder(Material.LEATHER_HELMET)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &33 Bronze"))
                .toItemStack();
        ItemStack chest = new ItemStackBuilder(Material.LEATHER_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &33 Bronze"))
                .toItemStack();
        ItemStack leggings = new ItemStackBuilder(Material.LEATHER_LEGGINGS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &33 Bronze"))
                .toItemStack();
        ItemStack boots = new ItemStackBuilder(Material.LEATHER_BOOTS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &33 Bronze"))
                .toItemStack();
        inventory.setItem(19, stick);
        inventory.setItem(20, sandstone);
        inventory.setItem(21, pickaxe);
        inventory.setItem(22, head);
        inventory.setItem(23, chest);
        inventory.setItem(24, leggings);
        inventory.setItem(25, boots);
        player.openInventory(inventory);
    }

    private static Inventory createBaseInventory(String name, int tab) {
        // 1 = Rush, 2 = Blöcke, 3 = Rüstung, 4 = Schwerter, 5 = Werkzeuge, 6 = Tränke, 7 = Gadgets
        if(tab > 7 || tab < 1) return Bukkit.createInventory(null, 9*3);
        Inventory inventory = Bukkit.createInventory(null, 9*3, name);
        ItemStack none = new ItemStackBuilder(Material.WHITE_STAINED_GLASS_PANE)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f"))
                .toItemStack();
        ItemStack sellected = new ItemStackBuilder(Material.RED_STAINED_GLASS_PANE)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f"))
                .toItemStack();
        ItemStack rush = new ItemStackBuilder(Material.STICK)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lRush"))
                .toItemStack();
        ItemStack blocks = new ItemStackBuilder(Material.SANDSTONE)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lBlöcke"))
                .toItemStack();
        ItemStack armor = new ItemStackBuilder(Material.IRON_CHESTPLATE)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lRüstung"))
                .toItemStack();
        ItemStack swords = new ItemStackBuilder(Material.IRON_SWORD)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lSchwerter"))
                .toItemStack();
        ItemStack tools = new ItemStackBuilder(Material.IRON_PICKAXE)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lWerkzeuge"))
                .toItemStack();
        ItemStack potions = new ItemStackBuilder(Material.POTION)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lTränke"))
                .toItemStack();
        ItemStack gadgets = new ItemStackBuilder(Material.TNT)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lGadgets"))
                .toItemStack();
        for(int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, none);
        inventory.setItem(1, rush);
        inventory.setItem(2, blocks);
        inventory.setItem(3, armor);
        inventory.setItem(4, swords);
        inventory.setItem(5, tools);
        inventory.setItem(6, potions);
        inventory.setItem(7, gadgets);
        inventory.setItem(9 + tab, sellected);
        return inventory;
    }
}
