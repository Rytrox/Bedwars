package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.utils.ScoreboardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws ReflectiveOperationException {
        ScoreboardManager scoreboardManager = new ScoreboardManager(event.getPlayer());
    }
}
