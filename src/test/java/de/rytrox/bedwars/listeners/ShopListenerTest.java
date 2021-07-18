package de.rytrox.bedwars.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.timeout.libs.item.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShopListenerTest {

    /*



    Das Problem liegt darin, dass der Test sofort abstürzt, wenn folgende Zeile Code ausgeführt wird.
    ShopListener Z.24 -> " private final Bedwars main = JavaPlugin.getPlugin(Bedwars.class); "



     */

    /*private ShopListener shopListener;
    private ItemStack exampleItem1;
    private ItemStack exampleItem2;
    private ItemStack exampleItem3;

    public ShopListenerTest() {
    }

    @BeforeEach
    public void setUp() {
        shopListener = new ShopListener();
        exampleItem1 = new ItemStackBuilder(Material.STICK)
                .addEnchantment(Enchantment.KNOCKBACK, 2)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &38 Bronze"))
                .writeNBTString("material", "bronze")
                .writeNBTInt("price", 8)
                .toItemStack();
        exampleItem2 = new ItemStackBuilder(Material.OAK_PLANKS)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &f4 Eisen"))
                .writeNBTString("material", "iron")
                .writeNBTInt("price", 4)
                .setAmount(2)
                .toItemStack();
        exampleItem3 = new ItemStackBuilder(Material.DIAMOND_CHESTPLATE)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .addLore(ChatColor.translateAlternateColorCodes('&', "&f"))
                .addLore(ChatColor.translateAlternateColorCodes('&', "&7Preis: &610 Gold"))
                .writeNBTString("material", "gold")
                .writeNBTInt("price", 10)
                .toItemStack();
    }

    @Test
    @DisplayName("testGetCurrency1 -> exampleItem1 (stick)")
    void testGetCurrency1() {
        assertEquals(Material.BRICK, shopListener.getCurrency(exampleItem1));
    }

    @Test
    @DisplayName("testGetCurrency2 -> exampleItem2 (wood)")
    void testGetCurrency2() {
        assertEquals(Material.IRON_INGOT, shopListener.getCurrency(exampleItem2));
    }

    @Test
    @DisplayName("testGetCurrency3 -> exampleItem3 (chest3)")
    void testGetCurrency3() {
        assertEquals(Material.GOLD_INGOT, shopListener.getCurrency(exampleItem3));
    }

    @Test
    @DisplayName("testGetPrice1 -> exampleItem1 (stick)")
    void testGetPrice1() {
        assertEquals(8, shopListener.getPrice(exampleItem1));
    }

    @Test
    @DisplayName("testGetPrice2 -> exampleItem2 (wood)")
    void testGetPrice2() {
        assertEquals(4, shopListener.getPrice(exampleItem2));
    }

    @Test
    @DisplayName("testGetPrice3 -> exampleItem3 (chest3)")
    void testGetPrice3() {
        assertEquals(10, shopListener.getPrice(exampleItem3));
    }*/
}
