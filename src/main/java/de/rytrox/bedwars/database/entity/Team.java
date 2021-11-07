package de.rytrox.bedwars.database.entity;

import de.rytrox.bedwars.utils.Completable;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
@Table (name = "`Teams`")
public class Team implements Completable {

    @Id
    @Column (name = "id", nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "`name`", nullable = false)
    private String name;

    @Enumerated (EnumType.ORDINAL)
    @Column (name = "`color`", nullable = false)
    private ChatColor color;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "`villager`", nullable = false)
    private Location villager;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "`spawn`", nullable = false)
    private Location spawn;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "`bed`", nullable = false)
    private Location bed;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "`map`", nullable = false)
    private Map map;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(@NotNull ChatColor color) {
        this.color = color;
    }

    public void setColor(@NotNull Character color) {
        this.color = ChatColor.getByChar(color);
    }

    public Location getVillager() {
        return villager;
    }

    public void setVillager(@NotNull Location villager) {
        this.villager = villager;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(@NotNull Location spawn) {
        this.spawn = spawn;
    }

    public Location getBed() {
        return bed;
    }

    public void setBed(@NotNull Location bed) {
        this.bed = bed;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(@NotNull Map map) {
        this.map = map;
    }


    @Override
    public boolean checkComplete() {
        return Stream.of(spawn, bed, map)
                .allMatch(object -> object != null && object.checkComplete());
    }
}
