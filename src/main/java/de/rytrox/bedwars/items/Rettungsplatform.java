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
        int x = -1;
        int y = -1;
        boolean used = false;
        Location location = playerLocation;
        location.add(-1,0,-1);
            while (y < 2) {
                if (location.getBlock().getType() == Material.AIR) {
                    location.getBlock().setType(Material.PURPLE_STAINED_GLASS);
                    used = true;
                }
                x++;
                location.add(1,0,0);
                if (x > 1) {
                    x = -1;
                    location.add(-3,0,1);
                    y++;
                }
            }
            if(used) {
                Bukkit.getServer().getScheduler().runTaskLater(main, () -> {
                    int xA = -1;
                    int yA = -1;
                    Location locationA = playerLocation;
                    locationA.add(2, 0, -1);
                    while (yA < 2) {
                        if (location.getBlock().getType() == Material.PURPLE_STAINED_GLASS) {
                            location.getBlock().setType(Material.AIR);
                        }
                        xA++;
                        locationA.add(-1, 0, 0);
                        if (xA > 1) {
                            xA = -1;
                            locationA.add(3, 0, -1);
                            yA++;
                        }
                    }
                }, 100);
            }
            return used;
    }

    @EventHandler
    public void onPlayerInteraction(PlayerInteractEvent event){
        Action action = event.getAction();
        if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            if (ItemStacks.hasNBTValue(player.getInventory().getItemInMainHand(), "Rettungsplatform")) {
                player.sendMessage("lol");

                Location playerLocation = player.getLocation().add(0,-1,0);

                if(platform(playerLocation)){
                    player.getInventory().removeItem(rettungsplatform);
                }
            }
        }
    }
}