package de.rytrox.bedwars.utils;

import de.timeout.libs.sql.SQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MapUtils {

    private final SQL db;

    public MapUtils(SQL sql) {
        this.db = sql;
    }

    public void updateTables() {

    }

    public String convertLocationToString(Location location) {
        return String.format("%s ! %f ! %f ! %f ! %f ! %f",
                Objects.requireNonNull(location.getWorld()).getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch());
    }

    public Location convertStringToLocation(String string) {
        String[] values = string.split(" ! ");
        if(values.length != 6) return null;
        return new Location(Bukkit.getWorld(values[0]),
                Double.parseDouble(values[1]),
                Double.parseDouble(values[2]),
                Double.parseDouble(values[3]),
                Float.parseFloat(values[4]),
                Float.parseFloat(values[5]));
    }

    public String convertListToString(List<String> list) {
        return list.stream()
                .collect(Collectors.joining("_", "{", "}"));
    }

    public List<String> convertStringToList(String string) {
        return Arrays.asList(string.replace("{", "")
                .replace("}", "")
                .split("_"));
    }

}
