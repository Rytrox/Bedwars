package de.rytrox.bedwars.utils.map;

import de.timeout.libs.sql.SQL;

import java.sql.SQLException;

public class MapUtils {

    private final SQL db;

    public MapUtils(SQL sql) {
        this.db = sql;
    }

    public void updateTables() {
        try {
            db.prepare("CREATE TABLE IF NOT EXISTS Locations(id INT NOT NULL AUTO_INCREMENT, " +
                            "world VARCHAR(100) NOT NULL, " +
                            "x FLOAT NOT NULL, " +
                            "y FLOAT NOT NULL, " +
                            "z FLOAT NOT NULL, " +
                            "yaw FLOAT NOT NULL, " +
                            "pitch FLOAT NOT NULL, " +
                            "PRIMARY KEY (id))")
                    .execute();
            db.prepare("CREATE TABLE IF NOT EXISTS Maps(id INT NOT NULL AUTO_INCREMENT, " +
                            "name VARCHAR(100) NOT NULL, " +
                            "teamsize INT(3) NOT NULL, " +
                            "pos1 INT NOT NULL, " +
                            "pos2 INT NOT NULL," +
                            "PRIMARY KEY (id), " +
                            "FOREIGN KEY (pos1) REFERENCES Locations(id), " +
                            "FOREIGN KEY (pos2) REFERENCES Locations(id))")
                    .execute();
            db.prepare("CREATE TABLE IF NOT EXISTS Teams(id INT NOT NULL AUTO_INCREMENT, " +
                            "color VARCHAR NOT NULL, " +
                            "villager INT NOT NULL, " +
                            "spawn INT NOT NULL, " +
                            "bed INT NOT NULL, " +
                            "mapid INT NOT NULL, " +
                            "PRIMARY KEY (id), " +
                            "FOREIGN KEY (villager) REFERENCES Locations(id), " +
                            "FOREIGN KEY (spawn) REFERENCES Locations(id), " +
                            "FOREIGN KEY (bed) REFERENCES Locations(id), " +
                            "FOREIGN KEY (mapid) REFERENCES Maps(id))")
                    .execute();
            db.prepare("CREATE TABLE IF NOT EXISTS Spawner(id INT NOT NULL AUTO_INCREMENT, " +
                            "material INT(1) NOT NULL, " +
                            "location INT NOT NULL, " +
                            "mapid INT NOT NULL, " +
                            "PRIMARY KEY (id), " +
                            "FOREIGN KEY (location) REFERENCES Locations(id), " +
                            "FOREIGN KEY (mapid) REFERENCES Maps(id))")
                    .execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
