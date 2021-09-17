package de.rytrox.bedwars.phase.phases;

import de.rytrox.bedwars.Bedwars;

import de.rytrox.bedwars.utils.Countdown;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class LobbyPhase extends GamePhase {


    public LobbyPhase(Bedwars main) {
        super(main);
    }

    /**
     * This method will be executed when a GamePhase is enabled. <br>
     * Only one Phase can run at the same time!
     */
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Countdown(), main);
    }

    /**
     * This method will be called when the current phase will be shut down. <br>
     * The next phase will be started after disabling the old phase
     */
    @Override
    public void onDisable() {
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
