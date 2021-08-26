package de.rytrox.bedwars.utils.map;

import de.rytrox.bedwars.utils.Completable;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class Team implements Completable {

    private Location villager;
    private ChatColor color;
    private Location spawn;
    private Location bed;

    public Location getVillager() {
        return villager;
    }

    public void setVillager(Location villager) {
        this.villager = villager;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public Location getBed() {
        return bed;
    }

    public void setBed(Location bed) {
        this.bed = bed;
    }

    @Override
    public boolean checkComplete() {
        return villager != null && color != null && spawn != null && bed != null;
    }
}
