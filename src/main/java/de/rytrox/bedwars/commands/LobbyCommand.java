package de.rytrox.bedwars.commands;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.phase.phases.LobbyPhase;
import de.rytrox.bedwars.phase.phases.SetupPhase;
import de.rytrox.bedwars.utils.Area;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import de.rytrox.bedwars.utils.LobbyArea;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LobbyCommand implements TabExecutor {
    private final Bedwars main = (Bedwars)JavaPlugin.getPlugin(Bedwars.class);

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSender instanceof Player && this.main.getPhaseManager().getCurrentPhase() instanceof SetupPhase) {
            Location playerLocation = ((Player) commandSender).getLocation();
            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "pos1":
                    main.getConfig().set("lobby.world", playerLocation.getWorld().getName());
                    main.getConfig().set("lobby.pos1.x", playerLocation.getBlockX());
                    main.getConfig().set("lobby.pos1.y", playerLocation.getBlockY());
                    main.getConfig().set("lobby.pos1.z", playerLocation.getBlockZ());
                    commandSender.sendMessage("position 1 set");
                    break;
                case "pos2":
                    main.getConfig().set("lobby.pos2.x", playerLocation.getBlockX());
                    main.getConfig().set("lobby.pos2.y", playerLocation.getBlockY());
                    main.getConfig().set("lobby.pos2.z", playerLocation.getBlockZ());
                    commandSender.sendMessage("position 2 set");
                    break;
                case "spawn":
                    main.getConfig().set("lobby.spawn.x", playerLocation.getBlockX() + 0.5);
                    main.getConfig().set("lobby.spawn.y", playerLocation.getBlockY());
                    main.getConfig().set("lobby.spawn.z", playerLocation.getBlockZ() + 0.5);
                    main.getConfig().set("lobby.spawn.yaw", playerLocation.getYaw());
                    main.getConfig().set("lobby.spawn.pitch", playerLocation.getPitch());
                    commandSender.sendMessage("spawn set");
                default:
                    return false;
            }
            main.saveConfig();
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return Arrays.asList("pos1", "pos2", "spawn");
    }
}

