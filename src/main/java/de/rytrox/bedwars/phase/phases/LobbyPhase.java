package de.rytrox.bedwars.phase.phases;

import de.rytrox.bedwars.Bedwars;

import de.rytrox.bedwars.listeners.LobbyBreakPlaceListener;
import de.rytrox.bedwars.map.MapLoader;
import de.rytrox.bedwars.team.TeamManager;
import de.rytrox.bedwars.utils.Countdown;
import de.rytrox.bedwars.utils.LobbyArea;
import de.rytrox.bedwars.utils.TopTenBoardHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class LobbyPhase extends GamePhase {

    private final TeamManager teamManager;
    private final MapLoader mapLoader;
    private final Countdown countdown;
    private final TopTenBoardHandler topTenBoardHandler;
    private final LobbyBreakPlaceListener lobbyBreakPlaceListener;
    private final LobbyArea lobbyArea;

    public LobbyPhase(Bedwars main) {

        super(main);
        this.lobbyArea = new LobbyArea();
        this.teamManager = new TeamManager();
        this.mapLoader = new MapLoader(main, teamManager);
        this.countdown = new Countdown(main);
        this.topTenBoardHandler = new TopTenBoardHandler();
        this.lobbyBreakPlaceListener = new LobbyBreakPlaceListener();

    }

    /**
     * This method will be executed when a GamePhase is enabled. <br>
     * Only one Phase can run at the same time!
     */
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this.lobbyArea, main);
        Bukkit.getPluginManager().registerEvents(teamManager, main);
        Bukkit.getPluginManager().registerEvents(this.countdown, main);
        Bukkit.getPluginManager().registerEvents(this.topTenBoardHandler, main);
        Bukkit.getPluginManager().registerEvents(this.lobbyBreakPlaceListener, main);
    }

    /**
     * This method will be called when the current phase will be shut down. <br>
     * The next phase will be started after disabling the old phase
     */
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(lobbyArea);
        HandlerList.unregisterAll(teamManager);
        HandlerList.unregisterAll(countdown);
        HandlerList.unregisterAll(topTenBoardHandler);
        HandlerList.unregisterAll(lobbyBreakPlaceListener);
    }

    /**
     * Gets an instance of the next phase.
     *
     * @return the next Phase. Cannot be null
     */
    @Override
    public @NotNull GamePhase next() {
        return new IngamePhase(main, mapLoader.getMap(), teamManager);
    }
}
