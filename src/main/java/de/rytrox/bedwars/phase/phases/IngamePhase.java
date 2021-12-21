package de.rytrox.bedwars.phase.phases;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.items.BedwarsTNT;
import de.rytrox.bedwars.items.Bridge;
import de.rytrox.bedwars.items.Rettungsplatform;
import de.rytrox.bedwars.listeners.ShopListener;
import de.rytrox.bedwars.listeners.BuildBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class IngamePhase extends GamePhase {

    private final BuildBreakListener buildBreakListener;
    private final ShopListener shopListener;
    private final Rettungsplatform rettungsplatform;
    private final BedwarsTNT bedwarsTNT;
    private final Bridge bridge;

    private final Map map;

    public IngamePhase(Bedwars main, Map map) {
        super(main);

        this.buildBreakListener = new BuildBreakListener();
        this.shopListener = new ShopListener(main);
        this.rettungsplatform = new Rettungsplatform(main);
        this.bedwarsTNT = new BedwarsTNT();
        this.bridge = new Bridge(main);

        this.map = map;
    }

    /**
     * This method will be executed when a GamePhase is enabled. <br>
     * Only one Phase can run at the same time!
     */
    @Override
    public void onEnable() {
        // register Listeners
        Bukkit.getPluginManager().registerEvents(buildBreakListener, main);
        Bukkit.getPluginManager().registerEvents(shopListener, main);
        Bukkit.getPluginManager().registerEvents(rettungsplatform, main);
        Bukkit.getPluginManager().registerEvents(bedwarsTNT, main);
        Bukkit.getPluginManager().registerEvents(bridge, main);
    }

    /**
     * This method will be called when the current phase will be shut down. <br>
     * The next phase will be started after disabling the old phase
     */
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(buildBreakListener);
        HandlerList.unregisterAll(shopListener);
        HandlerList.unregisterAll(rettungsplatform);
        HandlerList.unregisterAll(bedwarsTNT);
        HandlerList.unregisterAll(bridge);
    }

    /**
     * Gets an instance of the next phase.
     *
     * @return the next Phase. Cannot be null
     */
    @Override
    public @NotNull GamePhase next() {
        return new EndPhase(main);
    }

    public Map getMap() {
        return map;
    }
}
