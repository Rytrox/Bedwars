package de.rytrox.bedwars.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Set;

public class MapReset {
    public MapReset(Set<Block> blocks){
        blocks.forEach(block -> block.setType(Material.AIR));
    }
}
