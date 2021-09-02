package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.Bedwars;
import de.timeout.libs.item.ItemStacks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor({ "de.timeout.libs.item.ItemStacks" })
public class ShopListenerTest {

    private de.rytrox.bedwars.listeners.ShopListener shopListener;

    @Mock
    private Bedwars main;

    @Mock
    private ItemStack exampleItem;

    @Before
    public void setUp() {
        shopListener = new de.rytrox.bedwars.listeners.ShopListener(main);
    }

    @Test
    public void testGetCurrency() {
        try(MockedStatic<ItemStacks> mockedStatic = Mockito.mockStatic(ItemStacks.class)) {
            mockedStatic.when(() -> ItemStacks.hasNBTValue(exampleItem, "material"))
                    .thenAnswer(invocation -> true);
            mockedStatic.when(() -> ItemStacks.getNBTStringValue(exampleItem, "material"))
                    .thenAnswer(invocation -> "bronze");

            Material material = shopListener.getCurrency(exampleItem);
            assertEquals(Material.BRICK, material);
        }
    }

    @Test
    public void testGetPrice() {
        try(MockedStatic<ItemStacks> mockedStatic = Mockito.mockStatic(ItemStacks.class)) {
            mockedStatic.when(() -> ItemStacks.hasNBTValue(exampleItem, "price"))
                    .thenAnswer(invocation -> true);
            mockedStatic.when(() -> ItemStacks.getNBTIntValue(exampleItem, "price"))
                    .thenAnswer(invocation -> 5);

            int price = shopListener.getPrice(exampleItem);
            assertEquals(5, price);
        }
    }
}
