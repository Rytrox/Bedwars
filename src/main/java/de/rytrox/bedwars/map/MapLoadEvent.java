package de.rytrox.bedwars.map;

import de.rytrox.bedwars.database.entity.Map;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MapLoadEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private Map map;

    public MapLoadEvent(Map map) {
        this.map = map;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * Gibt die geladene Map zurück
     *
     * @return die angeforderte Map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Ersetzt die geladene Map mit einer neuen
     *
     * @param map die neue Map
     */
    public void setMap(Map map) {
        this.map = map;
    }
}
