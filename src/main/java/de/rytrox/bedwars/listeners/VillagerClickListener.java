package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.utils.ShopUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Objects;

public class VillagerClickListener implements Listener {

    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if(event.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) event.getRightClicked();
            if(Objects.requireNonNull(villager.getCustomName()).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&9&lShop"))) {
                ShopUtils.openRush(player);
            }
        }
    }
}
