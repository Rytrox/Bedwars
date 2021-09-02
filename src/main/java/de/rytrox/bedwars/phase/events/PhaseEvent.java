package de.rytrox.bedwars.phase.events;


import de.rytrox.bedwars.phase.phases.GamePhase;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * Event that is called when something happens with phases
 */
public abstract class PhaseEvent extends Event {

    private final GamePhase phase;

    public PhaseEvent(@NotNull GamePhase phase) {
        this.phase = phase;
    }

    /**
     * Returns the current phase that is running
     *
     * @return the current phase. Cannot be null
     */
    @NotNull
    public GamePhase getPhase() {
        return phase;
    }
}
