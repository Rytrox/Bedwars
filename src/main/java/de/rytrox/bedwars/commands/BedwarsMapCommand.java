package de.rytrox.bedwars.commands;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Location;
import de.rytrox.bedwars.database.entity.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BedwarsMapCommand implements CommandExecutor, TabCompleter {

    /*
    - (1) bwmap help
    - (1) bwmap list
    - (1) bwmap listinedit
    - (2) bwmap create (map)
    - (2) bwmap edit (map)
    - (2) bwmap save (map)
    - (2) bwmap check (map)
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
                        Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
                            List<String> maps = main.getMapRepository().findAllMapsWithName();
                            final String[] returnString = {"&7Maps (" + maps.size() + "): "};
                            maps.forEach((map -> returnString[0] += "&c" + map + "&7, "));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    returnString[0].substring(0, returnString[0].length() - 2)));
                        });
                        return true;
                    case "listineditor":
                        List<String> maps = main.getMapUtils().getMapNames();
                        final String[] returnString = {"&7MapsInEditor (" + maps.size() + "): "};
                        maps.forEach((map -> returnString[0] += "&c" + map + "&7, "));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                returnString[0].substring(0, returnString[0].length() - 2)));
                        return true;
                }
                break;
            case 2:
                switch (args[0]) {
                    case "create":
                        Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
                            if (!main.getMapRepository().findAllMapsWithName().contains(args[1])
                                    && !main.getMapUtils().getMapNames().contains(args[1])) {
                                main.getMapUtils().getOrCreateMap(args[1]);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&cDue hast die Map &7" + args[1] + "&c erfolgreich erstellt."));
                            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Map &7" + args[1] + "&c existiert bereits!"));
                        });
                        return true;
                    case "edit":
                        Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
                            if (main.getMapRepository().findByName(args[1]).isPresent()) {
                                if (!main.getMapUtils().getMapNames().contains(args[1])) {
                                    Map map = main.getMapRepository().findByName(args[1]).get();
                                    main.getMapUtils().getMapsInEdit().put(map.getName(), map);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&cDie Map &7" + args[1] + "&c nun im EditMode!"));
                                } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&cDie Map &7" + args[1] + "&c ist bereits im EditMode!"));
                            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Map &7" + args[1] + "&c existiert nicht!"));
                        });
                        return true;
                    case "save":
                        if (main.getMapUtils().getMapNames().contains(args[1])) {
                            if (main.getMapUtils().getMapsInEdit().get(args[1]).checkComplete()) {
                                main.getMapRepository().saveMap(main.getMapUtils().getMapsInEdit().get(args[1]));
                                main.getMapUtils().deleteMap(args[1]);
                            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDer Map &7" + args[1] + "&c fehlen noch wichtige Objekte!"));
                        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                        return true;
                    case "check":
                        if (main.getMapUtils().getMapNames().contains(args[1])) {
                            if (main.getMapUtils().getMapsInEdit().get(args[1]).checkComplete())
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&cDie Map &7" + args[1] + "&c ist vollständig!"));
                            else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDer Map &7" + args[1] + "&c fehlen noch wichtige Objekte!"));
                        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                        return true;
                    case "remove":
                        Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
                            if (main.getMapRepository().findByName(args[1]).isPresent() || main.getMapUtils().getMapNames().contains(args[1])) {
                                main.getMapRepository().findByName(args[1]).ifPresent(map -> main.getMapRepository().deleteMap(map));
                                if (main.getMapUtils().getMapNames().contains(args[1])) main.getMapUtils().deleteMap(args[1]);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&cDu hast die Map &7" + args[1] + "&c entfernt!"));
                            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Map &7" + args[1] + "&c existiert nicht!"));
                        });
                        return true;
                }
                break;
            case 3:
                if ("modify".equals(args[0])) {
                    switch (args[2]) {
                        case "pos1":
                            if (main.getMapUtils().getMapNames().contains(args[1])) {
                                main.getMapUtils().getMapsInEdit().get(args[1]).setPos1(new Location(player.getLocation()));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&cPos1 für &7" + args[1] + "&c erfolgreich geändert!"));
                            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                            return true;
                        case "pos2":
                            if (main.getMapUtils().getMapNames().contains(args[1])) {
                                main.getMapUtils().getMapsInEdit().get(args[1]).setPos2(new Location(player.getLocation()));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&cPos2 für &7" + args[1] + "&c erfolgreich geändert!"));
                            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                            return true;
                    }
                }
                break;
            case 4:
                if ("modify".equals(args[0])) {
                    switch (args[2]) {
                        case "teamsize":
                            if(main.getMapUtils().getMapNames().contains(args[1])) {
                                main.getMapUtils().getMapsInEdit().get(args[1]).setTeamsize(Integer.parseInt(args[3]));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&cDie Teamgröße für &7" + args[1] + "&c ist nun &7" + Integer.parseInt(args[3]) + "&c!"));
                            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                            return true;
                        case "spawner":
                            if("remove".equals(args[3])) {
                                if(main.getMapUtils().getMapNames().contains(args[1])) {

                                } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
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
                                if(main.getMapUtils().getMapNames().contains(args[1])) {

                                } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                                return true;
                            }
                            break;
                        case "teams":
                            switch (args[3]) {
                                case "add":
                                    if(main.getMapUtils().getMapNames().contains(args[1])) {

                                    } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                                    return true;
                                case "remove":
                                    if(main.getMapUtils().getMapNames().contains(args[1])) {

                                    } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
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
                            if(main.getMapUtils().getMapNames().contains(args[1])) {

                            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                            return true;
                        case "bed":
                            if(main.getMapUtils().getMapNames().contains(args[1])) {

                            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                            return true;
                        case "spawn":
                            if(main.getMapUtils().getMapNames().contains(args[1])) {

                            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                            return true;
                    }
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cBitte benutze &7/help&c!"));
                return true;
            case 7:
                if ("modify".equals(args[0]) && "teams".equals(args[2]) && "modify".equals(args[3]) && "color".equals(args[5])) {
                    if(main.getMapUtils().getMapNames().contains(args[1])) {

                    } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                    return true;
                }
                break;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cBitte benutze &7/help&c!"));
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final List<String> list = new LinkedList<>();
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDiser Command kann nur von einem Spieler ausgeführt werden!"));
            return list;
        }
        Player player = (Player) sender;
        switch (args.length) {
            case 1:
                list.add("create");
                list.add("modify");
                list.add("save");
                list.add("remove");
                list.add("check");
                list.add("list");
                list.add("listineditor");
                break;
            case 2:
                if ("modify".equals(args[0]) || "save".equals(args[0]) || "remove".equals(args[0]) || "check".equals(args[0])) {
                    list.addAll(main.getMapUtils().getMapNames());
                }
                break;
            case 3:
                if ("modify".equals(args[0]) && main.getMapUtils().getMapNames().contains(args[1])) {
                    list.add("pos1");
                    list.add("pos2");
                    list.add("teamsize");
                    list.add("spawner");
                    list.add("teams");
                }
                break;
            case 4:
                if ("modify".equals(args[0]) && main.getMapUtils().getMapNames().contains(args[1])) {
                    if ("spawner".equals(args[2]) || "teams".equals(args[2])) {
                        list.add("add");
                        list.add("remove");
                    }
                    if ("teams".equals(args[2])) list.add("modify");
                }
                break;
            case 5:
                if ("modify".equals(args[0]) && main.getMapUtils().getMapNames().contains(args[1]) && "teams".equals(args[2])) {
                    if("remove".equals(args[3]) || "modify".equals(args[3])) {

                        // Add teamnames to the list!

                    }
                }
                break;
            case 6:
                if ("modify".equals(args[0]) && main.getMapUtils().getMapNames().contains(args[1]) && "teams".equals(args[2]) && "modify".equals(args[3])) {
                    if(true /* Check if args[4] is a valid teamname */) {
                        list.add("villager");
                        list.add("bed");
                        list.add("spawn");
                        list.add("color");
                    }
                }
                break;
            case 7:
                if ("modify".equals(args[0]) && main.getMapUtils().getMapNames().contains(args[1]) && "teams".equals(args[2]) && "modify".equals(args[3])
                        && true /* Check if args[4] is a valid teamname */ && "color".equals(args[5])) {
                    Arrays.stream(ChatColor.values())
                            .map(ChatColor::getChar)
                            .map(String::valueOf)
                            .forEach(list::add);
                }
                break;
        }
        return list;
    }
}
