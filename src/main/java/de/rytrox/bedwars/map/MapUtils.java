package de.rytrox.bedwars.map;

import de.rytrox.bedwars.database.entity.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapUtils {

    private final java.util.Map<String, Map> mapsInEdit = new HashMap<>();

    public Map getOrCreateMap(String name) {
        return mapsInEdit.computeIfAbsent(name, Map::new);
    }

    public List<String> getMapNames() {
        return new ArrayList<>(mapsInEdit.keySet());
    }

    public java.util.Map<String, Map> getMapsInEdit() {
        return mapsInEdit;
    }

    public void deleteMap(String name) {
        mapsInEdit.remove(name);
    }
}