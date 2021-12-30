package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.database.entity.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.stream.Collectors;

public class BedBreakListener implements Listener {

    private final Map map;

    public BedBreakListener(Map map) {
        this.map = map;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getBlockData() instanceof Bed) {
            Bed bed = (Bed) event.getBlock().getBlockData();
            if (checkBed(event.getBlock().getRelative(bed.getFacing()).getLocation(), event.getPlayer())) return;
            if (bed.getPart() == Bed.Part.HEAD) checkBed(event.getBlock().getRelative(bed.getFacing()).getLocation(), event.getPlayer());
            else checkBed(event.getBlock().getRelative(bed.getFacing().getOppositeFace()).getLocation(), event.getPlayer());
            /*map.getTeams()
                    .stream()
                    .filter(team1 -> team1.getBed().toBukkitLocation().getBlock().getLocation()
                            .equals(event.getBlock().getRelative(bed.getFacing()).getLocation()))
                    .forEach(team1 -> {
                        team1.setHasBed(false);
                        Bukkit.getOnlinePlayers().forEach(player ->
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&fDas Bett von Team " + team1.getColor() + team1.getName() +
                                                " &fwurde von &b" + event.getPlayer().getName() + " zerstört!")));
                    });
            checkBed(event.getBlock().getRelative(bed.getFacing()).getLocation(), event.getPlayer());
            if (bed.getPart() == Bed.Part.HEAD) {
                map.getTeams()
                        .stream()
                        .filter(team1 -> team1.getBed().toBukkitLocation().getBlock().getLocation()
                                .equals(event.getBlock().getRelative(bed.getFacing()).getLocation()))
                        .forEach(team1 -> {
                            team1.setHasBed(false);
                            Bukkit.getOnlinePlayers().forEach(player ->
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&fDas Bett von Team " + team1.getColor() + team1.getName() +
                                                    " &fwurde von &b" + event.getPlayer().getName() + " zerstört!")));
                        });
            } else {
                map.getTeams()
                        .stream()
                        .filter(team1 -> team1.getBed().toBukkitLocation().getBlock().getLocation()
                                .equals(event.getBlock().getRelative(bed.getFacing().getOppositeFace()).getLocation()))
                        .forEach(team1 -> {
                            team1.setHasBed(false);
                            Bukkit.getOnlinePlayers().forEach(player ->
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&fDas Bett von Team " + team1.getColor() + team1.getName() +
                                                    " &fwurde von &b" + event.getPlayer().getName() + " zerstört!")));
                        });
            }*/
            /*Location location = bed.getPart() == Bed.Part.HEAD ?
                    event.getBlock().getRelative(bed.getFacing()).getLocation() :
                    event.getBlock().getRelative(bed.getFacing().getOppositeFace()).getLocation();
            map.getTeams()
                    .stream()
                    .filter(team1 -> team1.getBed().toBukkitLocation().getBlock().getLocation().equals(location))
                    .forEach(team1 -> {
                        team1.setHasBed(false);
                        Bukkit.getOnlinePlayers().forEach(player ->
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&fDas Bett von Team " + team1.getColor() + team1.getName() +
                                    " &fwurde von &b" + event.getPlayer().getName() + " zerstört!")));
                    });*/
        }
    }

    private boolean checkBed(Location location, Player player) {
        List<Team> teams = map.getTeams()
                .stream()
                .filter(team1 -> team1.getBed().toBukkitLocation().getBlock().getLocation().equals(location))
                .collect(Collectors.toList());
        teams.forEach(team1 -> {
            team1.setHasBed(false);
            Bukkit.getOnlinePlayers().forEach(player1 ->
                    player1.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&fDas Bett von Team " + team1.getColor() + team1.getName() +
                                    " &fwurde von &b" + player.getName() + " zerstört!")));
        });
        return !teams.isEmpty();
    }
}
