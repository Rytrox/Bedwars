package de.rytrox.bedwars.phase.phases;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.items.BedwarsTNT;
import de.rytrox.bedwars.items.Bridge;
import de.rytrox.bedwars.items.RescuePlatform;
import de.rytrox.bedwars.listeners.BedBreakListener;
import de.rytrox.bedwars.listeners.KillDeathListener;
import de.rytrox.bedwars.listeners.ShopListener;
import de.rytrox.bedwars.listeners.BuildBreakListener;
import de.rytrox.bedwars.map.MapManager;
import de.rytrox.bedwars.team.TeamManager;
import de.rytrox.bedwars.utils.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class IngamePhase extends GamePhase {

    private final Map map;
    private final TeamManager teamManager;
    private final ScoreboardManager scoreboardManager;
    private final KillDeathListener killDeathListener;
    private final BedBreakListener bedBreakListener;
    private final BuildBreakListener buildBreakListener;
    private final ShopListener shopListener;
    private final RescuePlatform rescuePlatform;
    private final BedwarsTNT bedwarsTNT;
    private final Bridge bridge;

    private final MapManager mapManager;

    public IngamePhase(Bedwars main, Map map, TeamManager teamManager) {
        super(main);

        this.map = map;
        this.teamManager = teamManager;
        this.scoreboardManager = new ScoreboardManager(map.getName(), teamManager);
        this.killDeathListener = new KillDeathListener(main, teamManager, scoreboardManager);
        this.bedBreakListener = new BedBreakListener(map, teamManager, scoreboardManager);
        this.buildBreakListener = new BuildBreakListener(map);
        this.shopListener = new ShopListener(main);
        this.rescuePlatform = new RescuePlatform(main);
        this.bedwarsTNT = new BedwarsTNT();
        this.bridge = new Bridge(main);

        this.mapManager = new MapManager(map, this.scoreboardManager, teamManager);
    }

    /**
     * This method will be executed when a GamePhase is enabled. <br>
     * Only one Phase can run at the same time!
     */
    @Override
    public void onEnable() {
        // register Listeners
        Bukkit.getPluginManager().registerEvents(killDeathListener, main);
        Bukkit.getPluginManager().registerEvents(bedBreakListener, main);
        Bukkit.getPluginManager().registerEvents(buildBreakListener, main);
        Bukkit.getPluginManager().registerEvents(shopListener, main);
        Bukkit.getPluginManager().registerEvents(rescuePlatform, main);
        Bukkit.getPluginManager().registerEvents(bedwarsTNT, main);
        Bukkit.getPluginManager().registerEvents(bridge, main);

        map.fillAliveTeams();
        mapManager.deleteAllGameEntities();
        mapManager.summonShops();
        mapManager.summonSpawners();
        mapManager.teleportPlayersAndClearInventories();
        mapManager.showScoreboards();

        teamManager.checkForWin();
    }

    /**
     * This method will be called when the current phase will be shut down. <br>
     * The next phase will be started after disabling the old phase
     */
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(killDeathListener);
        HandlerList.unregisterAll(bedBreakListener);
        HandlerList.unregisterAll(buildBreakListener);
        HandlerList.unregisterAll(shopListener);
        HandlerList.unregisterAll(rescuePlatform);
        HandlerList.unregisterAll(bedwarsTNT);
        HandlerList.unregisterAll(bridge);

        mapManager.killShops();
        mapManager.stopSpawners();
        mapManager.removeScoreboards();
        mapManager.deleteAllGameEntities();
    }

    /**
     * Gets an instance of the next phase.
     *
     * @return the next Phase. Cannot be null
     */
    @Override
    public @NotNull GamePhase next() {
        return new EndPhase(main, buildBreakListener.getPlacedBlocks());
    }
}
