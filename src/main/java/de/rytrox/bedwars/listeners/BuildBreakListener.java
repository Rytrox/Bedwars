package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.utils.Area;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class BuildBreakListener implements Listener {

    private final Set<Block> placedBlocks = new HashSet<>();

    private final Location pos1;
    private final Location pos2;

    public BuildBreakListener(@NotNull Map map) {
        pos1 = new Location(Bukkit.getWorld("world1"),map.getPos1().getX(),map.getPos1().getY(),map.getPos1().getZ());
        pos2 = new Location(Bukkit.getWorld("world1"),map.getPos2().getX(),map.getPos2().getY(),map.getPos2().getZ());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (new Area().inArea(pos1, pos2,event.getBlock().getLocation()))
            placedBlocks.add(event.getBlockPlaced());
        else event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!new Area().inArea(pos1, pos2,event.getBlock().getLocation()))
            event.setCancelled(true);
    }
}
