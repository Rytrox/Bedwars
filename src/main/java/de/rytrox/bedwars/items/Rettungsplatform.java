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

    @EventHandler
    public void onPlayerInteraction(PlayerInteractEvent event){
        Action action = event.getAction();
        if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            if (ItemStacks.hasNBTValue(player.getInventory().getItemInMainHand(), "Rettungsplatform")) {
                boolean used = false;
                Location location = player.getLocation().add( 0, -2, 0);
                if (location.getBlock().getType() == Material.AIR) {
                    location.getBlock().setType(Material.PURPLE_STAINED_GLASS);
                    used = true;
                }
                if (location.add(1, 0, 1).getBlock().getType() == Material.AIR) {
                    location.getBlock().setType(Material.PURPLE_STAINED_GLASS);
                    used = true;
                }
                if (location.add(-1, 0, 0).getBlock().getType() == Material.AIR) {
                    location.getBlock().setType(Material.PURPLE_STAINED_GLASS);
                    used = true;
                }
                if (location.add(-1, 0, 0).getBlock().getType() == Material.AIR) {
                    location.getBlock().setType(Material.PURPLE_STAINED_GLASS);
                    used = true;
                }
                if (location.add(0, 0, -1).getBlock().getType() == Material.AIR) {
                    location.getBlock().setType(Material.PURPLE_STAINED_GLASS);
                    used = true;
                }
                if (location.add(2, 0, 0).getBlock().getType() == Material.AIR) {
                    location.getBlock().setType(Material.PURPLE_STAINED_GLASS);
                    used = true;
                }
                if (location.add(0, 0, -1).getBlock().getType() == Material.AIR) {
                    location.getBlock().setType(Material.PURPLE_STAINED_GLASS);
                    used = true;
                }
                if (location.add(-1, 0, 0).getBlock().getType() == Material.AIR) {
                    location.getBlock().setType(Material.PURPLE_STAINED_GLASS);
                    used = true;
                }
                if (location.add(-1, 0, 0).getBlock().getType() == Material.AIR) {
                    location.getBlock().setType(Material.PURPLE_STAINED_GLASS);
                    used = true;
                }
                location.add(1, 0, 1);
                if(used) {
                    player.getInventory().removeItem(rettungsplatform);
                    Bukkit.getServer().getScheduler().runTaskLater(main, () -> {

                        if (location.getBlock().getType() == Material.PURPLE_STAINED_GLASS) {
                            location.getBlock().setType(Material.AIR);
                        }
                        if (location.add(1, 0, 1).getBlock().getType() == Material.PURPLE_STAINED_GLASS) {
                            location.getBlock().setType(Material.AIR);
                        }
                        if (location.add(-1, 0, 0).getBlock().getType() == Material.PURPLE_STAINED_GLASS) {
                            location.getBlock().setType(Material.AIR);
                        }
                        if (location.add(-1, 0, 0).getBlock().getType() == Material.PURPLE_STAINED_GLASS) {
                            location.getBlock().setType(Material.AIR);
                        }
                        if (location.add(0, 0, -1).getBlock().getType() == Material.PURPLE_STAINED_GLASS) {
                            location.getBlock().setType(Material.AIR);
                        }
                        if (location.add(2, 0, 0).getBlock().getType() == Material.PURPLE_STAINED_GLASS) {
                            location.getBlock().setType(Material.AIR);
                        }
                        if (location.add(0, 0, -1).getBlock().getType() == Material.PURPLE_STAINED_GLASS) {
                            location.getBlock().setType(Material.AIR);
                        }
                        if (location.add(-1, 0, 0).getBlock().getType() == Material.PURPLE_STAINED_GLASS) {
                            location.getBlock().setType(Material.AIR);
                        }
                        if (location.add(-1, 0, 0).getBlock().getType() == Material.PURPLE_STAINED_GLASS) {
                            location.getBlock().setType(Material.AIR);
                        }
                    }, 100);

                }
            }
        }
    }
}