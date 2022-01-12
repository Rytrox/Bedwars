package de.rytrox.bedwars.utils;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class Area{

    public static boolean inArea(@NotNull Location from, @NotNull Location to, @NotNull Location current){

        return
                current.getWorld().equals(from.getWorld()) && from.getWorld().equals(to.getWorld()) &&
                current.getX() <= Math.max(from.getX(), to.getX()) && current.getX() >= Math.min(from.getX(), to.getX()) &&
                current.getY() <= Math.max(from.getY(), to.getY()) && current.getY() >= Math.min(from.getY(), to.getY()) &&
                current.getZ() <= Math.max(from.getZ(), to.getZ()) && current.getZ() >= Math.min(from.getZ(), to.getZ());
    }
}
