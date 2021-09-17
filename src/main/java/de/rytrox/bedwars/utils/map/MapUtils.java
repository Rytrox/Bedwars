package de.rytrox.bedwars.utils.map;

import de.timeout.libs.sql.QueryBuilder;
import de.timeout.libs.sql.SQL;
import org.bukkit.Location;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapUtils {

    private final SQL db;

    public MapUtils(SQL sql) {
        this.db = sql;
    }

    private final List<Map> mapsInEditor = new ArrayList<>();

    public void updateTables() {
        try {
            db.prepare("CREATE TABLE IF NOT EXISTS Locations(id INTEGER NOT NULL, " +
                            "world VARCHAR(100) NOT NULL, " +
                            "x FLOAT NOT NULL, " +
                            "y FLOAT NOT NULL, " +
                            "z FLOAT NOT NULL, " +
                            "yaw FLOAT NOT NULL, " +
                            "pitch FLOAT NOT NULL, " +
                            "PRIMARY KEY (id))")
                    .execute();
            db.prepare("CREATE TABLE IF NOT EXISTS Maps(name VARCHAR(100) NOT NULL, " +
                            "teamsize INT(3) NOT NULL, " +
                            "pos1 INT NOT NULL, " +
                            "pos2 INT NOT NULL," +
                            "PRIMARY KEY (name), " +
                            "FOREIGN KEY (pos1) REFERENCES Locations(id), " +
                            "FOREIGN KEY (pos2) REFERENCES Locations(id))")
                    .execute();
            db.prepare("CREATE TABLE IF NOT EXISTS Teams(id INTEGER NOT NULL, " +
                            "color CHARACTER NOT NULL, " +
                            "villager INT NOT NULL, " +
                            "spawn INT NOT NULL, " +
                            "bed INT NOT NULL, " +
                            "map VARCHAR(100) NOT NULL, " +
                            "PRIMARY KEY (id), " +
                            "FOREIGN KEY (villager) REFERENCES Locations(id), " +
                            "FOREIGN KEY (spawn) REFERENCES Locations(id), " +
                            "FOREIGN KEY (bed) REFERENCES Locations(id), " +
                            "FOREIGN KEY (map) REFERENCES Maps(name))")
                    .execute();
            db.prepare("CREATE TABLE IF NOT EXISTS Spawner(id INTEGER NOT NULL, " +
                            "material INT(1) NOT NULL, " +
                            "location INT NOT NULL, " +
                            "map VARCHAR(100) NOT NULL, " +
                            "PRIMARY KEY (id), " +
                            "FOREIGN KEY (location) REFERENCES Locations(id), " +
                            "FOREIGN KEY (map) REFERENCES Maps(name))")
                    .execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Map> getMapsInEditor() {
        return new ArrayList<>(mapsInEditor);
    }

    public boolean saveMap(Map map) throws SQLException {
        if(!mapsInEditor.contains(map)) return false;
        if(!map.checkComplete()) return false;
        deleteTeams(map);
        deleteSpawner(map);
        buildLocation(map.getPos1()).insert((pos1id) -> {
            buildLocation(map.getPos2()).insert((pos2id) -> {
                db.prepare("REPLACE INTO Maps (name, teamsize, pos1, pos2) VALUES (?, ?, ?, ?)",
                        map.getName(), map.getMaxTeamSize(), pos1id, pos2id)
                        .insert();
                for (Team team : map.getTeams()) {
                    buildLocation(team.getVillager()).insert((villager) -> {
                        buildLocation(team.getBed()).insert((bed) -> {
                            buildLocation(team.getSpawn()).insert((spawn) -> {
                                db.prepare("INSERT INTO Teams (color, villager, spawn, bed, map) VALUES (?, ?, ?, ?, ?)",
                                        team.getColor().getChar(), villager, spawn, bed, map.getName())
                                        .insert();
                            });
                        });
                    });
                }
                for (Spawner spawner : map.getSpawners()) {
                    buildLocation(spawner.getLocation()).insert((buildLocation) -> {
                        db.prepare("INSERT INTO Spawner (material, location, map) VALUES (?, ?, ?)",
                                spawner.getMaterial().ordinal(), buildLocation, map.getName());
                    });
                }
            });
        });
        return true;
    }

    private QueryBuilder buildLocation(Location location) throws SQLException {
        return db.prepare("INSERT INTO Locations (world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?)",
                Objects.requireNonNull(location.getWorld()).getName(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    private void deleteSpawner(Map map) throws SQLException {
        db.prepare("SELECT Locations.id FROM Locations INNER JOIN Spawner ON Spawner.location = Locations.id WHERE Spawner.map = ?", map.getName())
                .query((resultSet) -> {
                    while (resultSet.next()) {
                        db.prepare("DELETE FROM Locations WHERE id = ?", resultSet.getInt("id")).execute();
                    }
                });
        db.prepare("DELETE FROM Spawner WHERE map = ?", map.getName()).execute();
    }

    private void deleteTeams(Map map) throws SQLException {
        db.prepare("SELECT Locations.id FROM Locations INNER JOIN Teams ON Teams.villager = Locations.id " +
                        "INNER JOIN Teams ON Teams.bed = Locations.id INNER JOIN Teams ON Teams.spawn = Locations.id WHERE Teams.map = ?", map.getName())
                .query((resultSet) -> {
                    while (resultSet.next()) {
                        db.prepare("DELETE FROM Locations WHERE id = ?", resultSet.getInt("id")).execute();
                    }
                });
        db.prepare("DELETE * FROM Teams WHERE map = ?", map.getName()).execute();
    }
}
