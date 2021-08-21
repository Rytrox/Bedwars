package de.rytrox.bedwars.Listeners;

import de.rytrox.bedwars.utils.TeamChoosingItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractsListener implements Listener {

    @EventHandler
    public void OnRightClickListener(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(new TeamChoosingItem().getItemMeta().getDisplayName())) {
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            }
        }
    }
}
