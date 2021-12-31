package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Team;
import de.rytrox.bedwars.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class KillDeathListener implements Listener {

    private final Bedwars main;
    private final TeamManager teamManager;

    public KillDeathListener(Bedwars main, TeamManager teamManager) {
        this.main = main;
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        EntityDamageEvent.DamageCause damageCause = Objects.requireNonNull(event.getEntity().getLastDamageCause()).getCause();

        Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> main.getStatistics().findByUUID(player.getUniqueId())
                .ifPresent(playerStatistic -> playerStatistic.setDeaths(playerStatistic.getDeaths() + 1)));

        if (damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            Player killer = player.getKiller();
            if (killer != null) Bukkit.getServer().getScheduler().runTaskAsynchronously(main,
                    () -> main.getStatistics().findByUUID(killer.getUniqueId())
                            .ifPresent(playerStatistic -> playerStatistic.setDeaths(playerStatistic.getDeaths() + 1)));
        }

        Team team = teamManager.getTeamByPlayer(player);
        if (team != null) event.setDeathMessage(team.getColor() + event.getDeathMessage());
    }
}
