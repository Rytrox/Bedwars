package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class RecourceSpawner {

    private int level;
    private int[] times = new int[3];
    private Location pos;
    private Material material;
    private BukkitTask task;

    public RecourceSpawner(Material material, Location pos, int time1, int time2, int time3) {
        times[0] = time1;
        times[1] = time2;
        times[2] = time3;
        this.pos = pos;
        this.material = material;
        level = 1;
        start();
    }

    public void start() {
        task = Bukkit.getServer().getScheduler().runTaskTimer(JavaPlugin.getPlugin(Bedwars.class), () -> {
            pos.getWorld().dropItemNaturally(pos, new ItemStack(material));
        }, 20,20*(times[level - 1]));

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
            this.level++;
            this.stop();
            this.start();
        } else {
            System.out.println("maximal level bereits erreicht");
        }
    }
}