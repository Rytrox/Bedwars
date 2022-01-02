package de.rytrox.bedwars.map;

import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.database.entity.Team;
import de.rytrox.bedwars.utils.ResourceSpawner;
import de.rytrox.bedwars.utils.ScoreboardManager;
import de.rytrox.bedwars.utils.ShopUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapManager {

    private final Map map;
    private final ScoreboardManager scoreboardManager;
    private final List<Location> shops = new ArrayList<>();
    private final List<ResourceSpawner> spawners = new ArrayList<>();

    public MapManager(Map map, ScoreboardManager scoreboardManager) {
        this.map = map;
        this.scoreboardManager = scoreboardManager;
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

    public void showScoreboards() {
        map.getTeams()
                .forEach(team -> team.getMembers()
                        .forEach(member -> {
                            scoreboardManager.addBoard(member, 0, 0);
                            scoreboardManager.addPlayerToTeam(member);
                        }));
    }

    public void deleteAllItems() {
        Objects.requireNonNull(Bukkit.getWorld(map.getPos1().getWorld())).getEntities().forEach(entity -> {
            if (entity instanceof Item) entity.remove();
        });
    }

    public void deleteAllSpawnerArmorStands() {
        Objects.requireNonNull(Bukkit.getWorld(map.getPos1().getWorld())).getEntities().forEach(entity -> {
            if (entity instanceof ArmorStand && Objects.requireNonNull(entity.getCustomName()).startsWith("Level: "))entity.remove();
        });
    }

    public void stopSpawners() {
        spawners.forEach(ResourceSpawner::stop);
    }

    public void killShops() {
        shops.forEach(villager -> ShopUtils.killShopVillagers(villager, 2));
    }

    public void removeScoreboards() {
        map.getTeams()
                .forEach(team -> team.getMembers()
                        .forEach(scoreboardManager::removeBoard));
    }

    public void levelUpSpawners() {
        spawners.forEach(ResourceSpawner::levelUp);
    }
}
