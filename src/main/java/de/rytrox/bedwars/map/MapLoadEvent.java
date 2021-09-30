package de.rytrox.bedwars.map;

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

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
