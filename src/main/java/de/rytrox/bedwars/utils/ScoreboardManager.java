package de.rytrox.bedwars.utils;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ScoreboardManager {

    private final Map<Player, Scoreboard> activeBoards = new HashMap<>();

    public void addBoard(@NotNull Player player, boolean blue, boolean red, int kills, int deaths) {
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        fillSidebar(board, blue, red, kills, deaths);

        board.registerNewTeam("red").setColor(ChatColor.RED);
        board.registerNewTeam("blue").setColor(ChatColor.BLUE);

        player.setScoreboard(board);
        activeBoards.put(player, board);
    }

    public void updateBoard(Player player, boolean blue, boolean red, int kills, int deaths) {
        if(!activeBoards.containsKey(player)) return;
        Scoreboard board = activeBoards.get(player);
        board.getObjective("Bedwars").unregister();
        fillSidebar(board, blue, red, kills, deaths);

        player.setScoreboard(board);
        activeBoards.replace(player, board);
    }

    public void removeBoard(Player player) {
        if(!activeBoards.containsKey(player)) return;
        player.getScoreboard().getObjectives().forEach(Objective::unregister);
        player.getScoreboard().getTeams().forEach(Team::unregister);
        activeBoards.remove(player);
    }

    public void addPlayerToTeam(Player player, Player target, boolean team) {
        if(!activeBoards.containsKey(player)) return;
        Scoreboard board = activeBoards.get(player);
        if(team) Objects.requireNonNull(board.getTeam("red")).addEntry(target.getName());
        else Objects.requireNonNull(board.getTeam("blue")).addEntry(target.getName());

        player.setScoreboard(board);
        activeBoards.replace(player, board);
    }

    private void fillSidebar(@NotNull Scoreboard board, boolean blue, boolean red, int kills, int deaths) {
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
    }
}
