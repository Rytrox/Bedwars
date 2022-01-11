package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerSpwanListener implements Listener {

    private Bedwars main;

    public PlayerSpwanListener(Bedwars main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent event){
        event.getPlayer().teleport(new Location(Bukkit.getWorld(main.getConfig().getString("lobby.world")), main.getConfig().getDouble("lobby.spawn.x"), main.getConfig().getDouble("lobby.spawn.Y"), main.getConfig().getDouble("lobby.spawn.z")));
    }
}
