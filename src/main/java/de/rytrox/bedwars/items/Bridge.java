package de.rytrox.bedwars.items;

import de.rytrox.bedwars.Bedwars;
import de.timeout.libs.item.ItemStackBuilder;
import de.timeout.libs.item.ItemStacks;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class Bridge implements Listener {

    private  final Bedwars main;

    public Bridge(Bedwars main) {
        this.main = main;
    }


    public ItemStack getBridge(){
        return new ItemStackBuilder(Material.STICK)
                .writeNBTBoolean("BedwarsBridge", true)
                .setDisplayName("Bridge LOOOL")
                .toItemStack();
    }

    @EventHandler
    public void onPlayerInteracts(PlayerInteractEvent event) {
        Action action = event.getAction();
        if(action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)) {
            Player player = event.getPlayer();
            if(ItemStacks.hasNBTValue(player.getInventory().getItemInMainHand(), "BedwarsBridge")) {
                player.getInventory().removeItem(getBridge());
                float Yaw = player.getLocation().getYaw();
                Location location = player.getLocation().add(0,-1,0);
                Location loc = player.getLocation().add(0,-1,0);
                if(Yaw >= 0 && Yaw <= 45 || Yaw > 315 && Yaw <= 360){
                    location.add(0,0,1);
                    player.sendMessage("z+");
                    for(int i = 0; i < 21; i++){
                        if(location.getBlock().getType().equals(Material.AIR)){
                            location.getBlock().setType(Material.SANDSTONE);
                        }
                        location.add(0,0,1);
                    }
                } else if(Yaw > 45 && Yaw <= 135){
                    location.add(-1,0,0);
                    player.sendMessage("x-");
                    for(int i = 0; i < 20; i++){
                        if(location.getBlock().getType().equals(Material.AIR)){
                            location.getBlock().setType(Material.SANDSTONE);
                        }
                        location.add(-1,0,0);
                    }
                } else if(Yaw > 135 && Yaw <= 225){
                    location.add(0,0,-1);
                    player.sendMessage("z-");
                    for(int i = 0; i < 20; i++){
                        if(location.getBlock().getType().equals(Material.AIR)){
                            location.getBlock().setType(Material.SANDSTONE);
                        }
                        location.add(0,0,-1);
                    }
                } else if(Yaw > 225 && Yaw <= 315){
                    location.add(1,0,0);
                    player.sendMessage("x+");
                    for(int i = 0; i < 20; i++){
                        if(location.getBlock().getType().equals(Material.AIR)){
                            location.getBlock().setType(Material.SANDSTONE);
                        }
                        location.add(1,0,0);
                    }
                } else{
                    player.sendMessage("[Error]: Brücke konnte nicht erstellt werden :(");
                }
            }
        }
    }
}
