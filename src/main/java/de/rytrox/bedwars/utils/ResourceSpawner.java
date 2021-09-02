package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class ResourceSpawner {

    private int level;
    private final int[] times = new int[3];
    private final Location pos;
    private final Material material;
    private BukkitTask task;
    private final ArmorStand armorStand;

    public ResourceSpawner(Material material, @NotNull Location pos, int time1, int time2, int time3) {
        times[0] = time1;
        times[1] = time2;
        times[2] = time3;
        level = 1;

        this.pos = pos;
        this.material = material;

        armorStand = pos.add(0,-1,0).getWorld().spawn(pos, ArmorStand.class);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName("Level: " + level);
        armorStand.setVisible(false);

        start();
    }

    public void start() {
        task = Bukkit.getServer().getScheduler().runTaskTimer(JavaPlugin.getPlugin(Bedwars.class), () -> {
            Item dropItem = pos.getWorld().dropItem(pos, new ItemStack(material));
            dropItem.setVelocity(new Vector());
        }, 100, 20L * times[level - 1]);
    }

    public void stop() {
        task.cancel();
    }

    public boolean isStarted() {
        return !task.isCancelled();
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        if(level < 3) {
            armorStand.setCustomName("Level: " + level);
            this.level++;
            this.stop();
            this.start();
        } else {
            System.out.println("maximal level bereits erreicht");
        }
    }
}
