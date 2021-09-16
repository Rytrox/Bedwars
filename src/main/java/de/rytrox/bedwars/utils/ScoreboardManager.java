package de.rytrox.bedwars.utils;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreboardManager {

    private final Map<Player, Scoreboard> activeBoards = new HashMap<>();
    private TeamChoosingManeger teamChoosingManeger;
    private List<de.rytrox.bedwars.team.Team> teams;

    public ScoreboardManager(TeamChoosingManeger teamChoosingManeger) {
        teams = teamChoosingManeger.getTeams();
    }

    public void addBoard(Player player, int kills, int deaths) {
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        fillSidebar(board, kills, deaths);
        teams.forEach(team -> { board.registerNewTeam(team.getTeamName()).setColor(team.getColor()); });
        player.setScoreboard(board);
        activeBoards.put(player, board);
    }

    public void updateBoard(Player player, int kills, int deaths) {
        if(!activeBoards.containsKey(player)) return;
        Scoreboard board = activeBoards.get(player);
        board.getObjective("Bedwars").unregister();
        fillSidebar(board, kills, deaths);

        player.setScoreboard(board);
        activeBoards.replace(player, board);
    }

    public void removeBoard(Player player) {
        if(!activeBoards.containsKey(player)) return;
        player.getScoreboard().getObjectives().forEach(Objective::unregister);
        player.getScoreboard().getTeams().forEach(Team::unregister);
        activeBoards.remove(player);
    }

    public void addPlayerToTeam(Player player) {
        if(!activeBoards.containsKey(player)) return;
        Scoreboard board = activeBoards.get(player);
        teams.forEach(team -> {
            team.getMembers().forEach( member ->  {
                Objects.requireNonNull(board.getTeam(team.getTeamName())).addEntry(member.getName());
            });
        });
        player.setScoreboard(board);
        activeBoards.replace(player, board);
    }

    private void fillSidebar(Scoreboard board, boolean blue, boolean red, int kills, int deaths) {
        Objective objective = board.registerNewObjective("Bedwars", "dummy", ChatColor.translateAlternateColorCodes('&', "&e&lBedwars"));

         AtomicInteger score = new AtomicInteger(teams.size() + 7);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8&l")).setScore(score.get());
        score.set(score.get() - 1);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&fMap: &aVoid")).setScore(score.get());
        score.set(score.get() - 1);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8&l&8")).setScore(score.get());
        score.set(score.get() - 1);
        teams.forEach(team -> {
            objective.getScore(ChatColor.translateAlternateColorCodes('&', String.format("%s&l%s &f:", team.getColor() , team.getTeamName()))).setScore(score.get());
            score.set(score.get() - 1);
        });
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8&l&8&l")).setScore(4);
        score.set(score.get() - 1);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&fKills: &a" + kills)).setScore(3);
        score.set(score.get() - 1);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&fTode: &a" + deaths)).setScore(2);
        score.set(score.get() - 1);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8&l&8&l&8")).setScore(1);

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
}
