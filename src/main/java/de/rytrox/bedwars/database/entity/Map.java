package de.rytrox.bedwars.database.entity;

import de.rytrox.bedwars.utils.Completable;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table (name = "`Maps`")
public class Map implements Completable {

    @Id
    @Column (name = "`name`", unique = true, nullable = false)
    private String name;

    @Column (name = "`world`", nullable = false)
    private String world;

    @Column (name = "`teamsize`", nullable = false)
    private Integer teamsize;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "`pos1`", nullable = false)
    private Location pos1;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "`pos2`", nullable = false)
    private Location pos2;

    @OneToMany (mappedBy = "map", cascade = CascadeType.ALL)
    private List<Team> teams;

    @OneToMany (mappedBy = "map", cascade = CascadeType.ALL)
    private List<Spawner> spawner;

    @Transient
    private List<Team> aliveTeams = new ArrayList<>();

    public Map() {

    }

    public Map(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public Integer getTeamsize() {
        return teamsize;
    }

    public void setTeamsize(@NotNull Integer teamsize) {
        this.teamsize = teamsize;
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(@NotNull Location pos1) {
        this.pos1 = pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(@NotNull Location pos2) {
        this.pos2 = pos2;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(@NotNull List<Team> teams) {
        this.teams = teams;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(@NotNull String world) {
        this.world = world;
    }

    public List<Team> getAliveTeams() {
        return new ArrayList<>(this.aliveTeams);
    }

    public void setAliveTeams(@NotNull List<Team> aliveTeams) {
        this.aliveTeams = aliveTeams;
    }

    public void addTeam(Team team) {
        if (team == null) return;
        team.setMap(this);
        this.teams.add(team);
    }

    public void removeTeam(Team team) {
        if (team == null) return;
        if (!this.teams.contains(team)) return;
        this.teams.remove(team);
    }

    public List<Spawner> getSpawner() {
        return spawner;
    }

    public void setSpawner(List<Spawner> spawner) {
        this.spawner = spawner;
    }

    public void addSpwner(Spawner spawner) {
        if (spawner == null) return;
        spawner.setMap(this);
        this.spawner.add(spawner);
    }

    public void removeSpawner(org.bukkit.Location location, double radius) {
        new ArrayList<>(spawner).forEach(streamSpawner -> {
            if (streamSpawner.getLocation().toBukkitLocation().distanceSquared(location) <= radius * radius)
                this.spawner.remove(streamSpawner);
        });
    }

    public void fillAliveTeams() {
        teams.stream()
                .filter(team -> !team.getMembers().isEmpty())
                .forEach(team -> aliveTeams.add(team));
    }

    @Override
    public boolean checkComplete() {
        if (teams == null || spawner == null || teams.size() < 2 || spawner.isEmpty()) return false;
        return name != null && teamsize != null &&
                Stream.concat(Stream.concat(teams.stream(), Stream.of(pos1, pos2)), spawner.stream())
                .allMatch(object -> object != null && object.checkComplete());
    }
}
