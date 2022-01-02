package de.rytrox.bedwars.utils;

import org.bukkit.Location;

public class Area{

    public boolean inArea(Location von, Location bis, Location current){

        return
                current.getX() <= Math.max(von.getX(), bis.getX()) && current.getX() >= Math.min(von.getX(), bis.getX()) &&
                current.getY() <= Math.max(von.getY(), bis.getY()) && current.getY() >= Math.min(von.getY(), bis.getY()) &&
                current.getZ() <= Math.max(von.getZ(), bis.getZ()) && current.getZ() >= Math.min(von.getZ(), bis.getZ());
    }
}
