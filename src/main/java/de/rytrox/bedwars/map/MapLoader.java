package de.rytrox.bedwars.map;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Map;
import org.bukkit.Bukkit;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MapLoader {

    private final Bedwars main;
    private Map map;

    public MapLoader(Bedwars main){
        this.main = main;
        List<String> worldNames = Bukkit.getServer().getWorlds().stream().map(world -> world.getName()).collect(Collectors.toList());
        Bukkit.getScheduler().runTaskAsynchronously(main,()->{
            List<Map> allMaps = main.getMapRepository().findAllMaps();
            Collections.shuffle(allMaps);
            allMaps.stream()
                    .filter(world -> worldNames.contains(world.getName()))
                    .findAny()
                    .ifPresentOrElse(mapList -> map = mapList, () -> System.out.println("No Map Found"));
        });
    }

    public Map getMap() {
        return map;
    }
}
