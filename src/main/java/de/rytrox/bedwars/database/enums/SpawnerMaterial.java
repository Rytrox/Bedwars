package de.rytrox.bedwars.database.enums;

import de.rytrox.bedwars.Bedwars;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public enum SpawnerMaterial {

    BRONZE(Material.BRICK, "bronze"),
    IRON(Material.IRON_INGOT, "iron"),
    GOLD(Material.GOLD_INGOT, "gold");

    private final Bedwars main = JavaPlugin.getPlugin(Bedwars.class);

    private Material material;
    private double level1;
    private double level2;
    private double level3;

    SpawnerMaterial(Material material, String materialName) {
        this.material = material;
        this.level1 = (double) main.getConfig().get("spawner." + materialName + ".level1", 5.0);
        this.level2 = (double) main.getConfig().get("spawner." + materialName + ".level2", 4.0);
        this.level3 = (double) main.getConfig().get("spawner." + materialName + ".level3", 3.0);
    }

    public Material getMaterial() {
        return material;
    }

    public double getLevel1() {
        return level1;
    }

    public double getLevel2() {
        return level2;
    }

    public double getLevel3() {
        return level3;
    }
}
