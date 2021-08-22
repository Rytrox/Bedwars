package de.rytrox.bedwars.utils;

import de.timeout.libs.sql.QueryBuilder;
import de.timeout.libs.sql.SQL;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Statistics {

    private final SQL db;

    public Statistics(SQL sql) {
        this.db = sql;
    }

    /**
     * Creates the statistics table if it not exists
     */
    public void updateTable() {
        try {
            db.prepare("CREATE TABLE IF NOT EXISTS Stats (uuid VARCHAR(32) NOT NULL, " +
                    "games INT(10) NOT NULL DEFAULT '0', " +
                    "wins INT(10) NOT NULL DEFAULT '0', " +
                    "kills INT(10) NOT NULL DEFAULT '0', " +
                    "deaths INT(10) NOT NULL DEFAULT '0', " +
                    "PRIMARY KEY (uuid))").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a value from a player from the Stats datatable
     * @param player The player to get from
     * @param key The key to the value to get
     * @return the value to the key or "-1" if the key is wrong
     * @throws SQLException if something went wrong
     */
    public QueryBuilder getValue(Player player, String key) throws SQLException {
        if(checkKey(key)) return null;
        return db.prepare(String.format("SELECT %s FROM Stats WHERE uuid = ?", key), player.getUniqueId().toString());
    }

    /**
     * Set a value from a player from the Stats datatable
     * @param player The player to set to
     * @param key The key to the value to set
     * @param value The value to set
     * @throws SQLException if something went wrong
     */
    public void setValue(Player player, String key, int value) throws SQLException {
        if(checkKey(key)) return;
        getValue(player, key).query(resultSet -> {
            if (!resultSet.next()) db.prepare("INSERT INTO Stats(uuid, " + key + ") VALUES (?, ?)", player.getUniqueId().toString(), value).execute();
            else db.prepare("UPDATE Stats SET " + key + " = ? WHERE uuid = ?", value, player.getUniqueId().toString()).execute();
        });
    }

    /**
     * Add a value to a players score from the Stats datatable
     * @param player The player to set to
     * @param key The key to the value to set
     * @param value The value to set
     * @throws SQLException if something went wrong
     */
    public void addValue(Player player, String key, int value) throws SQLException {
        if(checkKey(key)) return;
        getValue(player, key).query(resultSet -> {
            if (!resultSet.next()) db.prepare("INSERT INTO Stats(uuid " + key + ") VALUES (?, ?)", player.getUniqueId().toString(), value).execute();
            else db.prepare("UPDATE Stats SET " + key + " = ? WHERE uuid = ?", resultSet.getInt(key) + value, player.getUniqueId().toString()).execute();
        });
    }

    /**
     * Remove a value to a players score from the Stats datatable
     * @param player The player to set to
     * @param key The key to the value to set
     * @param value The value to set
     * @throws SQLException if something went wrong
     */
    public void removeValue(Player player, String key, int value) throws SQLException {
        if(checkKey(key)) return;
        getValue(player, key).query(resultSet -> {
            if (resultSet.next()) db.prepare("UPDATE Stats SET " + key + " = ? WHERE uuid = ?", resultSet.getInt(key) - value, player.getUniqueId().toString()).execute();
        });
    }

    /**
     * Checks if the key is Valid
     * @param key The key to check
     * @return Returns "true" if the Key is wrong and "false" if the key is right
     */
    private boolean checkKey(String key) {
        if(!"games".equalsIgnoreCase(key) && !"wins".equalsIgnoreCase(key)
                && !"kills".equalsIgnoreCase(key) && !"deaths".equalsIgnoreCase(key)) return true;
        return false;
    }
}
