package de.rytrox.bedwars.database.enums;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public enum TeamItem {

    BLACK(ChatColor.BLACK, Material.BLACK_WOOL),
    DARK_BLUE(ChatColor.DARK_BLUE, Material.BLUE_CONCRETE),
    DARK_GREEN(ChatColor.DARK_GREEN, Material.GREEN_WOOL),
    DARK_AQUA(ChatColor.DARK_AQUA, Material.CYAN_WOOL),
    DARK_RED(ChatColor.DARK_RED, Material.RED_WOOL),
    DARK_PURPLE(ChatColor.DARK_PURPLE, Material.PURPLE_WOOL),
    GOLD(ChatColor.GOLD, Material.ORANGE_WOOL),
    GRAY(ChatColor.GRAY, Material.LIGHT_GRAY_WOOL),
    DARK_GRAY(ChatColor.DARK_GRAY, Material.GRAY_WOOL),
    BLUE(ChatColor.BLUE, Material.BLUE_WOOL),
    GREEN(ChatColor.GREEN, Material.LIME_WOOL),
    AQUA(ChatColor.AQUA, Material.LIGHT_BLUE_WOOL),
    RED(ChatColor.RED, Material.PINK_TERRACOTTA),
    LIGHT_PURPLE(ChatColor.LIGHT_PURPLE, Material.MAGENTA_WOOL),
    YELLOW(ChatColor.YELLOW, Material.YELLOW_WOOL),
    WHITE(ChatColor.WHITE, Material.WHITE_WOOL);


    private ChatColor chatColor;
    private Material material;

    private TeamItem(ChatColor chatColor, Material material) {
        this.chatColor = chatColor;
        this.material = material;
    }

    public static Material findByChatColor(ChatColor chatColor) {
        return Arrays.stream(values())
                .filter(team -> team.chatColor == chatColor)
                .map(teamItem -> teamItem.material)
                .findAny()
                .orElse(Material.BARRIER);
    }
}
