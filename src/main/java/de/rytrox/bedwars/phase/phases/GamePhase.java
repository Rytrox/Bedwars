package de.rytrox.bedwars.phase.phases;

import de.rytrox.bedwars.Bedwars;
import org.jetbrains.annotations.NotNull;

/**
 * Interface that handles the different GamePhases
 *
 */
public abstract class GamePhase {

    protected Bedwars main;

    public GamePhase(Bedwars main) {
        this.main = main;
    }

    /**
     * This method will be executed when a GamePhase is enabled. <br>
     * Only one Phase can run at the same time!
     */
    public abstract void onEnable();

    /**
     * This method will be called when the current phase will be shut down. <br>
     * The next phase will be started after disabling the old phase
     */
    public abstract void onDisable();

    /**
     * Gets an instance of the next phase.
     *
     * @return the next Phase. Cannot be null
     */
    @NotNull
    public abstract GamePhase next();
}
