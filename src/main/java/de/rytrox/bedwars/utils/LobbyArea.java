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
        start = new Location(Bukkit.getWorld(main.getConfig().getString("lobby.world","world")),main.getConfig().getInt("lobby.pos1.x", 10), main.getConfig().getInt("lobby.pos1.y", 10) ,main.getConfig().getInt("lobby.pos1.z", 10));
        end = new Location(Bukkit.getWorld(main.getConfig().getString("lobby.world","world")),main.getConfig().getInt("lobby.pos2.x", -10), main.getConfig().getInt("lobby.pos2.y", 100) ,main.getConfig().getInt("lobby.pos2.z", -10));
        spawn = new Location(Bukkit.getWorld(main.getConfig().getString("lobby.world","world")),main.getConfig().getInt("lobby.spawn.x", 0), main.getConfig().getInt("lobby.spawn.y", 65) ,main.getConfig().getInt("lobby.spawn.z", 0));
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
}
