package de.rytrox.bedwars.commands;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Location;
import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.database.entity.Spawner;
import de.rytrox.bedwars.database.entity.Team;
import de.rytrox.bedwars.database.enums.SpawnerMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BedwarsMapCommand implements TabExecutor {

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
    - (5) bwmap modify (map) spawner add (material)
    - (5) bwmap modify (map) spawner remove (distance)
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
                                        "&cDu hast die Map &7" + args[1] + "&c erfolgreich erstellt."));
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
                                Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
                                    main.getMapRepository().saveMap(main.getMapUtils().getMapsInEdit().get(args[1]));
                                    main.getMapUtils().getMapsInEdit().remove(args[1]);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&cDie Map &7" + args[1] + "&c wurde gespeichert!"));
                                });
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
                if ("modify".equals(args[0]) && "teamsize".equals(args[2])) {
                    if(main.getMapUtils().getMapNames().contains(args[1])) {
                        try {
                            main.getMapUtils().getMapsInEdit().get(args[1]).setTeamsize(Integer.parseInt(args[3]));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Teamgröße für &7" + args[1] + "&c ist nun &7" + Integer.parseInt(args[3]) + "&c!"));
                        } catch (NumberFormatException exception) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Zahl &7" + args[3] + "&c ist nicht gültig!"));
                        }
                    } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                    return true;
                }
                break;
            case 5:
                if ("modify".equals(args[0])) {
                    switch (args[2]) {
                        case "spawner":
                            switch (args[3]) {
                                case "add":
                                    if(main.getMapUtils().getMapNames().contains(args[1])) {
                                        if (Arrays.stream(SpawnerMaterial.values())
                                                .map(SpawnerMaterial::toString)
                                                .collect(Collectors.toList())
                                                .contains(args[4])) {
                                            main.getMapUtils().getMapsInEdit().get(args[1])
                                                    .addSpwner(new Spawner(SpawnerMaterial.valueOf(args[4]), new Location(player.getLocation())));
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                    "&cDu hast einen Spawner zur Map &7" + args[1] + "&c hinzugefügt!"));
                                        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                "&cDas Material &7" + args[4] + "&c existiert nicht!"));
                                    } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                                    return true;
                                case "remove":
                                    if(main.getMapUtils().getMapNames().contains(args[1])) {
                                        try {
                                            main.getMapUtils().getMapsInEdit().get(args[1]).removeSpawner(player.getLocation(), Double.parseDouble(args[4]));
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                    "&cDu hast alle spawner im Umkreis von &7" + Integer.parseInt(args[4]) + "&c gelöscht!"));
                                        } catch (NumberFormatException exception) {
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                    "&cDie Zahl &7" + args[4] + "&c ist nicht gültig!"));
                                        }
                                    } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                                    return true;
                            }
                            break;
                        case "teams":
                            switch (args[3]) {
                                case "add":
                                    if(main.getMapUtils().getMapNames().contains(args[1])) {
                                        if (!main.getMapUtils().getMapsInEdit().get(args[1]).getTeams()
                                                .stream()
                                                .map(Team::getName)
                                                .collect(Collectors.toList())
                                                .contains(args[4])) {
                                            main.getMapUtils().getMapsInEdit().get(args[1]).addTeam(new Team(args[4]));
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                    "&cDas Team &7" + args[4] + "&c wurde zur Map &7" + args[1] + "&c hinzugefügt!"));
                                        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                "&cDas Team &7" + args[4] + "&c existiert bereits!"));
                                    } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                                    return true;
                                case "remove":
                                    if(main.getMapUtils().getMapNames().contains(args[1])) {
                                        if (main.getMapUtils().getMapsInEdit().get(args[1]).getTeams()
                                                .stream()
                                                .map(Team::getName)
                                                .collect(Collectors.toList())
                                                .contains(args[4])) {
                                            main.getMapUtils().getMapsInEdit().get(args[1]).getTeams()
                                                    .stream()
                                                    .filter(team -> team.getName().equals(args[4]))
                                                    .findFirst()
                                                    .ifPresent(team -> main.getMapUtils().getMapsInEdit().get(args[1]).removeTeam(team));
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                    "&cDas Team &7" + args[4] + "&c wurde gelöscht!"));
                                        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                "&cDas Team &7" + args[4] + "&c existiert nicht!"));
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
                                main.getMapUtils().getMapsInEdit().get(args[1]).getTeams()
                                        .stream()
                                        .filter(team -> team.getName().equals(args[4]))
                                        .findFirst()
                                        .ifPresentOrElse(team -> {
                                            team.setVillager(new Location(player.getLocation()));
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                    "&cDie Villager Position für &7" + args[4] + "&c wurde gesetzt!"));
                                        },() -> player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                "&cDas Team &7" + args[4] + "&c existiert nicht!")));
                            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                            return true;
                        case "bed":
                            if(main.getMapUtils().getMapNames().contains(args[1])) {
                                main.getMapUtils().getMapsInEdit().get(args[1]).getTeams()
                                        .stream()
                                        .filter(team -> team.getName().equals(args[4]))
                                        .findFirst()
                                        .ifPresentOrElse(team -> {
                                            team.setBed(new Location(player.getLocation()));
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                    "&cDie Bett Position für &7" + args[4] + "&c wurde gesetzt!"));
                                        },() -> player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                "&cDas Team &7" + args[4] + "&c existiert nicht!")));
                            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                            return true;
                        case "spawn":
                            if(main.getMapUtils().getMapNames().contains(args[1])) {
                                main.getMapUtils().getMapsInEdit().get(args[1]).getTeams()
                                        .stream()
                                        .filter(team -> team.getName().equals(args[4]))
                                        .findFirst()
                                        .ifPresentOrElse(team -> {
                                            team.setSpawn(new Location(player.getLocation()));
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                    "&cDie Spawn Position für &7" + args[4] + "&c wurde gesetzt!"));
                                        },() -> player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                                "&cDas Team &7" + args[4] + "&c existiert nicht!")));
                            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&cDie Map &7" + args[1] + "&c ist nicht im EditMode!"));
                            return true;
                    }
                }
                break;
            case 7:
                if ("modify".equals(args[0]) && "teams".equals(args[2]) && "modify".equals(args[3]) && "color".equals(args[5])) {
                    if(main.getMapUtils().getMapNames().contains(args[1])) {
                        main.getMapUtils().getMapsInEdit().get(args[1]).getTeams()
                                .stream()
                                .filter(team -> team.getName().equals(args[4]))
                                .findFirst()
                                .ifPresentOrElse(team -> {
                                    team.setColor(args[6].toCharArray()[0]);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&cDie Teamfarbe für &7" + args[4] + "&c wurde gesetzt!"));
                                },() -> player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&cDas Team &7" + args[4] + "&c existiert nicht!")));
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
        switch (args.length) {
            case 1:
                list.addAll(Arrays.asList("create", "modify", "save", "remove", "check", "list", "listineditor", "edit"));
                break;
            case 2:
                if ("modify".equals(args[0]) || "save".equals(args[0]) || "remove".equals(args[0]) || "check".equals(args[0])) {
                    list.addAll(main.getMapUtils().getMapNames());
                } else if("edit".equals(args[0])) {
                    list.addAll(main.getMapRepository().findAllMapsWithName());
                }
                break;
            case 3:
                if ("modify".equals(args[0]) && main.getMapUtils().getMapNames().contains(args[1])) {
                    list.addAll(Arrays.asList("pos1", "pos2", "teamsize", "spawner", "teams"));
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
                if ("modify".equals(args[0]) && main.getMapUtils().getMapNames().contains(args[1])) {
                    if("teams".equals(args[2]) && ("remove".equals(args[3]) || "modify".equals(args[3]))) {
                        main.getMapUtils().getMapsInEdit().get(args[1]).getTeams()
                                .stream()
                                .map(Team::getName)
                                .forEach(list::add);
                    } else if("spawner".equals(args[2]) && "add".equals(args[3])) {
                        Arrays.stream(SpawnerMaterial.values())
                                .map(SpawnerMaterial::toString)
                                .forEach(list::add);
                    }
                }
                break;
            case 6:
                if ("modify".equals(args[0]) && main.getMapUtils().getMapNames().contains(args[1]) && "teams".equals(args[2])
                        && "modify".equals(args[3]) && main.getMapUtils().getMapsInEdit().get(args[1]).getTeams()
                        .stream()
                        .map(Team::getName)
                        .collect(Collectors.toList())
                        .contains(args[4])) {
                    list.addAll(Arrays.asList("villager", "bed", "spawn", "color"));
                }
                break;
            case 7:
                if ("modify".equals(args[0]) && main.getMapUtils().getMapNames().contains(args[1]) && "teams".equals(args[2]) && "modify".equals(args[3])
                        && main.getMapUtils().getMapsInEdit().get(args[1]).getTeams()
                        .stream()
                        .map(Team::getName)
                        .collect(Collectors.toList())
                        .contains(args[4]) && "color".equals(args[5])) {
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
