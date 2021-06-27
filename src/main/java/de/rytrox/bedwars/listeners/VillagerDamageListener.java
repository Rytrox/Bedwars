package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.utils.ShopUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Objects;

public class VillagerDamageListener implements Listener {

    @EventHandler
    public void onVillagerDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Villager) {
            Villager villager = (Villager) event.getEntity();
            if(Objects.requireNonNull(villager.getCustomName()).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&9&lShop"))) {
                event.setCancelled(true);
            }
        }
    }
}
