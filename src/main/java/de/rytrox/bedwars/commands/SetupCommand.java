package de.rytrox.bedwars.commands;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.phase.phases.SetupPhase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SetupCommand implements CommandExecutor {

    private final Bedwars main = JavaPlugin.getPlugin(Bedwars.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (main.getPhaseManager().getCurrentPhase() instanceof SetupPhase) {
            main.getPhaseManager().next();
            sender.sendMessage("Setupmodus deaktiviert!");
        } else {
            main.getPhaseManager().enableSetupMode();
            sender.sendMessage("Setupmodus aktiviert!");
        }
        return true;
    }
}
