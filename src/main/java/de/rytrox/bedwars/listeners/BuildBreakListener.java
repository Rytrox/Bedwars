package de.rytrox.bedwars.listeners;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.phase.phases.IngamePhase;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class BuildBreakListener implements Listener {

    private final Bedwars main = JavaPlugin.getPlugin(Bedwars.class);
    private final Set<Block> placedBlocks = new HashSet<>();
    private IngamePhase ingamePhase;
    private Map map;

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public BuildBreakListener() {
        setValues();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        setValues();
        Block placedBlock = event.getBlockPlaced();
        if (placedBlock.getX() < maxX && placedBlock.getX() > minX && placedBlock.getY() < maxY && placedBlock.getY() > minY)
            placedBlocks.add(event.getBlockPlaced());
        else event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        setValues();
        if (event.getBlock().getX() < maxX && event.getBlock().getX() > minX && event.getBlock().getY() < maxY && event.getBlock().getY() > minY)
            if (!placedBlocks.contains(event.getBlock())) event.setCancelled(true);
        else event.setCancelled(true);
    }

    private void setValues() {
        if (ingamePhase == null || map == null) {
            if (main.getPhaseManager().getCurrentPhase() instanceof IngamePhase) {
                ingamePhase = (IngamePhase) main.getPhaseManager().getCurrentPhase();
                map = ingamePhase.getMap();
                if (map.getPos1().toBukkitLocation().getX() <= map.getPos2().toBukkitLocation().getX()) {
                    minX = map.getPos1().toBukkitLocation().getX();
                    maxX = map.getPos2().toBukkitLocation().getX();
                } else {
                    minX = map.getPos2().toBukkitLocation().getX();
                    maxX = map.getPos1().toBukkitLocation().getX();
                }
                if (map.getPos1().toBukkitLocation().getY() <= map.getPos2().toBukkitLocation().getY()) {
                    minY = map.getPos1().toBukkitLocation().getY();
                    maxY = map.getPos2().toBukkitLocation().getY();
                } else {
                    minY = map.getPos2().toBukkitLocation().getY();
                    maxY = map.getPos1().toBukkitLocation().getY();
                }
            }
        }
    }
}
