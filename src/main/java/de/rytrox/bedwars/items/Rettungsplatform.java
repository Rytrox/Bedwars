package de.rytrox.bedwars.items;

import de.rytrox.bedwars.Bedwars;
import de.timeout.libs.item.ItemStackBuilder;
import de.timeout.libs.item.ItemStacks;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Rettungsplatform implements Listener {

    private ItemStack rettungsplatform;
    private  final Bedwars main;

    public Rettungsplatform(Bedwars main) {
        this.main = main;
        rettungsplatform = new ItemStackBuilder(Material.BLAZE_ROD)
                .writeNBTBoolean("Rettungsplatform", true)
                .setDisplayName("Rettungsplatform")
                .toItemStack();
    }

    public ItemStack getRettungsplatform() {
        return rettungsplatform;
    }

    public boolean platform(Location playerLocation) {

        List<Location> platformBlocks = new ArrayList<>();
        if(playerLocation.getBlockY() > playerLocation.getWorld().getMinHeight() &&
                playerLocation.getBlockY() < playerLocation.getWorld().getMaxHeight()) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    Location currentBlock = playerLocation.clone().add(x, 0, z);
                    if (currentBlock.getBlock().getType() == Material.AIR) {
                        currentBlock.getBlock().setType(Material.PURPLE_STAINED_GLASS);
                        platformBlocks.add(currentBlock);
                    }
                }
            }
        }
        Bukkit.getServer().getScheduler().runTaskLater(main, () ->
                    platformBlocks.forEach(block -> block.getBlock().setType(Material.AIR)),
                100);
        return !platformBlocks.isEmpty();
    }

    @EventHandler
    public void onPlayerInteraction(PlayerInteractEvent event){
        Action action = event.getAction();
        if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            if (ItemStacks.hasNBTValue(player.getInventory().getItemInMainHand(), "Rettungsplatform")) {

                Location playerLocation = player.getLocation().add(0,-1,0);

                if(platform(playerLocation)){
                    player.getInventory().removeItem(rettungsplatform);
                }
            }
        }
    }
}