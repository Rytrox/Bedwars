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
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if(inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&9Shop | Rush"))
        || inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&9Shop | Blöcke"))
        || inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&9Shop | Rüstung"))
        || inventoryName.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&9Shop | Schwerter"))) {

            assert clickedItem != null;
            assert clickedItem.getItemMeta() != null;
            if(ChatColor.translateAlternateColorCodes('&', "&b&lRush").equalsIgnoreCase(clickedItem.getItemMeta().getDisplayName()))
                ShopUtils.openRush(player);
            if(ChatColor.translateAlternateColorCodes('&', "&b&lBlöcke").equalsIgnoreCase(clickedItem.getItemMeta().getDisplayName()))
                ShopUtils.openBlocks(player);
            if(ChatColor.translateAlternateColorCodes('&', "&b&lRüstung").equalsIgnoreCase(clickedItem.getItemMeta().getDisplayName()))
                ShopUtils.openArmor(player);
            if(ChatColor.translateAlternateColorCodes('&', "&b&lSchwerter").equalsIgnoreCase(clickedItem.getItemMeta().getDisplayName()))
                ShopUtils.openSwords(player);

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
            int money = Arrays.stream(playerItems)
                    .filter(Objects::nonNull)
                    .filter((itemStack) -> itemStack.getType().equals(materialMaterial))
                    .mapToInt(ItemStack::getAmount)
                    .sum();
            if(price > money) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fDir fehlen &9" + (price - money) + " " + materialMaterial + "&f!"));
                return;
            }
            if(buyAll) {
                if(money % price == 0) {
                    int amount = money / price;

                }
            } else {
                player.getInventory().removeItem(new ItemStack(materialMaterial, price));
                ItemStack itemStack = clickedItem.clone();
                ItemMeta itemMeta = itemStack.getItemMeta();
                assert itemMeta != null;
                itemMeta.setLore(null);
                itemStack.setItemMeta(itemMeta);
                player.getInventory().addItem(itemStack);
            }
        }
    }

    /*@EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if(event.isSneaking()) ShopUtils.summonShopVillager(event.getPlayer().getLocation());
    }*/
}
