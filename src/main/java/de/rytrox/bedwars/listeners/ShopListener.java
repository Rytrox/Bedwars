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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class ShopListener implements Listener {

    private final Bedwars main;

    public ShopListener(Bedwars main) {
        this.main = main;
    }

    /**
     * Check if the inventory is one of the shop inventories
     * Cancels the event, so that the player can't take the item
     * Switches the inventory tab if the player clicks the right item
     * Gets if the price and the material of the item, that the player is going to buy and stores it
     * Removes the Money from the Inventory of the Player and adds the Items, which the Player bought
     * @param event The InventoryClickEvent, that is used
     */
    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        // Check if the inventory is one of the shop inventories
        if(event.getView().getTitle().startsWith(ChatColor.translateAlternateColorCodes('&', "&9Shop | "))) {

            // Cancels the event, so that the player can't take the item
            event.setCancelled(true);

            // Switches the inventory tab if the player clicks the right item
            if(clickedItem == null || clickedItem.getItemMeta() == null) return;
            switchInventoryTab(player, clickedItem.getItemMeta().getDisplayName());

            // Gets if the price and the material of the item, that the player is going to buy and stores it
            int price = getPrice(clickedItem);
            Material currency = getCurrency(clickedItem);
            if(price == -1 || currency == null) return;

            // Removes the Money from the Inventory of the Player and adds the Items, which the Player bought
            buy(player, price, currency, event.isShiftClick(), clickedItem);
        }
    }

    /**
     * Opens the Shop inventory if the "Shop Villager" is clicked
     * @param event The PlayerInteractEntityEvent, that is used
     */
    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if(event.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) event.getRightClicked();
            if(Objects.requireNonNull(villager.getCustomName()).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', main.getMessages().getVillagerName()))) {
                ShopUtils.openRush(player);
            }
        }
    }

    /**
     * Protects the "Shop Villager" from dying
     * @param event The EntityDamageByEntityEvent, that is used
     */
    @EventHandler
    public void onVillagerDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            if(event.getEntity() instanceof Villager) {
                Villager villager = (Villager) event.getEntity();
                if(Objects.requireNonNull(villager.getCustomName()).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', main.getMessages().getVillagerName()))) {
                    event.setCancelled(true);
                }
            }
        }
    }

    /**
     * Switches between the inventory Tabs of the Shop
     * @param player The Player, who wants the inventory tab switched
     * @param displayName The display name of the item the player clicked
     */
    private void switchInventoryTab(Player player, String displayName) {
        if(ChatColor.translateAlternateColorCodes('&', "&b&lRush").equalsIgnoreCase(displayName))
            ShopUtils.openRush(player);
        if(ChatColor.translateAlternateColorCodes('&', "&b&lBlöcke").equalsIgnoreCase(displayName))
            ShopUtils.openBlocks(player);
        if(ChatColor.translateAlternateColorCodes('&', "&b&lRüstung").equalsIgnoreCase(displayName))
            ShopUtils.openArmor(player);
        if(ChatColor.translateAlternateColorCodes('&', "&b&lSchwerter").equalsIgnoreCase(displayName))
            ShopUtils.openSwords(player);
    }

    /**
     * Get the currency of the Item, which the player wants to buy
     * @param item The item, that the player has clicked
     * @return Returns the currency of the Item, which the player wants to buy (Returns "null" if the item has no currency)
     */
    public Material getCurrency(ItemStack item) {
        if(!ItemStacks.hasNBTValue(item, "material")) return null;
        return storeCurrency(ItemStacks.getNBTStringValue(item, "material"));
    }

    /**
     * Get the price of the Item, which the player wants to buy
     * @param item The item, that the player has clicked
     * @return Returns the price of the Item, which the player wants to buy (Returns "-1" if the item has no price)
     */
    public int getPrice(ItemStack item) {
        if(!ItemStacks.hasNBTValue(item, "price")) return -1;
        return ItemStacks.getNBTIntValue(item, "price");
    }

    /**
     * Converts a material from a NBT-String to a material
     * @param currency The NBT-String of a material
     * @return Returns the material to the NBT-String (Returns "null", if the NBT-String has no material)
     */
    @Contract(pure = true)
    private @Nullable Material storeCurrency(@NotNull String currency) {
        switch (currency) {
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

    /**
     * Gets the money (Number of material that is needed) from the player and stores it
     * Checks if the Player has enough money
     * If the items is unstackable or the maxStackSize is 1 the Players buy the offer just once
     * Else the player buys the offer as often as possible
     * @param player The player, who is going to buy an item from the shop
     * @param price The price the player has to pay
     * @param currency The currency the player has to pay
     * @param buyAll Is true if the player is going to buy as much as possible
     * @param item The item the player is going to buy
     */
    private void buy(@NotNull Player player, int price, Material currency, boolean buyAll, ItemStack item) {

        // Gets the money (Number of material that is needed) from the player and stores it
        int money = Arrays.stream(player.getInventory().getContents())
                .filter(Objects::nonNull)
                .filter(itemStack -> itemStack.getType().equals(currency))
                .mapToInt(ItemStack::getAmount)
                .sum();

        // Checks if the Player has enough money
        if(price > money) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessages().getNotEnoughMoney((price - money), currency.toString())));
            return;
        }

        // If the items is unstackable or the maxStackSize is 1 the Players buy the offer just once
        if(!buyAll || item.getMaxStackSize() == 1) {
            transferItems(player, item, currency, price, 1);
            return;
        }

        // The player buys the offer as often as possible
        int cmoney = money - (money % price);
        int amount = cmoney / price;
        transferItems(player, item, currency, price, amount);
    }

    /**
     * Removes the Money from the Inventory of the Player and adds the Items, which the Player bought
     * @param player The player, who has bought the item
     * @param item The item, that the player has bought
     * @param currency The currency that the player has to pay
     * @param price The price that the player has to pay
     * @param amount The amount, how often the player has bought the item
     */
    private void transferItems(Player player, ItemStack item, Material currency, int price, int amount) {
        player.getInventory().removeItem(new ItemStack(currency, price * amount));
        ItemStack itemStack = new ItemStackBuilder(item)
                .setLore(null)
                .writeNBTString("material", "buy")
                .setAmount(item.getAmount() * amount)
                .toItemStack();
        player.getInventory().addItem(itemStack);
    }
}
