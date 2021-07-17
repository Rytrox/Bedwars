package de.rytrox.bedwars.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Objects;

public class VillagerDamageListener implements Listener {

    // Protects the "Shop Villager" from dying
    @EventHandler
    public void onVillagerDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            if(event.getEntity() instanceof Villager) {
                Villager villager = (Villager) event.getEntity();
                if(Objects.requireNonNull(villager.getCustomName()).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&9&lShop"))) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
