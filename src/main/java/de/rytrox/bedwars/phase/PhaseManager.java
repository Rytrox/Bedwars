package de.rytrox.bedwars.phase;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.phase.events.PhaseChangeEvent;
import de.rytrox.bedwars.phase.phases.GamePhase;
import de.rytrox.bedwars.phase.phases.LobbyPhase;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class PhaseManager {

    private GamePhase currentPhase;

    public PhaseManager(Bedwars main) {

        this.currentPhase = new LobbyPhase(main);
        this.currentPhase.onEnable();
    }

    /**
     * Returns the game phase that is currently running.
     *
     * @return the current game phase
     */
    @NotNull
    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    /**
     * Calls the {@link PhaseChangeEvent} and initialized the new phase when event succeed. <br>
     * <br>
     * Disables the old phase before starting the new phase
     */
    public void next() {
        // Call API-Event
        PhaseChangeEvent event = new PhaseChangeEvent(this.currentPhase);
        Bukkit.getPluginManager().callEvent(event);

        // Only continue when event is not cancelled
        if(!event.isCancelled()) {
            // disable old phase
            event.getPhase().onDisable();

            // declare and enable new phase
            this.currentPhase = event.getNextPhase();
            this.currentPhase.onEnable();
        }
    }
}
