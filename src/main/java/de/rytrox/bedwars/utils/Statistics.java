package de.rytrox.bedwars.utils;

import de.timeout.libs.sql.SQL;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
     * Checks if the Stats DataTable contains the players uuid as a primary key
     * @param player The player to check
     * @return "true" if the player is registered and "false" if not
     * @throws SQLException if something went wrong
     */
    private boolean containsPlayer(Player player) throws SQLException {
        AtomicBoolean isThere = new AtomicBoolean(false);
        db.prepare("SELECT FROM Stats WHERE uuid = ?", player.getUniqueId().toString())
                .query(resultSet -> {
                    while (resultSet.next()) isThere.set(true);
                });
        return isThere.get();
    }

    /**
     * Registers a Player to the DataTable with all values "0"
     * Please only execute this methode if the Player isn't registered yet
     * @param player The Player to register
     * @throws SQLException if something went wrong
     */
    private void registerPlayer(Player player) throws SQLException {
        db.prepare("INSERT INTO Stats (uuid, games, wins, kills, deaths) VALUES (?, ?, ?, ?, ?)",
                player.getUniqueId().toString(), 0, 0, 0, 0)
                .update();
    }

    /**
     * Get a value from a player from the Stats datatable
     * @param player The player to get from
     * @param key The key to the value to get
     * @return the value to the key or "-1" if the key is wrong
     * @throws SQLException if something went wrong
     */
    public int getValue(Player player, String key) throws SQLException {
        if(!"games".equalsIgnoreCase(key) && !"wins".equalsIgnoreCase(key)
                && !"kills".equalsIgnoreCase(key) && !"deaths".equalsIgnoreCase(key)) return -1;
        if(!containsPlayer(player)) registerPlayer(player);
        AtomicInteger value = new AtomicInteger(-1);
        db.prepare("SELECT FROM Stats WHERE uuid = ?", player.getUniqueId().toString())
                .query(resultSet -> {
                    while (resultSet.next()) value.set(resultSet.getInt(key));
                });
        return value.get();
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
        if(!containsPlayer(player)) registerPlayer(player);
        db.prepare("UPDATE Stats SET " + key + " = ? WHERE uuid = ?", value, player.getUniqueId().toString())
                .update();
        return true;
    }
}
