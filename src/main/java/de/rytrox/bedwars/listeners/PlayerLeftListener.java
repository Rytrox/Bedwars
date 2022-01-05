package de.rytrox.bedwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeftListener implements Listener {

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event){
        event.getPlayer().getInventory().clear();
    }
}
