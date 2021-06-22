package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.utils.ShopInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class InvClickListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {

    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if(event.isSneaking()) ShopInventory.openRush(event.getPlayer());
    }
}
