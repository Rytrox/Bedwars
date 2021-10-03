package de.rytrox.bedwars.phase.events;

import de.rytrox.bedwars.phase.phases.GamePhase;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event that will be triggered when a phase changes
 *
 */
public class PhaseChangeEvent extends PhaseEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private GamePhase next;
    private boolean cancel;

    public PhaseChangeEvent(@NotNull GamePhase current) {
        super(current);

        this.next = current.next();
    }

    /**
     * Returns the handlerlist of this event
     *
     * @return the handlerlist of the event that contains all listener
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return getHandlerList();
    }

    /**
     * Required for Bukkit to run properly
     *
     * @return the Handlerlist of this event
     */
    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Returns the next phase which is going to be enabled
     *
     * @return the next phase. Cannot be null
     */
    @NotNull
    public GamePhase getNextPhase() {
        return next;
    }

    /**
     * Sets the next phase which is going to be enabled
     *
     * @param next the next phase. Cannot be null
     */
    public void setNextPhase(@NotNull GamePhase next) {
        this.next = next;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancel = b;
    }
}
