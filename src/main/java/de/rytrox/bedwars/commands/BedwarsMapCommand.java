package de.rytrox.bedwars.commands;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.map.MapLoadEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BedwarsMapCommand implements CommandExecutor {

    /*
    - (1) bwmap help
    - (1) bwmap list
    - (1) bwmap listinedit
    - (2) bwmap create (map)
    - (2) bwmap edit (map)
    - (2) bwmap save (map)
    - (2) bwmap remove (map)
    - (3) bwmap modify (map) pos1
    - (3) bwmap modify (map) pos2
    - (4) bwmap modify (map) teamsize (amount)
    - (4) bwmap modify (map) spawner remove
    - (5) bwmap modify (map) spawner add (material)
    - (5) bwmap modify (map) teams add (teamname)
    - (5) bwmap modify (map) teams remove (teamname)
    - (6) bwmap modify (map) teams modify (teamname) villager
    - (6) bwmap modify (map) teams modify (teamname) bed
    - (6) bwmap modify (map) teams modify (teamname) spawn
    - (7) bwmap modify (map) teams modify (teamname) color (color)
     */

    private final Bedwars main = JavaPlugin.getPlugin(Bedwars.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDiser Command kann nur von einem Spieler ausgeführt werden!"));
            return true;
        }
        Player player = (Player) sender;
        switch (args.length) {
            case 1:
                switch (args[0]) {
                    case "help":

                        return true;
                    case "list":

                        return true;
                    case "listineditor":

                        return true;
                }
                break;
            case 2:
                switch (args[0]) {
                    case "create":

                        return true;
                    case "edit":

                        return true;
                    case "save":

                        break;
                    case "remove":

                        return true;
                }
                break;
            case 3:
                if ("modify".equals(args[0])) {
                    switch (args[2]) {
                        case "pos1":

                            return true;
                        case "pos2":

                            return true;
                    }
                }
                break;
            case 4:
                if ("modify".equals(args[0])) {
                    switch (args[2]) {
                        case "teamsize":

                            return true;
                        case "spawner":
                            if("remove".equals(args[3])) {

                                return true;
                            }
                            break;
                    }
                }
                break;
            case 5:
                if ("modify".equals(args[0])) {
                    switch (args[2]) {
                        case "spawner":
                            if("add".equals(args[3])) {

                                return true;
                            }
                            break;
                        case "teams":
                            switch (args[3]) {
                                case "add":

                                    return true;
                                case "remove":

                                    return true;
                            }
                            break;
                    }
                }
                break;
            case 6:
                if ("modify".equals(args[0]) && "teams".equals(args[2]) && "modify".equals(args[3])) {
                    switch (args[5]) {
                        case "villager":

                            return true;
                        case "bed":

                            return true;
                        case "spawn":

                            return true;
                    }
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cBitte benutze &7/help&c!"));
                return true;
            case 7:
                if ("modify".equals(args[0]) && "teams".equals(args[2]) && "modify".equals(args[3]) && "color".equals(args[5])) {

                }
                break;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cBitte benutze &7/help&c!"));
        return true;
    }

    @EventHandler
    public void onMapLoad(MapLoadEvent event) {
        main.getMapUtils().getMapsInEdit().add(event.getMap());
    }
}
