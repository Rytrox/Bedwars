package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.database.entity.Map;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class BuildBreakListener implements Listener {

    private final Set<Block> placedBlocks = new HashSet<>();

    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;
    private final double minZ;
    private final double maxZ;

    public BuildBreakListener(@NotNull Map map) {
        minX = Math.min(map.getPos1().getX(), map.getPos2().getX());
        maxX = Math.max(map.getPos1().getX(), map.getPos2().getX());
        minY = Math.min(map.getPos1().getY(), map.getPos2().getY());
        maxY = Math.max(map.getPos1().getY(), map.getPos2().getY());
        minZ = Math.min(map.getPos1().getZ(), map.getPos2().getZ());
        maxZ = Math.max(map.getPos1().getZ(), map.getPos2().getZ());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getBlockData() instanceof Bed) return;
        if (isInsideMap(event.getBlockPlaced()))
            placedBlocks.add(event.getBlockPlaced());
        else event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!isInsideMap(event.getBlock()) || !placedBlocks.contains(event.getBlock())
                && !(event.getBlock().getBlockData() instanceof Bed))
            event.setCancelled(true);
    }

    private boolean isInsideMap(Block block) {
        return block.getX() < maxX && block.getX() > minX && block.getY() < maxY &&
                block.getY() > minY && block.getZ() < maxZ && block.getZ() > minZ;
    }
}
