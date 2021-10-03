package de.rytrox.bedwars.database.repository;

import de.rytrox.bedwars.database.entity.Map;
import io.ebean.Database;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MapRepository {

    private final Database database;

    public MapRepository(Database database) {
        this.database = database;
    }

    @NotNull
    public Optional<Map> findByName(@NotNull String name) {
        return database.find(Map.class)
                .where()
                .idEq(name)
                .findOneOrEmpty();
    }
}
