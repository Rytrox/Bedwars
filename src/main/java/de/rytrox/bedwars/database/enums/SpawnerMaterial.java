package de.rytrox.bedwars.database.enums;

import org.bukkit.Material;

public enum SpawnerMaterial {

    BRONZE(Material.BRICK), IRON(Material.IRON_INGOT), GOLD(Material.GOLD_INGOT);

    private Material material;

    SpawnerMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
