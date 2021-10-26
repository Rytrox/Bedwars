package de.rytrox.bedwars.map;

import de.rytrox.bedwars.database.entity.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapUtils {

    private final java.util.Map<String, Map> mapsInEdit = new HashMap<>();

    /**
     * Erstellt eine Map, wenn nötig und gibt diese zurück
     * Ansonsten wird die vorhandene Map zurückgegeben
     *
     * @param name der name der Map, die gesucht wird oder erstellt werden soll
     * @return die gefundene oder erstellte Map
     */
    public Map getOrCreateMap(String name) {
        return mapsInEdit.computeIfAbsent(name, Map::new);
    }

    /**
     * Gibt die Namen alles Maps im EditMode aus
     *
     * @return die Namen aller Maps im EditMode
     */
    public List<String> getMapNames() {
        return new ArrayList<>(mapsInEdit.keySet());
    }

    /**
     * Gibt die HashMaps mit allen Maps im EditMode zurück
     *
     * @return die HashMaps mit allen Maps im EditMode
     */
    public java.util.Map<String, Map> getMapsInEdit() {
        return mapsInEdit;
    }

    /**
     * löscht eine Map aus dem EditMode
     *
     * @param name die zu löschende Map
     */
    public void deleteMap(String name) {
        mapsInEdit.remove(name);
    }
}