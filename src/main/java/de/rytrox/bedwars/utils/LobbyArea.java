package de.rytrox.bedwars.utils;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LobbyArea implements Listener {

    @EventHandler
    public void onPlayerInteracts(PlayerMoveEvent event){

        if(!new Area().inArea(new org.bukkit.Location(event.getPlayer().getWorld(),0,0,0),new org.bukkit.Location(event.getPlayer().getWorld(),100,100,100),event.getPlayer().getLocation())){
            event.getPlayer().teleport(new Location(event.getPlayer().getWorld(),0,50,0));
        }
    }
}
