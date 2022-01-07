package de.rytrox.bedwars.commands;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.phase.phases.LobbyPhase;
import de.rytrox.bedwars.utils.Area;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class LobbyCommand implements TabExecutor {
    private final Bedwars main = (Bedwars)JavaPlugin.getPlugin(Bedwars.class);

    public LobbyCommand() {
    }

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player && this.main.getPhaseManager().getCurrentPhase() instanceof LobbyPhase && ((LobbyPhase)this.main.getPhaseManager().getCurrentPhase()).getLobbyArea() != null && strings.length == 9) {
            Location start;
            Location end;
            Location spawn;
            try {
                start = new Location(((Player)commandSender).getWorld(), 0.5D + (double)Integer.parseInt(strings[0]), 0.5D + (double)Integer.parseInt(strings[1]), 0.5D + (double)Integer.parseInt(strings[2]));
                end = new Location(((Player)commandSender).getWorld(), 0.5D + (double)Integer.parseInt(strings[3]), 0.5D + (double)Integer.parseInt(strings[4]), 0.5D + (double)Integer.parseInt(strings[5]));
                spawn = new Location(((Player)commandSender).getWorld(), 0.5D + (double)Integer.parseInt(strings[6]), 0.5D + (double)Integer.parseInt(strings[7]), 0.5D + (double)Integer.parseInt(strings[8]));
                if (new Area().inArea(start, end, spawn)) {
                    ((LobbyPhase)this.main.getPhaseManager().getCurrentPhase()).getLobbyArea().setLobbyLocations(spawn, start, end);
                    commandSender.sendMessage("parameters changed succesfully");
                    return true;
                }
            } catch (NumberFormatException var9) {
                commandSender.sendMessage("please only integer");
                return false;
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList();
        if ((args.length == 1 || args.length == 4 || args.length == 7) && sender instanceof Player && (((Player)sender).getLocation().getBlockX() + "").toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
            list.add(((Player)sender).getLocation().getBlockX() + "");
        } else if ((args.length == 2 || args.length == 5 || args.length == 8) && sender instanceof Player && (((Player)sender).getLocation().getBlockY() + "").toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
            list.add(((Player)sender).getLocation().getBlockY() + "");
        } else if ((args.length == 3 || args.length == 6 || args.length == 9) && sender instanceof Player && (((Player)sender).getLocation().getBlockZ() + "").toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
            list.add(((Player)sender).getLocation().getBlockZ() + "");
        }

        String[] description = new String[]{"Area Start X", "Area Start Y", "Area Start Z", "Area End X", "Area End Y", "Area End Z", "Spawn X", "Spawn Y", "Spawn Z"};

        for(int i = 0; i < description.length; ++i) {
            if (args.length - 1 == i) {
                list.add(description[i]);
            }
        }

        if (args.length > 1) {
            try {
                Integer.parseInt(args[args.length - 2]);
            } catch (NumberFormatException var8) {
                list.add("the one before is not an Intager ;)");
            }
        }

        return list;
    }
}

