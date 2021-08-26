package de.rytrox.bedwars.utils.map;

import org.bukkit.Location;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Map {

    private String name;
    private Location pos1;
    private Location pos2;
    private int teamsize = 1;
    private final List<Team> teams = new LinkedList<>();
    private final List<Spawner> spawners = new LinkedList<>();

    public Map(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    public int getTeamsize() {
        return teamsize;
    }

    public void setTeamsize(int teamsize) {
        this.teamsize = teamsize;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Spawner> getSpawners() {
        return spawners;
    }

    public boolean checkComplete() {
        AtomicBoolean teamCheck = new AtomicBoolean(true);
        AtomicBoolean spawnerCheck = new AtomicBoolean(true);
        if (teams.size() > 1) return false;
        if (spawners.isEmpty()) return false;
        teams.forEach(team -> teamCheck.set(team.checkComplete()));
        spawners.forEach(spawner -> teamCheck.set(spawner.checkComplete()));
        return name != null && pos1 != null && pos2 != null && teamCheck.get() && spawnerCheck.get();
    }
}
