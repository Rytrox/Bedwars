package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.database.entity.Map;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.security.PublicKey;

public class LobbyArea implements Listener {

    private Map map;
    private Location spawn;
    private Location start;
    private Location end;


    @EventHandler
    public void onPlayerInteracts(PlayerMoveEvent event){
        if(!new Area().inArea(start,end,event.getPlayer().getLocation())){
            event.getPlayer().teleport(spawn);
        }
    }

    public void setmap(Map map){
        this.map = map;
    }

    public void setLobbyLocations(Location spawn, Location start, Location end){
        this.spawn = spawn;
        this.start = start;
        this.end = end;
    }
}
