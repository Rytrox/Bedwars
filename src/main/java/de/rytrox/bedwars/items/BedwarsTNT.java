package de.rytrox.bedwars.items;

import de.timeout.libs.item.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

public class BedwarsTNT implements Listener {

    public static ItemStack getBedwarsTNT() {
        return new ItemStackBuilder(Material.TNT)
                .setDisplayName(ChatColor.RED +  "TSSS...BUMM (TNT)")
                .toItemStack();
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().clear();
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType().equals(Material.TNT)) {
            Location location = event.getBlockPlaced().getLocation();
            location.getBlock().setType(Material.AIR);
            location.getWorld().spawnEntity(location.add(0.5,0.5,0.5), EntityType.PRIMED_TNT);
        }
    }
}
