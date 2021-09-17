package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

public class Countdown implements Listener {

    private final AtomicInteger myInt = new AtomicInteger(4);
    private BukkitTask task;
    private int minPlayerSize = 1;

    @EventHandler
    public void onPlayerJoinListener(PlayerJoinEvent event) throws InterruptedException {
        int playerOnlineSize = Bukkit.getOnlinePlayers().size();
        if(playerOnlineSize > 1) {
            this.start();
        }
    }

    private void start() {
        AtomicInteger timer = new AtomicInteger(10);
        task = Bukkit.getServer().getScheduler().runTaskTimer(JavaPlugin.getPlugin(Bedwars.class), () -> {
            if(timer.get() >= 0) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if(timer.get() > 0) {
                        player.sendTitle("" + timer.get(), "SECONDS LEFT");
                    } else {
                        player.sendTitle("START","BEDWARS");
                    }
                }
                timer.set(timer.get() - 1);
            }
        }, 100, 20L);
    }

    public int getMinPlayerSize() {
        return minPlayerSize;
    }

    public void setMinPlayerSize(int minPlayerSize) {
        this.minPlayerSize = minPlayerSize;
    }
}
