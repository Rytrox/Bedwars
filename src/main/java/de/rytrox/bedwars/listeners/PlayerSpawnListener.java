package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerSpawnListener implements Listener {

    private Bedwars main;

    public PlayerSpawnListener(Bedwars main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent event){
        Location spawn = new Location(
                Bukkit.getWorld(main.getConfig().getString("lobby.world")),
                main.getConfig().getDouble("lobby.spawn.x", 0),
                main.getConfig().getDouble("lobby.spawn.y", 65),
                main.getConfig().getDouble("lobby.spawn.z", 0)
        );
        spawn.setPitch(main.getConfig().getInt("lobby.spawn.pitch", 0));
        spawn.setYaw(main.getConfig().getInt("lobby.spawn.yaw", 0));
        event.getPlayer().teleport(spawn);
    }
}
