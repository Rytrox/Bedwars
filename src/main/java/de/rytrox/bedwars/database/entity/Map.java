package de.rytrox.bedwars.database.entity;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "´Maps´")
public class Map {

    @Id
    @Column (name = "´name´", unique = true, nullable = false)
    private String name;

    @Column (name = "´teamsize´", nullable = false)
    private Integer teamsize;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "´pos1´", nullable = false)
    private Location pos1;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "´pos2´", nullable = false)
    private Location pos2;

    @OneToMany (mappedBy = "map", cascade = CascadeType.ALL)
    private List<Team> teams;

    @OneToMany (mappedBy = "map", cascade = CascadeType.ALL)
    private List<Spawner> spawner;

    public Map() {

    }

    public Map(String name) {
        this.name = name;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public Integer getTeamsize() {
        return teamsize;
    }

    public void setTeamsize(@NotNull Integer teamsize) {
        this.teamsize = teamsize;
    }

    @NotNull
    public Location getPos1() {
        return pos1;
    }

    public void setPos1(@NotNull Location pos1) {
        this.pos1 = pos1;
    }

    @NotNull
    public Location getPos2() {
        return pos2;
    }

    public void setPos2(@NotNull Location pos2) {
        this.pos2 = pos2;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Spawner> getSpawner() {
        return spawner;
    }

    public void setSpawner(List<Spawner> spawner) {
        this.spawner = spawner;
    }
}
