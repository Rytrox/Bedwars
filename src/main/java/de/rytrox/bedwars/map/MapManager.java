package de.rytrox.bedwars.map;

import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.database.entity.Team;
import de.rytrox.bedwars.utils.ResourceSpawner;
import de.rytrox.bedwars.utils.ShopUtils;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class MapManager {

    private final Map map;
    private final List<Location> shops = new ArrayList<>();
    private final List<ResourceSpawner> spawners = new ArrayList<>();

    public MapManager(Map map) {
        this.map = map;
    }

    public void summonSpawners() {
        map.getSpawner().forEach(spawner -> spawners.add(new ResourceSpawner(spawner.getMaterial().getMaterial(),
                spawner.getLocation().toBukkitLocation(), 5, 4, 3)));
    }

    public void summonShops() {
        map.getTeams()
                .stream()
                .map(Team::getVillager)
                .forEach(villager -> shops.add(villager.toBukkitLocation()));
        shops.forEach(ShopUtils::summonShopVillager);
    }

    public void teleportPlayers() {
        map.getTeams()
                .forEach(team -> team.getMembers()
                        .forEach(member -> member.teleport(team.getSpawn().toBukkitLocation())));
    }

    public void stopSpawners() {
        spawners.forEach(ResourceSpawner::stop);
    }

    public void killShops() {
        shops.forEach(villager -> ShopUtils.killShopVillagers(villager, 2));
    }

    public void levelUpSpawners() {
        spawners.forEach(ResourceSpawner::levelUp);
    }
}
