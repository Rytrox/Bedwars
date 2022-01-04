package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.database.entity.Map;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LobbyArea implements Listener {

    private Map map;

    public LobbyArea(Map map){
        this.map = map;
    }

    @EventHandler
    public void onPlayerInteracts(PlayerMoveEvent event){

        if(!new Area().inArea(map.getPos1().toBukkitLocation(),map.getPos2().toBukkitLocation(),event.getPlayer().getLocation())){
            event.getPlayer().teleport(new Location(event.getPlayer().getWorld(),0,50,0));
        }
    }
}
