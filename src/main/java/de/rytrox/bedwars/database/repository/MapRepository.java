package de.rytrox.bedwars.database.repository;

import de.rytrox.bedwars.database.entity.Map;
import io.ebean.Database;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Map> findAllMaps() {
        return database.find(Map.class)
                .findList();
    }

    public List<String> findAllMapsWithName() {
        return database.find(Map.class)
                .findList()
                .stream()
                .map(Map::getName)
                .collect(Collectors.toList());
    }

    public void saveMap(Map map) {
        if (!map.checkComplete()) return;
        if (this.findByName(map.getName()).isEmpty()) database.save(map);
        else database.update(map);
    }

    public void deleteMap(Map map) {
        database.delete(map);
    }
}
