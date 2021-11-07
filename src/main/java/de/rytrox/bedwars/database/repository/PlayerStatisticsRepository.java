package de.rytrox.bedwars.database.repository;

import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.database.entity.PlayerStatistic;
import io.ebean.Database;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public int getStatisticSize() {
        return database.find(PlayerStatistic.class)
                .where()
                .findCount();
    }

    public PlayerStatistic getTopPlayer(int position) {
        return database.find(PlayerStatistic.class)
                .where()
                .orderBy("wins")
                .setMaxRows(position)
                .findList()
                .get(position - 1);
    }

    public void savePlayerStatistic(PlayerStatistic playerStatistic) {
        if (this.findByUUID(playerStatistic.getUniqueID()).isEmpty()) database.save(playerStatistic);
        else database.update(playerStatistic);
    }
}
