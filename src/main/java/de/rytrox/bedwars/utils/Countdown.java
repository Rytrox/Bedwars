package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
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

    /**
     * Startet den Countdown, wenn ein Spieler den Server betritt und somit die
     * minimale Spieleranzahl zum Start überschreitet
     *
     * @param event The PlayerJoinEvent, that is used
     */
    @EventHandler
    public void onPlayerJoinListener(PlayerJoinEvent event) {
        int playerOnlineSize = Bukkit.getOnlinePlayers().size();
        if(playerOnlineSize >= minPlayerSize) {

            this.start();
        }
    }

    /**
     * Beendet den SpielTimer, wenn ein Spieler den server verlässt und somit die
     * minimal erlaubte Spieleranzahl nicht mehr erfüllt ist
     *
     * @param event The PlayerQuitEvent, that is used
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if(Bukkit.getOnlinePlayers().size() < minPlayerSize && task != null) {

            task.cancel();
        }
    }

    /**
     * Startet den Timer, der bis zum Start des Spieles runterzählt
     */
    private void start() {
        AtomicInteger timer = new AtomicInteger(startValue);
        task = Bukkit.getServer().getScheduler().runTaskTimer(main, () -> {

            int currentTime = timer.decrementAndGet();
            if(currentTime > 0) {
                Bukkit.getOnlinePlayers().forEach(player ->
                    player.sendTitle(main.getMessages().getTimerRunning(String.valueOf(currentTime))[0],
                            main.getMessages().getTimerRunning(String.valueOf(currentTime))[1], 20, 20, 20));
            } else {
                Bukkit.getOnlinePlayers().forEach(player ->
                    player.sendTitle(main.getMessages().getTimerEnd()[0], main.getMessages().getTimerEnd()[1],20,20,20)
                );
                task.cancel();
            }

        }, 100, 20L);
    }

    /**
     * Gibt die Minimal benötigte Spieleranzahl zum Spart eines Spieles zurück
     *
     * @return die Minimal benötigte Spieleranzahl
     */
    public int getMinPlayerSize() {
        return minPlayerSize;
    }

    /**
     * Setzt die Minimal benötigte Spieleranzahl zum Spart eines Spieles
     *
     * @param minPlayerSize die neue Minimal benötigte Spieleranzahl zum Spart eines Spieles
     */
    public void setMinPlayerSize(int minPlayerSize) {
        this.minPlayerSize = minPlayerSize;
    }
}
