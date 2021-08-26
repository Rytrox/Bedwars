package de.rytrox.bedwars.utils.map;

import org.bukkit.Location;

public class Spawner {

    private Location location;
    private SpawnerMaterial material;

    public Spawner(Location location, SpawnerMaterial material) {
        this.location = location;
        this.material = material;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public SpawnerMaterial getMaterial() {
        return material;
    }

    public void setMaterial(SpawnerMaterial material) {
        this.material = material;
    }

    public boolean checkComplete() {
        return location != null && material != null;
    }
}
