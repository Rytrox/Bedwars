package de.rytrox.bedwars.database.enums;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public enum TeamItem {

    BLACK(ChatColor.BLACK, Material.BLACK_WOOL),
    DARK_BLUE(ChatColor.DARK_BLUE, Material.BLUE_WOOL),
    DARK_GREEN(ChatColor.DARK_GREEN, Material.GREEN_WOOL),
    DARK_AQUA(ChatColor.DARK_AQUA, Material.CYAN_WOOL),
    DARK_RED(ChatColor.DARK_RED, Material.RED_WOOL);

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
