package de.rytrox.bedwars.database.repository;

import de.rytrox.bedwars.database.entity.PlayerStatistic;
import io.ebean.Database;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class PlayerStatisticsRepository {

    private final Database database;

    public PlayerStatisticsRepository(Database database) {
        this.database = database;
    }

    /**
     * Sucht in der Datenbank nach einer Statistik. <br>
     * Sollte diese Statistik noch nicht existieren, wird ein leerer Optional zurückgegeben. <br>
     * <br>
     * Diese Methode ist sync! Nicht im Main-Thread verwenden, friert den Server ein!
     *
     * @param uuid die UUID des Spielers
     * @return die Statistik des Spielers oder ein leeres Optional
     */
    @NotNull
    public Optional<PlayerStatistic> findByUUID(@NotNull UUID uuid) {
        return database.find(PlayerStatistic.class)
                .where()
                .idEq(uuid.toString())
                .findOneOrEmpty();
    }
}
