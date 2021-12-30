package de.rytrox.bedwars.map;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MapLoader {

    private Map map;

    public MapLoader(Bedwars main, TeamManager teamManager){
        Bukkit.getScheduler().runTaskAsynchronously(main,() -> {
            List<String> worlds = Bukkit.getServer().getWorlds()
                    .stream()
                    .map(World::getName)
                    .collect(Collectors.toList());

            List<Map> allMaps = main.getMapRepository().findMaps(worlds);
            Collections.shuffle(allMaps);
            allMaps.stream()
                    .findAny()
                    .ifPresentOrElse(mapList -> map = mapList, () -> System.out.println("No Map Found"));
            teamManager.setMap(map);
        });
    }

    public Map getMap() {
        return map;
    }
}
