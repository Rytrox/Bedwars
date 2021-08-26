package de.rytrox.bedwars.utils.map;

import de.rytrox.bedwars.utils.Completable;
import org.bukkit.Location;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Map implements Completable {

    private String name;
    private Location pos1;
    private Location pos2;
    private int maxTeamSize = 1;
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

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public void setMaxTeamSize(int maxTeamSize) {
        this.maxTeamSize = maxTeamSize;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Spawner> getSpawners() {
        return spawners;
    }

    @Override
    public boolean checkComplete() {
        if (teams.size() <= 1) return false;
        if (spawners.isEmpty()) return false;

        boolean check = Stream.concat(teams.stream(), spawners.stream())
                .anyMatch(team -> !team.checkComplete());

        return name != null && pos1 != null && pos2 != null && check;
    }
}
