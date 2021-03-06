package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.database.entity.Team;
import de.rytrox.bedwars.team.TeamManager;
import de.rytrox.bedwars.utils.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.data.type.Bed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BedBreakListener implements Listener {

    private final Map map;
    private final TeamManager teamManager;
    private final ScoreboardManager scoreboardManager;

    public BedBreakListener(@NotNull Map map,
                            @NotNull TeamManager teamManager,
                            @NotNull ScoreboardManager scoreboardManager) {
        this.map = map;
        this.teamManager = teamManager;
        this.scoreboardManager = scoreboardManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getBlockData() instanceof Bed) {
            event.setDropItems(false);
            // Get Bed
            Bed bed = (Bed) event.getBlock().getBlockData();
            // Calculates both locations of bed
            Location destroyed = event.getBlock().getLocation();
            Location other = bed.getPart() != Bed.Part.HEAD ?
                    event.getBlock().getRelative(bed.getFacing()).getLocation() :
                    event.getBlock().getRelative(bed.getFacing().getOppositeFace()).getLocation();

            // find team based on both locations
            map.getTeams()
                    .stream()
                    .filter(team ->
                            team.getBed().toBukkitLocation().getBlock().getLocation().equals(destroyed) ||
                                    team.getBed().toBukkitLocation().getBlock().getLocation().equals(other))
                    .forEach(team -> {
                        // remove bed and send message to all players
                        if (Objects.equals(teamManager.getTeamByPlayer(event.getPlayer()), team)) {
                            event.setCancelled(true);
                            return;
                        }
                        team.setHasBed(false);
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            scoreboardManager.updateBoard(player, 0, 0);
                            Team playerTeam = teamManager.getTeamByPlayer(event.getPlayer());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&fDas Bett von Team " + team.getColor() + team.getName() +
                                            " &fwurde von " + playerTeam.getColor()
                                            + event.getPlayer().getName() + "&f zerst??rt!"));
                        });
                    });
        }
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        event.setCancelled(true);
    }
}
