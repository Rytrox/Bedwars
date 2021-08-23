package de.rytrox.bedwars.utils;

import de.timeout.libs.sql.SQL;

import java.sql.SQLException;

public class MapUtils {

    private final SQL db;

    public MapUtils(SQL sql) {
        this.db = sql;
    }

    public void updateTables() {
        try {
            db.prepare("CREATE TABLE IF NOT EXISTS Spawner (id VARCHAR NOT NULL, " +
                    "gold VARCHAR, iron VARCHAR, bronze VARCHAR, PRIMARY KEY (id))").execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
