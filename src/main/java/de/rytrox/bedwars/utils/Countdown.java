package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

public class Countdown implements Listener {

    private BukkitTask task;
    private int minPlayerSize;
    private  final Bedwars main;
    private final int startValue;

    public Countdown(Bedwars main) {
        this.main = main;
        this.minPlayerSize = main.getConfig().getInt("countdown.minPlayers", 2);
        this.startValue = main.getConfig().getInt("countdown.startValue", 10);
    }

    @EventHandler
    public void onPlayerJoinListener(PlayerJoinEvent event) {
        int playerOnlineSize = Bukkit.getOnlinePlayers().size();
        if(playerOnlineSize >= minPlayerSize) {

            this.start();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if(Bukkit.getOnlinePlayers().size() < minPlayerSize) {

            task.cancel();
        }
    }

    private void start() {
        AtomicInteger timer = new AtomicInteger(startValue);
        task = Bukkit.getServer().getScheduler().runTaskTimer(main, () -> {

            int currentTime = timer.decrementAndGet();
            if(currentTime > 0) {
                Bukkit.getOnlinePlayers().forEach((player) ->
                    player.sendTitle(String.valueOf(currentTime), "SECONDS LEFT", 20, 20, 20)
                );
            } else {
                Bukkit.getOnlinePlayers().forEach((player) ->
                    player.sendTitle("START", "BEDWARS",20,20,20)
                );
                task.cancel();
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
