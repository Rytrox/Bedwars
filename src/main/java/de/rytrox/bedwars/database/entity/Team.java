package de.rytrox.bedwars.database.entity;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table (name = "`Teams`")
public class Team {

    @Id
    @Column (name = "`id`", unique = true, nullable = false)
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

    @NotNull
    public Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public ChatColor getColor() {
        return color;
    }

    public void setColor(@NotNull ChatColor color) {
        this.color = color;
    }

    @NotNull
    public Location getVillager() {
        return villager;
    }

    public void setVillager(@NotNull Location villager) {
        this.villager = villager;
    }

    @NotNull
    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(@NotNull Location spawn) {
        this.spawn = spawn;
    }

    @NotNull
    public Location getBed() {
        return bed;
    }

    public void setBed(@NotNull Location bed) {
        this.bed = bed;
    }

    @NotNull
    public Map getMap() {
        return map;
    }

    public void setMap(@NotNull Map map) {
        this.map = map;
    }
}
