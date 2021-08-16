package de.rytrox.bedwars.utils;

import de.timeout.libs.sql.QueryBuilder;
import de.timeout.libs.sql.SQL;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Statistics {

    private final SQL db;

    public Statistics(SQL sql) {
        this.db = sql;
    }

    /**
     * Creates the statistics table if it not exists
     * @throws SQLException if something went wrong
     */
    public void updateTable() throws SQLException {
        db.prepare("CREATE TABLE IF NOT EXIST Stats (uuid VARCHAR(32) NOT NULL, " +
                "games INT(10) NOT NULL DEFAULT '0', " +
                "wins INT(10) NOT NULL DEFAULT '0', " +
                "kills INT(10) NOT NULL DEFAULT '0', " +
                "deaths INT(10) NOT NULL DEFAULT '0', " +
                "PRIMARY KEY (uuid))").execute();
    }

    /**
     * Get a value from a player from the Stats datatable
     * @param player The player to get from
     * @param key The key to the value to get
     * @return the value to the key or "-1" if the key is wrong
     * @throws SQLException if something went wrong
     */
    public QueryBuilder getValue(Player player, String key) throws SQLException {
        if(!"games".equalsIgnoreCase(key) && !"wins".equalsIgnoreCase(key)
                && !"kills".equalsIgnoreCase(key) && !"deaths".equalsIgnoreCase(key)) return null;
        return db.prepare("SELECT " + key + " FROM Stats WHERE uuid = ?", player.getUniqueId().toString());
    }

    /**
     * Set a value from a player from the Stats datatable
     * @param player The player to set to
     * @param key The key to the value to set
     * @param value The value to set
     * @return true if everything is ok
     * @throws SQLException if something went wrong
     */
    public boolean setValue(Player player, String key, int value) throws SQLException {
        if(!"games".equalsIgnoreCase(key) && !"wins".equalsIgnoreCase(key)
                && !"kills".equalsIgnoreCase(key) && !"deaths".equalsIgnoreCase(key)) return false;
        db.prepare("INSERT INTO Stats(uuid, " + key + ") VALUES (?, ?) ON DUPLICATE KEY UPDATE " + key + " = ?", player.getUniqueId().toString(), value, value)
                .update();
        return true;
    }
}
