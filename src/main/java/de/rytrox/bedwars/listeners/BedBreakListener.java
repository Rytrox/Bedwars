package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.database.entity.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
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
            Bed bed = (Bed) event.getBlock();
            Location location = bed.getPart() == Bed.Part.HEAD ?
                    (((Block) bed).getRelative(bed.getFacing())).getLocation() :
                    (((Block) bed).getRelative(bed.getFacing().getOppositeFace())).getLocation();
            map.getTeams()
                    .stream()
                    .filter(team1 -> team1.getBed().toBukkitLocation().equals(location))
                    .forEach(team1 -> {
                        team1.setHasBed(false);
                        Bukkit.getOnlinePlayers().forEach(player ->
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&fDas Bett von Team " + team1.getColor() + team1.getName() +
                                    " &fwurde von &b" + event.getPlayer().getName() + " zerstört!")));
                    });
        }
    }
}
