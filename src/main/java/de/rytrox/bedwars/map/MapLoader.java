package de.rytrox.bedwars.map;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.team.TeamManager;
import de.rytrox.bedwars.utils.LobbyArea;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MapLoader {

    private Map map;

    public MapLoader(Bedwars main, TeamManager teamManager){
        Bukkit.getScheduler().runTaskAsynchronously(main,() -> {
            List<String> worlds = Arrays.stream(Objects.requireNonNull(Bukkit.getServer().getWorldContainer().listFiles(File::isDirectory)))
                    .map(File::getName)
                    .collect(Collectors.toList());

            List<Map> allMaps = main.getMapRepository().findMapsByWorld(worlds);
            Collections.shuffle(allMaps);
            allMaps.stream()
                    .findAny()
                    .ifPresentOrElse(mapList -> map = mapList, () -> System.out.println("No Map Found"));

            if(map != null) {
                teamManager.setMap(map);

                Bukkit.getScheduler().runTask(main, () ->
                        Bukkit.getServer().createWorld(new WorldCreator(map.getWorld())));
            }
        });
    }

    public Map getMap() {
        return map;
    }
}
