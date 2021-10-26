package de.rytrox.bedwars.database.entity;

import de.rytrox.bedwars.database.enums.SpawnerMaterial;
import de.rytrox.bedwars.utils.Completable;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table (name = "`Spawner`")
public class Spawner implements Completable {

    @Id
    @Column (name = "id", nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated (EnumType.ORDINAL)
    @Column (name = "`material`", nullable = false)
    private SpawnerMaterial material;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "`location`", nullable = false)
    private Location location;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "`map`", nullable = false)
    private Map map;

    public Spawner() {
    }

    public Spawner(SpawnerMaterial material, Location location) {
        this.material = material;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    public SpawnerMaterial getMaterial() {
        return material;
    }

    public void setMaterial(@NotNull SpawnerMaterial material) {
        this.material = material;
    }

    @NotNull
    public Location getLocation() {
        return location;
    }

    public void setLocation(@NotNull Location location) {
        this.location = location;
    }

    @NotNull
    public Map getMap() {
        return map;
    }

    public void setMap(@NotNull Map map) {
        this.map = map;
    }

    @Override
    public boolean checkComplete() {
        if (!location.checkComplete()) return false;
        return material != null && location != null;
    }
}
