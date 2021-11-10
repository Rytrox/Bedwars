package de.rytrox.bedwars.database.entity;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table (name = "`TopTenSigns`")
public class TopTenSign {

    @Id
    @Column (name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne (cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn (name = "`location`",  nullable = false)
    private Location location;

    @Column (name = "`position`", nullable = false)
    private Integer position;

    @Column (name = "`sorted`", nullable = false)
    String sorted;

    @NotNull
    public Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
        this.id = id;
    }

    @NotNull
    public Location getLocation() {
        return location;
    }

    public void setLocation(@NotNull Location location) {
        this.location = location;
    }

    @NotNull
    public Integer getPosition() {
        return position;
    }

    public void setPosition(@NotNull Integer position) {
        this.position = position;
    }

    @NotNull
    public String getSorted() {
        return sorted;
    }

    public void setSorted(@NotNull String sorted) {
        this.sorted = sorted;
    }
}
