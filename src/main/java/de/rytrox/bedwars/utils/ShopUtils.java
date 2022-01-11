package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.items.BedwarsTNT;
import de.rytrox.bedwars.items.Bridge;
import de.rytrox.bedwars.items.Rettungsplatform;
import de.timeout.libs.item.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ShopUtils {

    private static final Bedwars main = JavaPlugin.getPlugin(Bedwars.class);

    private ShopUtils() {}

    /**
     * opens the "Rush" inventory
     * @param player The player who opens the inventory
     */
    public static void openRush(Player player) {
        Inventory inventory = createBaseInventory(ChatColor.translateAlternateColorCodes('&', "&9Shop | Rush"), 1);
        ItemStack stick = new ItemStackBuilder(Material.STICK)
                .addEnchantment(Enchantment.KNOCKBACK, 2)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &38 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 8)
                .toItemStack();
        ItemStack sandstone = new ItemStackBuilder(Material.SANDSTONE)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &32 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 2)
                .setAmount(4)
                .toItemStack();
        ItemStack pickaxe = new ItemStackBuilder(Material.WOODEN_PICKAXE)
                .addEnchantment(Enchantment.DIG_SPEED, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &35 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 5)
                .toItemStack();
        ItemStack head = new ItemStackBuilder(Material.LEATHER_HELMET)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &33 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 3)
                .toItemStack();
        ItemStack chest = new ItemStackBuilder(Material.LEATHER_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &33 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 3)
                .toItemStack();
        ItemStack leggings = new ItemStackBuilder(Material.LEATHER_LEGGINGS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &33 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 3)
                .toItemStack();
        ItemStack boots = new ItemStackBuilder(Material.LEATHER_BOOTS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &33 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 3)
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

    /**
     * opens the "Blocks" inventory
     * @param player The player who opens the inventory
     */
    public static void openBlocks(Player player) {
        Inventory inventory = createBaseInventory(ChatColor.translateAlternateColorCodes('&', "&9Shop | Blöcke"), 2);
        ItemStack sandstone = new ItemStackBuilder(Material.SANDSTONE)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &32 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 2)
                .setAmount(4)
                .toItemStack();
        ItemStack wool = new ItemStackBuilder(Material.BLACK_WOOL)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &34 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 4)
                .setAmount(4)
                .toItemStack();
        ItemStack glass = new ItemStackBuilder(Material.GLASS)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &320 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 20)
                .setAmount(4)
                .toItemStack();
        ItemStack wood = new ItemStackBuilder(Material.OAK_PLANKS)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &f4 Eisen"))
                .writeNBTString("material", "iron")
                .writeNBTInt("price", 4)
                .setAmount(2)
                .toItemStack();
        ItemStack endstone = new ItemStackBuilder(Material.END_STONE)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &f6 Eisen"))
                .writeNBTString("material", "iron")
                .writeNBTInt("price", 6)
                .setAmount(2)
                .toItemStack();
        ItemStack emerald = new ItemStackBuilder(Material.EMERALD_BLOCK)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &61 Gold"))
                .writeNBTString("material", "gold")
                .writeNBTInt("price", 1)
                .setAmount(1)
                .toItemStack();
        ItemStack obsidian = new ItemStackBuilder(Material.OBSIDIAN)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &616 Gold"))
                .writeNBTString("material", "gold")
                .writeNBTInt("price", 16)
                .setAmount(4)
                .toItemStack();
        inventory.setItem(19, sandstone);
        inventory.setItem(20, wool);
        inventory.setItem(21, glass);
        inventory.setItem(22, wood);
        inventory.setItem(23, endstone);
        inventory.setItem(24, emerald);
        inventory.setItem(25, obsidian);
        player.openInventory(inventory);
    }

    /**
     * opens the "Armor" inventory
     * @param player The player who opens the inventory
     */
    public static void openArmor(Player player) {
        Inventory inventory = createBaseInventory(ChatColor.translateAlternateColorCodes('&', "&9Shop | Rüstung"), 3);
        ItemStack head = new ItemStackBuilder(Material.LEATHER_HELMET)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &33 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 3)
                .toItemStack();
        ItemStack chest = new ItemStackBuilder(Material.LEATHER_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &33 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 3)
                .toItemStack();
        ItemStack leggings = new ItemStackBuilder(Material.LEATHER_LEGGINGS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &33 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 3)
                .toItemStack();
        ItemStack boots = new ItemStackBuilder(Material.LEATHER_BOOTS)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &33 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 3)
                .toItemStack();
        ItemStack chest1 = new ItemStackBuilder(Material.CHAINMAIL_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &340 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 40)
                .toItemStack();
        ItemStack chest2 = new ItemStackBuilder(Material.IRON_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &f10 Eisen"))
                .writeNBTString("material", "iron")
                .writeNBTInt("price", 10)
                .toItemStack();
        ItemStack chest3 = new ItemStackBuilder(Material.DIAMOND_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &610 Gold"))
                .writeNBTString("material", "gold")
                .writeNBTInt("price", 10)
                .toItemStack();
        inventory.setItem(19, head);
        inventory.setItem(20, chest);
        inventory.setItem(21, leggings);
        inventory.setItem(22, boots);
        inventory.setItem(23, chest1);
        inventory.setItem(24, chest2);
        inventory.setItem(25, chest3);
        player.openInventory(inventory);
    }

    /**
     * opens the "Swords" inventory
     * @param player The player who opens the inventory
     */
    public static void openSwords(Player player) {
        Inventory inventory = createBaseInventory(ChatColor.translateAlternateColorCodes('&', "&9Shop | Schwerter"), 4);
        ItemStack wood = new ItemStackBuilder(Material.WOODEN_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &33 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 3)
                .toItemStack();
        ItemStack stone = new ItemStackBuilder(Material.STONE_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &320 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 20)
                .toItemStack();
        ItemStack gold = new ItemStackBuilder(Material.GOLDEN_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &330 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 30)
                .toItemStack();
        ItemStack gold1 = new ItemStackBuilder(Material.GOLDEN_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 2)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &f5 Eisen"))
                .writeNBTString("material", "iron")
                .writeNBTInt("price", 5)
                .toItemStack();
        ItemStack iron = new ItemStackBuilder(Material.IRON_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &f10 Eisen"))
                .writeNBTString("material", "iron")
                .writeNBTInt("price", 30)
                .toItemStack();
        ItemStack iron1 = new ItemStackBuilder(Material.IRON_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 2)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &f20 Eisen"))
                .writeNBTString("material", "iron")
                .writeNBTInt("price", 5)
                .toItemStack();
        ItemStack diamond = new ItemStackBuilder(Material.DIAMOND_SWORD)
                .addEnchantment(Enchantment.DAMAGE_ALL, 2)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &610 Gold"))
                .writeNBTString("material", "gold")
                .writeNBTInt("price", 5)
                .toItemStack();
        inventory.setItem(19, wood);
        inventory.setItem(20, stone);
        inventory.setItem(21, gold);
        inventory.setItem(22, gold1);
        inventory.setItem(23, iron);
        inventory.setItem(24, iron1);
        inventory.setItem(25, diamond);
        player.openInventory(inventory);
    }

    /**
     * opens the "Tools" inventory
     * @param player The player who opens the inventory
     */
    public static void openTools(Player player) {
        Inventory inventory = createBaseInventory(ChatColor.translateAlternateColorCodes('&', "&9Shop | Schwerter"), 5);
        ItemStack stonePickaxe = new ItemStackBuilder(Material.STONE_PICKAXE)
                .addEnchantment(Enchantment.DIG_SPEED, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &38 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 8)
                .toItemStack();
        ItemStack ironPickaxe = new ItemStackBuilder(Material.IRON_PICKAXE)
                .addEnchantment(Enchantment.DIG_SPEED, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &f6 Eisen"))
                .writeNBTString("material", "iron")
                .writeNBTInt("price", 6)
                .toItemStack();
        ItemStack diamondPickaxe = new ItemStackBuilder(Material.DIAMOND_PICKAXE)
                .addEnchantment(Enchantment.DIG_SPEED, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &65 Gold"))
                .writeNBTString("material", "gold")
                .writeNBTInt("price", 5)
                .toItemStack();
        ItemStack stoneAxe = new ItemStackBuilder(Material.STONE_AXE)
                .addEnchantment(Enchantment.DIG_SPEED, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &38 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 8)
                .toItemStack();
        ItemStack ironAxe = new ItemStackBuilder(Material.IRON_AXE)
                .addEnchantment(Enchantment.DIG_SPEED, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &f6 Eisen"))
                .writeNBTString("material", "iron")
                .writeNBTInt("price", 6)
                .toItemStack();
        ItemStack diamondAxe = new ItemStackBuilder(Material.DIAMOND_AXE)
                .addEnchantment(Enchantment.DIG_SPEED, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &65 Gold"))
                .writeNBTString("material", "gold")
                .writeNBTInt("price", 5)
                .toItemStack();
        ItemStack shears = new ItemStackBuilder(Material.SHEARS)
                .addEnchantment(Enchantment.DIG_SPEED, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &38 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 8)
                .toItemStack();
        inventory.setItem(19, stonePickaxe);
        inventory.setItem(20, ironPickaxe);
        inventory.setItem(21, diamondPickaxe);
        inventory.setItem(22, stoneAxe);
        inventory.setItem(23, ironAxe);
        inventory.setItem(24, diamondAxe);
        inventory.setItem(25, shears);
        player.openInventory(inventory);
    }

    /**
     * opens the "Swords" inventory
     * @param player The player who opens the inventory
     */
    public static void openPotions(Player player) {
        Inventory inventory = createBaseInventory(ChatColor.translateAlternateColorCodes('&', "&9Shop | Schwerter"), 6);
        ItemStack tnt = new ItemStackBuilder(BedwarsTNT.getBedwarsTNT())
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &f5 Eisen"))
                .writeNBTString("material", "iron")
                .writeNBTInt("price", 5)
                .toItemStack();
        ItemStack bridge = new ItemStackBuilder(Bridge.getBridge())
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &64 Gold"))
                .writeNBTString("material", "gold")
                .writeNBTInt("price", 20)
                .toItemStack();
        ItemStack rettungsplatform = new ItemStackBuilder(Rettungsplatform.getRettungsplatform())
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &64 Gold"))
                .writeNBTString("material", "gold")
                .writeNBTInt("price", 30)
                .toItemStack();
        inventory.setItem(19, tnt);
        inventory.setItem(20, bridge);
        inventory.setItem(21, rettungsplatform);
        player.openInventory(inventory);
    }

    /**
     * Basic structure of the inventory
     * @param name Name of the inventory
     * @param tab Tab number of the inventory
     * @return Returns the basic structure of the inventory
     */
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
        //inventory.setItem(6, potions);
        inventory.setItem(6, gadgets);
        inventory.setItem(9 + tab, sellected);
        return inventory;
    }

    /**
     * Summons the Shop Villager
     * @param location The location the Villager should appear
     */
    public static void summonShopVillager(@NotNull Location location) {
        Villager villager = (Villager) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.VILLAGER);
        villager.setCustomName(ChatColor.translateAlternateColorCodes('&', main.getMessages().getVillagerName()));
        villager.setCustomNameVisible(true);
        villager.setAI(false);
        villager.setSilent(true);
    }

    /**
     * Kills all "Shop Villagers" in a custom radius
     * @param location The location from where the "Shop Villagers" should get killed
     * @param radius The radius, in which the "Shop Villagers" should get killed
     */
    public static void killShopVillagers(@NotNull Location location, int radius) {
        Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, radius, radius, radius)
                .stream()
                .filter(Villager.class::isInstance)
                .filter(entity -> Objects.requireNonNull(entity.getCustomName()).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', main.getMessages().getVillagerName())))
                .forEach(entity -> ((Villager) entity).setHealth(0.0));
    }
}
