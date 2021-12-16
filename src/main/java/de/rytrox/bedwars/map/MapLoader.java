package de.rytrox.bedwars.map;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Map;
import org.bukkit.Bukkit;

import java.util.Collections;
import java.util.stream.Collectors;

public class MapLoader {

    private final Bedwars main;
    private Map map;

    public MapLoader(Bedwars main){
        this.main = main;
        Bukkit.getScheduler().runTaskAsynchronously(main,()->{
            main.getMapRepository().findAllMaps().stream()
                    .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                        Collections.shuffle(collected);
                        return collected.stream();
                    }))
                    .findAny()
                    .ifPresentOrElse(mapList -> map = mapList, () -> System.out.println("No Map Found"));
        });
    }

    public Map getMap() {
        return map;
    }
}
