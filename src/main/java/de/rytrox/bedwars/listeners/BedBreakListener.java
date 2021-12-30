package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.database.entity.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.data.type.Bed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BedBreakListener implements Listener {

    private final Map map;

    public BedBreakListener(Map map) {
        this.map = map;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getBlockData() instanceof Bed) {
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
                        team.setHasBed(false);
                        Bukkit.getOnlinePlayers().forEach(player ->
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&fDas Bett von Team " + team.getColor() + team.getName() +
                                                " &fwurde von &b" + event.getPlayer().getName() + " zerstört!")));
                    });
        }
    }
}
