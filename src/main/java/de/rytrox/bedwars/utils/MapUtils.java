package de.rytrox.bedwars.utils;

import de.timeout.libs.sql.SQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;

public class MapUtils {

    private final SQL db;

    public MapUtils(SQL sql) {
        this.db = sql;
    }

    public void updateTables() {

    }

    public String convertLocationToString(Location location) {
        return String.format("%s - %f - %f - %f - %f - %f",
                Objects.requireNonNull(location.getWorld()).getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch());
    }

    public Location convertStringToLocation(String value) {
        String[] values = value.split(" - ");
        if(values.length != 6) return null;
        return new Location(Bukkit.getWorld(values[0]),
                Double.parseDouble(values[1]),
                Double.parseDouble(values[2]),
                Double.parseDouble(values[3]),
                Float.parseFloat(values[4]),
                Float.parseFloat(values[5]));
    }
}
