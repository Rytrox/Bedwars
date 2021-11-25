package de.rytrox.bedwars.phase.phases;

import de.rytrox.bedwars.Bedwars;

import de.rytrox.bedwars.database.repository.TopTenSignsRepository;
import de.rytrox.bedwars.utils.Countdown;
import de.rytrox.bedwars.utils.TopTenBoardHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class LobbyPhase extends GamePhase {

    private final Countdown countdown;
    private final TopTenBoardHandler topTenBoardHandler;

    public LobbyPhase(Bedwars main) {
        super(main);

        this.countdown = new Countdown(main);
        this.topTenBoardHandler = new TopTenBoardHandler();
    }

    /**
     * This method will be executed when a GamePhase is enabled. <br>
     * Only one Phase can run at the same time!
     */
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this.countdown, main);
        Bukkit.getPluginManager().registerEvents(this.topTenBoardHandler, main);
    }

    /**
     * This method will be called when the current phase will be shut down. <br>
     * The next phase will be started after disabling the old phase
     */
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(countdown);
        HandlerList.unregisterAll(topTenBoardHandler);
    }

    /**
     * Gets an instance of the next phase.
     *
     * @return the next Phase. Cannot be null
     */
    @Override
    public @NotNull GamePhase next() {
        return new IngamePhase(main);
    }
}
