package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

public class LobbyArea {

    private Location spawn;
    private Location start;
    private Location end;
    private BukkitTask moveTask;

    public LobbyArea(Bedwars main) {
        moveTask = Bukkit.getScheduler().runTaskTimer(main, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (!Area.inArea(start, end, player.getLocation())) {
                    player.teleport(spawn);
                }
            });
        }, 0, 30);
    }

    public void stopMoveTask() {
        if (moveTask != null) {
            moveTask.cancel();
            moveTask = null;
        }
    }

    public void setLobbyLocations(Location spawn, Location start, Location end) {
        this.spawn = spawn;
        this.start = start;
        this.end = end;
    }
}
