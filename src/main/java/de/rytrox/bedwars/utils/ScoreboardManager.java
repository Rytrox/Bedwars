package de.rytrox.bedwars.utils;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class ScoreboardManager {

    public void updateBoard(Player player, boolean blue, boolean red, int kills, int deaths) {
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective objective = board.registerNewObjective("Bedwars", "dummy", ChatColor.translateAlternateColorCodes('&', "&e&lBedwars"));

        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8&l")).setScore(9);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&fMap: &aVoid")).setScore(8);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8&l&8")).setScore(7);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&c&lR &fRot: " + (red ? "&a[+]" : "&c[-]"))).setScore(6);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&9&lB &fBlau: " + (blue ? "&a[+]" : "&c[-]"))).setScore(5);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8&l&8&l")).setScore(4);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&fKills: &a" + kills)).setScore(3);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&fTode: &a" + deaths)).setScore(2);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8&l&8&l&8")).setScore(1);

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);

        Objective objective1 = board.registerNewObjective("Tablist", "dummy", "Tablist");
        board.registerNewTeam("Rot").setColor(ChatColor.RED);
        Objects.requireNonNull(board.getTeam("Rot")).addEntry(player.getName());
        objective1.setDisplaySlot(DisplaySlot.PLAYER_LIST);
    }

}
