package de.rytrox.bedwars.Listeners;

import de.rytrox.bedwars.utils.TeamChoosingItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPLayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().addItem(new TeamChoosingItem());
        player.sendMessage("Welcome!");
    }
}
