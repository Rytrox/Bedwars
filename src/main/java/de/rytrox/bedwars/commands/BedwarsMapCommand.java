package de.rytrox.bedwars.commands;

import de.rytrox.bedwars.Bedwars;
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
                        if(!isInEditMode(args[1], player)) return true;

                        break;
                    case "check":
                        if(!isInEditMode(args[1], player)) return true;

                        break;
                    case "remove":

                        return true;
                }
                break;
            case 3:
                if ("modify".equals(args[0])) {
                    switch (args[2]) {
                        case "pos1":
                            if(!isInEditMode(args[1], player)) return true;

                            return true;
                        case "pos2":
                            if(!isInEditMode(args[1], player)) return true;

                            return true;
                    }
                }
                break;
            case 4:
                if ("modify".equals(args[0])) {
                    switch (args[2]) {
                        case "teamsize":
                            if(!isInEditMode(args[1], player)) return true;

                            return true;
                        case "spawner":
                            if("remove".equals(args[3])) {
                                if(!isInEditMode(args[1], player)) return true;

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
                                if(!isInEditMode(args[1], player)) return true;

                                return true;
                            }
                            break;
                        case "teams":
                            switch (args[3]) {
                                case "add":
                                    if(!isInEditMode(args[1], player)) return true;

                                    return true;
                                case "remove":
                                    if(!isInEditMode(args[1], player)) return true;

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
                            if(!isInEditMode(args[1], player)) return true;

                            return true;
                        case "bed":
                            if(!isInEditMode(args[1], player)) return true;

                            return true;
                        case "spawn":
                            if(!isInEditMode(args[1], player)) return true;

                            return true;
                    }
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cBitte benutze &7/help&c!"));
                return true;
            case 7:
                if ("modify".equals(args[0]) && "teams".equals(args[2]) && "modify".equals(args[3]) && "color".equals(args[5])) {
                    if(!isInEditMode(args[1], player)) return true;

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
                list.add("add");
                list.add("modify");
                list.add("save");
                list.add("remove");
                list.add("check");
                list.add("list");
                list.add("listinedit");
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

    private boolean isInEditMode(String name, Player player) {
        boolean statement = true;
        if(statement) {
            return true;
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDie Map &7" + name + "&c ist nicht im Editor!"));
            return false;
        }
    }
}
