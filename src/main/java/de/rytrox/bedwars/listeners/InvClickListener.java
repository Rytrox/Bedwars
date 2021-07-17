package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.utils.ShopUtils;
import de.timeout.libs.item.ItemStackBuilder;
import de.timeout.libs.item.ItemStacks;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class InvClickListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String inventoryName = event.getView().getTitle();
        ItemStack clickedItem = event.getCurrentItem();
        boolean buyAll = event.isShiftClick();
        String material;
        Material materialMaterial;
        int price;
        ItemStack[] playerItems = event.getWhoClicked().getInventory().getContents();

        // Check if the inventory is one of the shop inventories
        if(inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&9Shop | Rush"))
        || inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&9Shop | Blöcke"))
        || inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&9Shop | Rüstung"))
        || inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&9Shop | Schwerter"))) {

            // Cancels the event, so that the player can't take the item
            event.setCancelled(true);

            // Switches the inventory tab if the player clicks the right item
            if(clickedItem == null || clickedItem.getItemMeta() == null) return;
            if(ChatColor.translateAlternateColorCodes('&', "&b&lRush").equalsIgnoreCase(clickedItem.getItemMeta().getDisplayName()))
                ShopUtils.openRush(player);
            if(ChatColor.translateAlternateColorCodes('&', "&b&lBlöcke").equalsIgnoreCase(clickedItem.getItemMeta().getDisplayName()))
                ShopUtils.openBlocks(player);
            if(ChatColor.translateAlternateColorCodes('&', "&b&lRüstung").equalsIgnoreCase(clickedItem.getItemMeta().getDisplayName()))
                ShopUtils.openArmor(player);
            if(ChatColor.translateAlternateColorCodes('&', "&b&lSchwerter").equalsIgnoreCase(clickedItem.getItemMeta().getDisplayName()))
                ShopUtils.openSwords(player);

            // Gets if the price and the material of the item, that the player is going to buy and stores it
            if(!ItemStacks.hasNBTValue(clickedItem, "material") || !ItemStacks.hasNBTValue(clickedItem, "price")) return;
            material = ItemStacks.getNBTStringValue(clickedItem, "material");
            price = ItemStacks.getNBTIntValue(clickedItem, "price");
            event.setCancelled(true);
            switch (material) {
                case "bronze":
                    materialMaterial = Material.BRICK;
                    break;
                case "iron":
                    materialMaterial = Material.IRON_INGOT;
                    break;
                case "gold":
                    materialMaterial = Material.GOLD_INGOT;
                    break;
                default:
                    return;
            }

            // Gets the money (Number of material that is needed) from the player and stores it
            int money = Arrays.stream(playerItems)
                    .filter(Objects::nonNull)
                    .filter((itemStack) -> itemStack.getType().equals(materialMaterial))
                    .mapToInt(ItemStack::getAmount)
                    .sum();

            // Checks if the Player has enough money
            if(price > money) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fDir fehlen &9" + (price - money) + " " + materialMaterial + "&f!"));
                return;
            }

            // If the items is unstackable or the maxStackSize is 1 the Players buy the offer just once
            if(!buyAll || clickedItem.getMaxStackSize() == 1) {
                player.getInventory().removeItem(new ItemStack(materialMaterial, price));
                ItemStack itemStack = new ItemStackBuilder(clickedItem)
                        .setLore(null)
                        .writeNBTString("material", "buy")
                        .toItemStack();
                player.getInventory().addItem(itemStack);
                return;
            }

            // The player buys the offer as often as possible
            int cmoney = money - (money % price);
            int amount = cmoney / price;
            player.getInventory().removeItem(new ItemStack(materialMaterial, price * amount));
            ItemStack itemStack = new ItemStackBuilder(clickedItem)
                    .setLore(null)
                    .writeNBTString("material", "buy")
                    .setAmount(clickedItem.getAmount() * amount)
                    .toItemStack();
            player.getInventory().addItem(itemStack);
        }
    }
}
