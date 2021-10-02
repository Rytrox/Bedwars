package de.rytrox.bedwars.commands;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.map.Map;
import de.rytrox.bedwars.map.MapLoadEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BedwarsMapCommand implements CommandExecutor, TabCompleter {

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
        
        return null;
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
