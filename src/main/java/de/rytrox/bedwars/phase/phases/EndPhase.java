package de.rytrox.bedwars.phase.phases;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.utils.MapReset;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Endphase which is called when a Winner has been declared
 */
public class EndPhase extends GamePhase {

    private Set<Block> blocks;

    public EndPhase(Bedwars main, Set<Block> blocks) {
        super(main);
        this.blocks = blocks;
    }


    /**
     * This method will be executed when a GamePhase is enabled. <br>
     * Only one Phase can run at the same time!
     */
    @Override
    public void onEnable() {
        new MapReset(blocks);
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
        return new LobbyPhase(main);
    }
}
