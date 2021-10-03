package de.rytrox.bedwars.database.entity;

import de.rytrox.bedwars.database.enums.SpawnerMaterial;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table (name = "´Spawner´")
public class Spawner {

    @Id
    @Column (name = "´id´", unique = true, nullable = false)
    private Integer id;

    @Enumerated (EnumType.ORDINAL)
    @Column (name = "´material´", nullable = false)
    private SpawnerMaterial material;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "´location´", nullable = false)
    private Location location;

    @ManyToOne (cascade = CascadeType.DETACH)
    @JoinColumn (name = "´map´", nullable = false)
    private Map map;

    @NotNull
    public Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
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
}
