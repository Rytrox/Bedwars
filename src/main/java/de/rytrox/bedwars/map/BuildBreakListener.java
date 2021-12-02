package de.rytrox.bedwars.map;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.LinkedList;
import java.util.List;

public class BuildBreakListener implements Listener {

    private List<Block> placedBlocks = new LinkedList<>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!placedBlocks.contains(event.getBlockPlaced())) placedBlocks.add(event.getBlockPlaced());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!placedBlocks.contains(event.getBlock())) event.setCancelled(true);
    }
}
