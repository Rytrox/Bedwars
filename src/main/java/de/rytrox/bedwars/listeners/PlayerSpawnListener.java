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
        event.getPlayer().teleport(new Location(
                Bukkit.getWorld(main.getConfig().getString("lobby.world")),
                main.getConfig().getDouble("lobby.spawn.x"),
                main.getConfig().getDouble("lobby.spawn.y"),
                main.getConfig().getDouble("lobby.spawn.z")
        ));
    }
}
