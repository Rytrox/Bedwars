package de.rytrox.bedwars.phase.phases;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.utils.TopTenBoardHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SetupPhase extends GamePhase {

    private final TopTenBoardHandler topTenBoardHandler;

    public SetupPhase(Bedwars main) {
        super(main);

        this.topTenBoardHandler = new TopTenBoardHandler();
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(topTenBoardHandler, main);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(topTenBoardHandler);
    }

    @Override
    public @NotNull GamePhase next() {
        return new LobbyPhase(main);
    }
}
