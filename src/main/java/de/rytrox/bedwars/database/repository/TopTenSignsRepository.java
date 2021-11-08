package de.rytrox.bedwars.database.repository;

import de.rytrox.bedwars.database.entity.TopTenSign;
import io.ebean.Database;
import org.bukkit.Location;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TopTenSignsRepository {

    private final Database database;

    public TopTenSignsRepository(Database database) {
        this.database = database;
    }

    public Optional<TopTenSign> findById(Integer id) {
        return database.find(TopTenSign.class)
                .where()
                .idEq(id)
                .findOneOrEmpty();
    }

    public Optional<TopTenSign> findByLocation(Location location) {
        return database.find(TopTenSign.class)
                .fetch("location")
                .where()
                .eq("location.world", Objects.requireNonNull(location.getWorld()).getName())
                .eq("location.x", location.getX())
                .eq("location.y", location.getY())
                .eq("location.z", location.getZ())
                .findOneOrEmpty();
    }

    public List<TopTenSign> findAllSigns() {
        return database.find(TopTenSign.class)
                .where()
                .findList();
    }

    public void saveTopTenSign(TopTenSign topTenSigns) {
        database.save(topTenSigns);
    }

    public void deleteTopTenSign(TopTenSign topTenSigns) {
        database.delete(topTenSigns);
    }
}
