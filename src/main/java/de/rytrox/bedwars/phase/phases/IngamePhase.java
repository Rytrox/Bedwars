package de.rytrox.bedwars.phase.phases;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.listeners.ShopListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class IngamePhase extends GamePhase {

    private final ShopListener shopListener;

    public IngamePhase(Bedwars main) {
        super(main);

        this.shopListener = new ShopListener(main);
    }

    /**
     * This method will be executed when a GamePhase is enabled. <br>
     * Only one Phase can run at the same time!
     */
    @Override
    public void onEnable() {
        // register Listeners
        Bukkit.getPluginManager().registerEvents(shopListener, main);
    }

    /**
     * This method will be called when the current phase will be shut down. <br>
     * The next phase will be started after disabling the old phase
     */
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(shopListener);
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
}
