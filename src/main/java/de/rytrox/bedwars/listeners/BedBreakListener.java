package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Collectors;

public class BedBreakListener implements Listener {

    private final Map map;
    private final Bedwars main = JavaPlugin.getPlugin(Bedwars.class);

    public BedBreakListener(Map map) {
        this.map = map;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getBlockData() instanceof Bed) {
            org.bukkit.material.Bed bed = (org.bukkit.material.Bed) event.getBlock();
            Location location;
            if (bed.isHeadOfBed()) location = (((Block) bed).getRelative(bed.getFacing())).getLocation();
            else location = (((Block) bed).getRelative(bed.getFacing().getOppositeFace())).getLocation();
            map.getTeams()
                    .stream()
                    .filter(team1 -> team1.getBed().toBukkitLocation() == location)
                    .collect(Collectors.toList())
                    .forEach(team1 -> {
                        main.getTeamManager().destroyBed(team1.getName());
                        Bukkit.getOnlinePlayers().forEach(player ->
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&fDas Bett von Team " + team1.getColor() + team1.getName() +
                                    " &fwurde von &b" + event.getPlayer().getName() + " zerstört!")));
                    });
        }
    }
}
