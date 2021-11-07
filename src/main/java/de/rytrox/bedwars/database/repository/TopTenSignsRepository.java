package de.rytrox.bedwars.database.repository;

import de.rytrox.bedwars.database.entity.TopTenSigns;
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

    public Optional<TopTenSigns> findById(Integer id) {
        return database.find(TopTenSigns.class)
                .where()
                .idEq(id)
                .findOneOrEmpty();
    }

    public Optional<TopTenSigns> findByLocation(Location location) {
        return database.find(TopTenSigns.class)
                .fetch("location")
                .where()
                .eq("location.world", Objects.requireNonNull(location.getWorld()).getName())
                .eq("location.x", location.getX())
                .eq("location.y", location.getY())
                .eq("location.z", location.getZ())
                .findOneOrEmpty();
    }

    public List<TopTenSigns> findAllSigns() {
        return database.find(TopTenSigns.class)
                .where()
                .findList();
    }

    public void saveTopTenSigns(TopTenSigns topTenSigns) {
        database.save(topTenSigns);
    }

    public void deleteTopTenSigns(TopTenSigns topTenSigns) {
        database.delete(topTenSigns);
    }
}
