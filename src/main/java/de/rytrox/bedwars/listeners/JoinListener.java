package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.Bedwars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinListener implements Listener {

    private final Bedwars main = JavaPlugin.getPlugin(Bedwars.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        
    }
}
