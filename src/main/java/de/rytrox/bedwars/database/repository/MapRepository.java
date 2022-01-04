package de.rytrox.bedwars.database.repository;

import de.rytrox.bedwars.database.entity.Map;
import io.ebean.Database;
import io.ebean.ExpressionList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MapRepository {

    private final Database database;

    public MapRepository(Database database) {
        this.database = database;
    }

    /**
     * Sucht in der Datenbank nach einer Statistik.
     * Sollte diese Statistik noch nicht existieren, wird ein leerer Optional zurückgegeben.
     *
     * Diese Methode ist sync! Nicht im Main-Thread verwenden, friert den Server ein!
     *
     * @param name der name der Map
     * @return die Map oder ein leeres Optional
     */
    @NotNull
    public Optional<Map> findByName(@NotNull String name) {
        return database.find(Map.class)
                .where()
                .idEq(name)
                .findOneOrEmpty();
    }

    /**
     * Sucht fir alle in der Datenbank vorhandenen Maps raus
     *
     * Diese Methode ist sync! Nicht im Main-Thread verwenden, friert den Server ein!
     *
     * @return eine Liste mit allen Maps
     */
    public List<Map> findMapsByWorld(List<String> worldNames) {
        ExpressionList<Map> scope = database.find(Map.class).where();
        for (int i = 0; i < worldNames.size(); i++)
            scope = scope
                    .or()
                    .eq("world", worldNames.get(i));
        return scope.findList();
    }

    /**
     * Sucht dir die Namen aller in der Datenbank vorhandenen Maps raus
     *
     * Diese Methode ist sync! Nicht im Main-Thread verwenden, friert den Server ein!
     *
     * @return eine Liste der Namen aller Maps
     */
    public List<String> findAllMapsWithName() {
        return database.find(Map.class)
                .findIds();
    }

    /**
     * Speichert eine Map in der Datenbank ab, oder ersetzt eine vorhandene
     *
     * Diese Methode ist sync! Nicht im Main-Thread verwenden, friert den Server ein!
     *
     * @param map die Map, die gespeichert werden soll
     */
    public void saveMap(Map map) {
        if (!map.checkComplete()) return;
        if (this.findByName(map.getName()).isEmpty()) database.save(map);
        else database.update(map);
    }

    /**
     * Löscht eine Map aus der Datenbank
     *
     * Diese Methode ist sync! Nicht im Main-Thread verwenden, friert den Server ein!
     *
     * @param map die zu löschende Map
     */
    public void deleteMap(Map map) {
        database.delete(map);
    }
}
