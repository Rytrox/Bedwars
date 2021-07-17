package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.utils.ShopUtils;
import de.timeout.libs.item.ItemStackBuilder;
import de.timeout.libs.item.ItemStacks;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Objects;

public class ShopListener implements Listener {

    private final Bedwars main = JavaPlugin.getPlugin(Bedwars.class);

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
        if(inventoryName.startsWith(ChatColor.translateAlternateColorCodes('&', "&9Shop | "))) {

            // Cancels the event, so that the player can't take the item
            event.setCancelled(true);

            // Switches the inventory tab if the player clicks the right item
            if(clickedItem == null || clickedItem.getItemMeta() == null) return;
            switchIntevoryTab(player, clickedItem.getItemMeta().getDisplayName());

            // Gets if the price and the material of the item, that the player is going to buy and stores it
            if(!ItemStacks.hasNBTValue(clickedItem, "material") || !ItemStacks.hasNBTValue(clickedItem, "price")) return;
            material = ItemStacks.getNBTStringValue(clickedItem, "material");
            price = ItemStacks.getNBTIntValue(clickedItem, "price");
            event.setCancelled(true);
            if((materialMaterial = storeMaterial(material)) == null) return;

            // Gets the money (Number of material that is needed) from the player and stores it
            int money = Arrays.stream(playerItems)
                    .filter(Objects::nonNull)
                    .filter(itemStack -> itemStack.getType().equals(materialMaterial))
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

    // Opens the Shop inventory if the "Shop Villager" is clicked
    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if(event.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) event.getRightClicked();
            if(Objects.requireNonNull(villager.getCustomName()).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(main.getConfig().getString("villagerName"))))) {
                ShopUtils.openRush(player);
            }
        }
    }

    // Protects the "Shop Villager" from dying
    @EventHandler
    public void onVillagerDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            if(event.getEntity() instanceof Villager) {
                Villager villager = (Villager) event.getEntity();
                if(Objects.requireNonNull(villager.getCustomName()).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(main.getConfig().getString("villagerName"))))) {
                    event.setCancelled(true);
                }
            }
        }
    }

    private void switchIntevoryTab(Player player, String displayName) {
        if(ChatColor.translateAlternateColorCodes('&', "&b&lRush").equalsIgnoreCase(displayName))
            ShopUtils.openRush(player);
        if(ChatColor.translateAlternateColorCodes('&', "&b&lBlöcke").equalsIgnoreCase(displayName))
            ShopUtils.openBlocks(player);
        if(ChatColor.translateAlternateColorCodes('&', "&b&lRüstung").equalsIgnoreCase(displayName))
            ShopUtils.openArmor(player);
        if(ChatColor.translateAlternateColorCodes('&', "&b&lSchwerter").equalsIgnoreCase(displayName))
            ShopUtils.openSwords(player);
    }

    private Material storeMaterial(String material) {
        switch (material) {
            case "bronze":
                return Material.BRICK;
            case "iron":
                return Material.IRON_INGOT;
            case "gold":
                return Material.GOLD_INGOT;
            default:
                return null;
        }
    }
}
