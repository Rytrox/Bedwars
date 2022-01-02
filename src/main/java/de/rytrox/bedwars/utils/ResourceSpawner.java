package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
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
    private ArmorStand armorStand;
    private final Location armorStandPos;

    public ResourceSpawner(Material material, @NotNull Location pos, int time1, int time2, int time3) {
        times[0] = time1;
        times[1] = time2;
        times[2] = time3;
        level = 1;

        this.pos = pos.getBlock().getLocation();
        this.material = material;

        this.armorStandPos = this.pos.add(0,-1,0).clone();

        this.pos.add(0.5,1,0.5);

        start();
    }

    /**
     * Startet den ResourceSpawner
     */
    public void start() {
        summonArmorStand();
        task = Bukkit.getServer().getScheduler().runTaskTimer(JavaPlugin.getPlugin(Bedwars.class), () -> {
            Item dropItem = pos.getWorld().dropItem(pos, new ItemStack(material));
            dropItem.setVelocity(new Vector());
        }, 100, 20L * times[level - 1]);
    }

    /**
     * Beendet den ResourceSpawner
     */
    public void stop() {
        armorStand.remove();
        armorStand = null;
        task.cancel();
    }

    /**
     * Gibt zurück, ob der Spawner gestartet sind
     *
     * @return sind die Spawner gestartet
     */
    public boolean isStarted() {
        return !task.isCancelled();
    }

    /**
     * Gibt das Level des Spawner zurück
     *
     * @return spawnerLevel
     */
    public int getLevel() {
        return level;
    }

    /**
     * Erhöht das Level und die SpawnRate des Spawners
     */
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

    private void summonArmorStand() {
        armorStand = armorStandPos.getWorld().spawn(pos, ArmorStand.class);
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        armorStand.setCustomName("Level: " + level);
        armorStand.setVisible(false);
    }
}
