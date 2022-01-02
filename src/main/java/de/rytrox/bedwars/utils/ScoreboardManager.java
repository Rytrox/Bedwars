package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.team.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreboardManager {

    private final Bedwars main = JavaPlugin.getPlugin(Bedwars.class);

    private final Map<Player, Scoreboard> activeBoards = new HashMap<>();
    private final TeamManager teamManager;
    private final String mapName;

    public ScoreboardManager(String mapName, TeamManager teamManager) {
        this.teamManager = teamManager;
        this.mapName = mapName;
    }

    /**
     * Zeigt einem Spieler das SpielScoreboard an
     *
     * @param player Der Spieler dem das Board angezeigt werden soll
     * @param kills Die kills des Spielers
     * @param deaths Die Tode ses Spielers
     */
    public void addBoard(Player player, int kills, int deaths) {
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        fillSidebar(board, kills, deaths);
        teamManager.getTeams().forEach(team ->
                board.registerNewTeam(team.getName()).setColor(team.getColor()));
        player.setScoreboard(board);
        activeBoards.put(player, board);
    }

    /**
     * Erneuert die Werte auf dem SpielScoreboard eines Spielers
     *
     * @param player Der Spieler, dem die neuen Werte angezeigt werden sollen
     * @param kills Die Kills des Spielers
     * @param deaths Die Tode des Spielers
     */
    public void updateBoard(Player player, int kills, int deaths) {
        if(!activeBoards.containsKey(player)) return;
        Scoreboard board = activeBoards.get(player);
        Objects.requireNonNull(board.getObjective("Bedwars")).unregister();
        fillSidebar(board, kills, deaths);

        player.setScoreboard(board);
        activeBoards.replace(player, board);
    }

    /**
     * Entfernt das SpielScoreboard für einen Spieler
     *
     * @param player Der Spieler zum Entfernen des Scoreboard
     */
    public void removeBoard(Player player) {
        if(!activeBoards.containsKey(player)) return;
        player.getScoreboard().getObjectives().forEach(Objective::unregister);
        player.getScoreboard().getTeams().forEach(Team::unregister);
        activeBoards.remove(player);
    }

    /**
     * Fügt den Spieler zu einem Team auf dem Scoreboard hinzu
     *
     * @param player Der Spieler zum joinen eines Teams
     */
    public void addPlayerToTeam(Player player) {
        if(!activeBoards.containsKey(player)) return;
        Scoreboard board = activeBoards.get(player);
        teamManager.getTeams().forEach(team ->
                team.getMembers().forEach( member ->
                        Objects.requireNonNull(board.getTeam(team.getName())).addEntry(member.getName())));
        player.setScoreboard(board);
        activeBoards.replace(player, board);
    }

    /**
     * Befüllt das Scoreboard mit Werten
     *
     * @param board das Scoreboard zum befüllen
     * @param kills die Kills des Spielers
     * @param deaths die Tode des Spielers
     */
    private void fillSidebar(Scoreboard board, int kills, int deaths) {
        Objective objective = board.registerNewObjective("Bedwars", "dummy", ChatColor.translateAlternateColorCodes('&', main.getMessages().getScoreboardTitle()));

         AtomicInteger score = new AtomicInteger(teamManager.getTeams().size() + 7);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8&l")).setScore(score.get());
        score.set(score.get() - 1);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', main.getMessages().getScoreboardMap(mapName))).setScore(score.get());
        score.set(score.get() - 1);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8&l&8")).setScore(score.get());
        score.set(score.get() - 1);
        teamManager.getTeams().forEach(team -> {
            objective.getScore(ChatColor.translateAlternateColorCodes('&', main.getMessages().getScoreboardTeam(team.getColor(), team.getName()))).setScore(score.get());
            score.set(score.get() - 1);
        });
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8&l&8&l")).setScore(4);
        score.set(score.get() - 1);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', main.getMessages().getScoreboardKills(kills))).setScore(3);
        score.set(score.get() - 1);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', main.getMessages().getScoreboardTode(deaths))).setScore(2);
        score.set(score.get() - 1);
        objective.getScore(ChatColor.translateAlternateColorCodes('&', "&8&l&8&l&8")).setScore(1);

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
}
