package de.rytrox.bedwars.commands;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Location;
import de.rytrox.bedwars.database.entity.Map;
import de.rytrox.bedwars.database.entity.Spawner;
import de.rytrox.bedwars.database.entity.Team;
import de.rytrox.bedwars.database.enums.SpawnerMaterial;
import de.rytrox.bedwars.phase.phases.SetupPhase;
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
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getMapCommandConsoleOutput()));
            return true;
        }
        Player player = (Player) sender;
        if (!(main.getPhaseManager().getCurrentPhase() instanceof SetupPhase)) {
            player.sendMessage("Du bist nicht in der Setup Phase!");
            return true;
        }
        switch (args.length) {
            case 1:
                switch (args[0]) {
                    case "help":

                        return true;
                    case "list":
                        Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () ->
                                this.printListToPlayer(player, main.getMapRepository().findAllMapsWithName()));
                        return true;
                    case "listineditor":
                        this.printListToPlayer(player, main.getMapUtils().getMapNames());
                        return true;
                }
                break;
            case 2:
                switch (args[0]) {
                    case "create":
                        this.createNewMap(player, args[1]);
                        return true;
                    case "edit":
                        this.setMapInEditMode(player, args[1]);
                        return true;
                    case "save":
                        this.saveMap(player, args[1]);
                        return true;
                    case "check":
                        this.checkMap(player, args[1]);
                        return true;
                    case "remove":
                        this.removeMap(player, args[1]);
                        return true;
                }
                break;
            case 3:
                if ("modify".equals(args[0])) {
                    switch (args[2]) {
                        case "pos1":
                            this.setPosition1(player, args[1]);
                            return true;
                        case "pos2":
                            this.setPosition2(player, args[1]);
                            return true;
                    }
                }
                break;
            case 4:
                if ("modify".equals(args[0]) && "teamsize".equals(args[2])) {
                    this.setMaxTeamSize(player, args[1], args[3]);
                    return true;
                }
                break;
            case 5:
                if ("modify".equals(args[0])) {
                    switch (args[2]) {
                        case "spawner":
                            switch (args[3]) {
                                case "add":
                                    this.addSpawner(player, args[1], args[4]);
                                    return true;
                                case "remove":
                                    this.removeSpawner(player, args[1], args[4]);
                                    return true;
                            }
                            break;
                        case "teams":
                            switch (args[3]) {
                                case "add":
                                    this.addTeam(player, args[1], args[4]);
                                    return true;
                                case "remove":
                                    this.removeTeam(player, args[1], args[4]);
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
                            this.setTeamVillager(player, args[1], args[4]);
                            return true;
                        case "bed":
                            this.setTeamBed(player, args[1], args[4]);
                            return true;
                        case "spawn":
                            this.setTeamSpawn(player, args[1], args[4]);
                            return true;
                    }
                }
                break;
            case 7:
                if ("modify".equals(args[0]) && "teams".equals(args[2]) && "modify".equals(args[3]) && "color".equals(args[5])) {
                    this.setTeamColor(player, args[1], args[4], args[6]);
                    return true;
                }
                break;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getMapCommandCommandError()));
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final List<String> list = new LinkedList<>();
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getMapCommandConsoleOutput()));
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
                    if ("spawner".equals(args[2]) || "teams".equals(args[2]))
                        list.addAll(Arrays.asList("add", "remove"));
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

    /**
     * Prints a list to a player
     * @param player the player to print the list to
     * @param maps the list to print
     */
    private void printListToPlayer(Player player, List<String> maps) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandList(maps.size()) + String.join(", ", maps)));
    }

    /**
     * Creates a new map, which is in edit mode
     * @param player the player that runs the command
     * @param mapName the name of the map to create
     */
    private void createNewMap(@NotNull Player player, @NotNull String mapName) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
            if (!main.getMapRepository().findAllMapsWithName().contains(mapName)
                    && !main.getMapUtils().getMapNames().contains(mapName)) {
                main.getMapUtils().getOrCreateMap(mapName);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getMessages().getMapCommandMapCreated(mapName)));
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessages().getMapCommandMapAllreadyExists(mapName)));
        });
    }

    /**
     * Sets a map from the database in edit mode
     * @param player the player that runs the command
     * @param mapName the name of the map to remove
     */
    private void setMapInEditMode(@NotNull Player player, @NotNull String mapName) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
            if (main.getMapRepository().findByName(mapName).isPresent()) {
                if (!main.getMapUtils().getMapNames().contains(mapName)) {
                    Map map = main.getMapRepository().findByName(mapName).get();
                    main.getMapUtils().getMapsInEdit().put(map.getName(), map);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessages().getMapCommandMapIsNowImEditMode(mapName)));
                } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getMessages().getMapCommandMapIsAllreadyImEditMode(mapName)));
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessages().getMapCommandMapDosentExists(mapName)));
        });
    }

    /**
     * Saves a map from the edit mode to the database
     * @param player the player that runs the command
     * @param mapName the name of the map to save
     */
    private void saveMap(@NotNull Player player, @NotNull String mapName) {
        if (main.getMapUtils().getMapNames().contains(mapName)) {
            if (main.getMapUtils().getMapsInEdit().get(mapName).checkComplete()) {
                Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
                    main.getMapRepository().saveMap(main.getMapUtils().getMapsInEdit().get(mapName));
                    main.getMapUtils().getMapsInEdit().remove(mapName);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessages().getMapCommandMapSaved(mapName)));
                });
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessages().getMapCommandMapDosentComplete(mapName)));
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandMapIsNotInEditMode(mapName)));
    }

    /**
     * Checks if a map is complete and can get saved
     * @param player the player that runs the command
     * @param mapName the map of the name to check
     */
    private void checkMap(@NotNull Player player, @NotNull String mapName) {
        if (main.getMapUtils().getMapNames().contains(mapName)) {
            main.getMapUtils().showCheckInventory(player, main.getMapUtils().getMapsInEdit().get(mapName));
            if (main.getMapUtils().getMapsInEdit().get(mapName).checkComplete())
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getMessages().getMapCommandMapIsComplete(mapName)));
            else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessages().getMapCommandMapDosentComplete(mapName)));
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandMapIsNotInEditMode(mapName)));
    }

    /**
     * Removes a map from the database and the edit mode
     * @param player the player that runs the command
     * @param mapName the name of the map to remove
     */
    private void removeMap(@NotNull Player player, @NotNull String mapName) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
            if (main.getMapRepository().findByName(mapName).isPresent() || main.getMapUtils().getMapNames().contains(mapName)) {
                main.getMapRepository().findByName(mapName).ifPresent(map -> main.getMapRepository().deleteMap(map));
                if (main.getMapUtils().getMapNames().contains(mapName)) main.getMapUtils().deleteMap(mapName);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getMessages().getMapCommandMapRemoved(mapName)));
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessages().getMapCommandMapDosentExists(mapName)));
        });
    }

    /**
     * Sets the 1st corner position of a map
     * @param player the player that runs the command
     * @param mapName the map to modify
     */
    private void setPosition1(@NotNull Player player, @NotNull String mapName) {
        if (main.getMapUtils().getMapNames().contains(mapName)) {
            main.getMapUtils().getMapsInEdit().get(mapName).setPos1(new Location(player.getLocation()));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessages().getMapCommandChangedPos1(mapName)));
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandMapIsNotInEditMode(mapName)));
    }

    /**
     * Sets the 2nd corner position of a map
     * @param player the player that runs the command
     * @param mapName the map to modify
     */
    private void setPosition2(@NotNull Player player, @NotNull String mapName) {
        if (main.getMapUtils().getMapNames().contains(mapName)) {
            main.getMapUtils().getMapsInEdit().get(mapName).setPos2(new Location(player.getLocation()));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessages().getMapCommandChangedPos1(mapName)));
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandMapIsNotInEditMode(mapName)));
    }

    /**
     * Sets the maximal team size of a map
     * @param player the player that runs the command
     * @param mapName the map to modify
     * @param maxTeamSize the maximal team size
     */
    private void setMaxTeamSize(@NotNull Player player, @NotNull String mapName, @NotNull String maxTeamSize) {
        if(main.getMapUtils().getMapNames().contains(mapName)) {
            try {
                main.getMapUtils().getMapsInEdit().get(mapName).setTeamsize(Integer.parseInt(maxTeamSize));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getMessages().getMapCommandTeamSizeChanged(mapName, Integer.parseInt(maxTeamSize))));
            } catch (NumberFormatException exception) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getMessages().getMapCommandNotANumber(maxTeamSize)));
            }
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandMapIsNotInEditMode(mapName)));
    }

    /**
     * Adds a spawner to a map
     * @param player the player that runs the command
     * @param mapName the map to modify
     * @param material the material of the spawner
     */
    private void addSpawner(@NotNull Player player, @NotNull String mapName, @NotNull String material) {
        if(main.getMapUtils().getMapNames().contains(mapName)) {
            if (Arrays.stream(SpawnerMaterial.values())
                    .map(SpawnerMaterial::toString)
                    .collect(Collectors.toList())
                    .contains(material)) {
                main.getMapUtils().getMapsInEdit().get(mapName)
                        .addSpwner(new Spawner(SpawnerMaterial.valueOf(material), new Location(player.getLocation())));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getMessages().getMapCommandSpawnerAdded(mapName)));
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessages().getMapCommandNotAMaterial(material)));
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandMapIsNotInEditMode(mapName)));
    }

    /**
     * Removes a spawner from a map
     * @param player the player that runs the command
     * @param mapName the map to modify
     * @param distance the distance where the spawner get removed
     */
    public void removeSpawner(@NotNull Player player, @NotNull String mapName, @NotNull String distance) {
        if(main.getMapUtils().getMapNames().contains(mapName)) {
            try {
                main.getMapUtils().getMapsInEdit().get(mapName).removeSpawner(player.getLocation(), Double.parseDouble(distance));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getMessages().getMapCommandAllSpawnersDeletes(Double.parseDouble(distance))));
            } catch (NumberFormatException exception) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getMessages().getMapCommandNotANumber(distance)));
            }
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandMapIsNotInEditMode(mapName)));
    }

    /**
     * Adds a team to a map
     * @param player the player that runs the command
     * @param mapName the map to modify
     * @param teamName the name of the team
     */
    private void addTeam(@NotNull Player player, @NotNull String mapName, @NotNull String teamName) {
        if(main.getMapUtils().getMapNames().contains(mapName)) {
            if (!main.getMapUtils().getMapsInEdit().get(mapName).getTeams()
                    .stream()
                    .map(Team::getName)
                    .collect(Collectors.toList())
                    .contains(teamName)) {
                main.getMapUtils().getMapsInEdit().get(mapName).addTeam(new Team(teamName));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getMessages().getMapCommandTeamAdded(mapName, teamName)));
            } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessages().getMapCommandTeamAllreadyExists(teamName)));
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandMapIsNotInEditMode(mapName)));
    }

    /**
     * Removes a team from a map
     * @param player the player that runs the command
     * @param mapName the map to modify
     * @param teamName the name of the team
     */
    private void removeTeam(@NotNull Player player, @NotNull String mapName, @NotNull String teamName) {
        if(main.getMapUtils().getMapNames().contains(mapName)) {
            main.getMapUtils().getMapsInEdit().get(mapName).getTeams()
                    .stream()
                    .filter(team -> team.getName().equals(teamName))
                    .findFirst()
                    .ifPresentOrElse(team -> {
                        team.setVillager(new Location(player.getLocation()));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                main.getMessages().getMapCommandTeamRemoved(teamName)));
                    },() -> player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessages().getMapCommandTeamDosentExists(teamName))));
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandMapIsNotInEditMode(mapName)));
    }

    /**
     * Sets the position of the villager from a team
     * @param player the player that runs the command
     * @param mapName the map to modify
     * @param teamName the name of the team
     */
    private void setTeamVillager(@NotNull Player player, @NotNull String mapName, @NotNull String teamName) {
        if(main.getMapUtils().getMapNames().contains(mapName)) {
            main.getMapUtils().getMapsInEdit().get(mapName).getTeams()
                    .stream()
                    .filter(team -> team.getName().equals(teamName))
                    .findFirst()
                    .ifPresentOrElse(team -> {
                        team.setVillager(new Location(player.getLocation()));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                main.getMessages().getMapCommandTeamVillagerSet(mapName)));
                    },() -> player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessages().getMapCommandTeamDosentExists(teamName))));
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandMapIsNotInEditMode(mapName)));
    }

    /**
     * Sets the position of the bed from a team
     * @param player the player that runs the command
     * @param mapName the map to modify
     * @param teamName the name of the team
     */
    private void setTeamBed(@NotNull Player player, @NotNull String mapName, @NotNull String teamName) {
        if(main.getMapUtils().getMapNames().contains(mapName)) {
            main.getMapUtils().getMapsInEdit().get(mapName).getTeams()
                    .stream()
                    .filter(team -> team.getName().equals(teamName))
                    .findFirst()
                    .ifPresentOrElse(team -> {
                        team.setBed(new Location(player.getLocation()));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                main.getMessages().getMapCommandTeamBedSet(mapName)));
                    },() -> player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessages().getMapCommandTeamDosentExists(teamName))));
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandMapIsNotInEditMode(mapName)));
    }

    /**
     * Sets the position of the spawn from a team
     * @param player the player that runs the command
     * @param mapName the map to modify
     * @param teamName the name of the team
     */
    private void setTeamSpawn(@NotNull Player player, @NotNull String mapName, @NotNull String teamName) {
        if(main.getMapUtils().getMapNames().contains(mapName)) {
            main.getMapUtils().getMapsInEdit().get(mapName).getTeams()
                    .stream()
                    .filter(team -> team.getName().equals(teamName))
                    .findFirst()
                    .ifPresentOrElse(team -> {
                        team.setSpawn(new Location(player.getLocation()));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                main.getMessages().getMapCommandTeamSpawnSet(mapName)));
                    },() -> player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessages().getMapCommandTeamDosentExists(teamName))));
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandMapIsNotInEditMode(mapName)));
    }

    /**
     * Sets the color of a team
     * @param player the player that runs the command
     * @param mapName the map to modify
     * @param teamName the name of the team
     * @param color the color for the team
     */
    private void setTeamColor(@NotNull Player player, @NotNull String mapName, @NotNull String teamName, @NotNull String color) {
        if(main.getMapUtils().getMapNames().contains(mapName)) {
            main.getMapUtils().getMapsInEdit().get(mapName).getTeams()
                    .stream()
                    .filter(team -> team.getName().equals(teamName))
                    .findFirst()
                    .ifPresentOrElse(team -> {
                        team.setColor(color.toCharArray()[0]);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                main.getMessages().getMapCommandTeamColorSet(mapName)));
                    },() -> player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessages().getMapCommandTeamDosentExists(teamName))));
        } else player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessages().getMapCommandMapIsNotInEditMode(mapName)));
    }
}
