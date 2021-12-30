package de.rytrox.bedwars.phase.phases;

import de.rytrox.bedwars.Bedwars;
import org.jetbrains.annotations.NotNull;

public class SetupPhase extends GamePhase {

    public SetupPhase(Bedwars main) {
        super(main);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public @NotNull GamePhase next() {
        return new LobbyPhase(main);
    }
}
