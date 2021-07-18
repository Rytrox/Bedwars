package de.rytrox.bedwars.listeners;

import static org.junit.jupiter.api.Assertions.assertEquals;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import de.rytrox.bedwars.Bedwars;
import de.timeout.libs.item.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopListenerTest {

    private ServerMock server;
    private Bedwars plugin;
    private ShopListener shopListener;
    private ItemStack exampleItem1;
    private ItemStack exampleItem2;
    private ItemStack exampleItem3;

    @BeforeEach
    public void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(Bedwars.class);
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

    @AfterEach
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    void testGetCurrency() {
        assertEquals(Material.BRICK, shopListener.getCurrency(exampleItem1));
        assertEquals(Material.IRON_INGOT, shopListener.getCurrency(exampleItem2));
        assertEquals(Material.GOLD_INGOT, shopListener.getCurrency(exampleItem3));
    }

    @Test
    void testGetPrice() {
        assertEquals(8, shopListener.getPrice(exampleItem1));
        assertEquals(4, shopListener.getPrice(exampleItem2));
        assertEquals(10, shopListener.getPrice(exampleItem3));
    }
}
