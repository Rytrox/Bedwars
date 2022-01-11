package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Team;
import de.rytrox.bedwars.team.TeamManager;
import de.rytrox.bedwars.utils.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Arrays;
import java.util.Objects;

public class KillDeathListener implements Listener {

    private final Bedwars main;
    private final TeamManager teamManager;
    private final ScoreboardManager scoreboardManager;

    public KillDeathListener(Bedwars main, TeamManager teamManager, ScoreboardManager scoreboardManager) {
        this.main = main;
        this.teamManager = teamManager;
        this.scoreboardManager = scoreboardManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        scoreboardManager.updateBoard(player, 0, 1);

        Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> main.getStatistics().findByUUID(player.getUniqueId())
                .ifPresent(playerStatistic -> {
                    playerStatistic.setDeaths(playerStatistic.getDeaths() + 1);
                    main.getStatistics().savePlayerStatistic(playerStatistic);
                }));

        Player killer = player.getKiller();

        if (killer != null) {
            scoreboardManager.updateBoard(killer, 1, 0);
            Bukkit.getServer().getScheduler().runTaskAsynchronously(main,
                    () -> main.getStatistics().findByUUID(killer.getUniqueId())
                            .ifPresent(playerStatistic -> {
                                playerStatistic.setDeaths(playerStatistic.getDeaths() + 1);
                                main.getStatistics().savePlayerStatistic(playerStatistic);
                            }));
        }

        Team team = teamManager.getTeamByPlayer(player);
        if (team != null) {
            event.getDrops().clear();
            player.spigot().respawn();
            player.teleport(team.getSpawn().toBukkitLocation());
            player.setGameMode(GameMode.SPECTATOR);
            if (team.hasBed()) {
                event.setDeathMessage(team.getColor() + event.getDeathMessage());
                player.sendTitle(ChatColor.RED + "Du bist tot!", ChatColor.YELLOW + "Du respawnst in 5 Sekunden...", 20, 20, 20);
                Bukkit.getServer().getScheduler().runTaskLater(main, () -> {
                    player.teleport(team.getSpawn().toBukkitLocation());
                    player.setGameMode(GameMode.SURVIVAL);
                }, 20 * 5);
            } else {
                event.setDeathMessage(team.getColor() + event.getDeathMessage() + ChatColor.BOLD + " (FINAL KILL)");
                player.sendTitle(ChatColor.DARK_RED + "Du bist tot!", ChatColor.GOLD + "Du bist nun ein Zuschauer", 20, 20, 20);
                team.getMembers().remove(player);
                teamManager.checkForWin();
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (teamManager.getTeamByPlayer(player) == null) event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        Team playerTeam = teamManager.getTeamByPlayer(player);

        if (playerTeam == null) {
            event.setCancelled(true);
            return;
        }

        Player damager = getDamager(event);
        if (damager == null) return;
        Team damagerTeam = teamManager.getTeamByPlayer(damager);

        if (damagerTeam == null || playerTeam.equals(damagerTeam)) {
            event.setCancelled(true);
        }
    }

    private Player getDamager(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) return (Player) event.getDamager();
        else if (event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            if (projectile.getShooter() instanceof Player) return (Player) projectile.getShooter();
        }
        return null;
    }
}
