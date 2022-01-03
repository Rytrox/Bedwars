package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;
import de.timeout.libs.config.UTFConfig;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Messages {

    private final Bedwars main = JavaPlugin.getPlugin(Bedwars.class);
    private final UTFConfig configuration = main.getLanguage();

    // Shop
    public String getVillagerName() {
        return configuration.getString("villagerName", "&9&lShop");
    }

    public String getNotEnoughMoney(int amount, String currency) {
        return configuration.getString("notEnoughMoney", "&dDir fehlen &b" + amount + " " + currency + "&d!")
                .replace("%amount%", "" + amount).replace("%currency%", currency);
    }

    // Scoreboard
    public String getScoreboardTitle() {
        return configuration.getString("scoreboardTitle", "&1&lBedwars");
    }

    public String getScoreboardMap(String mapname) {
        return configuration.getString("scoreboardMap", "&fMap: &b" + mapname).replace("%mapname%", mapname);
    }

    public String getScoreboardTeam(ChatColor teamcolor, String teamname) {
        return configuration.getString("scoreboardTeam",teamcolor + "&l" + teamname + " &f:")
                .replace("%teamcolor%", "" + teamcolor).replace("%teamname%", teamname);
    }

    public String getScoreboardKills(int kills) {
        return configuration.getString("scoreboardKills", "&fKills: &b" + kills).replace("%kills%", "" + kills);
    }

    public String getScoreboardTode(int tode) {
        return configuration.getString("scoreboardTode", "&fTode: &b" + tode).replace("%tode%", "" + tode);
    }

    // Timer Messages
    public String[] getTimerRunning(String seconds) {
        return new String[]{configuration.getString("timerRunningTitle", "&b%seconds%").replace("%seconds%", seconds),
                configuration.getString("timerRunningSubtitle", "&9Sekunden verbleiben")};
    }

    public String[] getTimerEnd() {
        return new String[]{configuration.getString("timerEndTitle", "&bBedwars"),
                configuration.getString("timerEndSubtitle", "&9Match startet")};
    }

    // Team Select
    public String getTeamSelected(String teamname) {
        return configuration.getString("teamSelected", "&9Du bist nun in Team: &b" + teamname).replace("%teamname%", teamname);
    }

    // Map Command
    public String getMapCommandConsoleOutput() {
        return configuration.getString("mapCommandConsoleOutput", "&dDieser Command kann nur von einem Spieler ausgeführt werden!");
    }

    public String getMapCommandCommandError() {
        return configuration.getString("mapCommandCommandError", "&dBitte benutze &5/help&d!");
    }

    public String getMapCommandList(int listSize) {
        return configuration.getString("mapCommandMapList", "&9Alls Maps (" + listSize + "): &b").replace("%listsize%", "" + listSize);
    }

    public String getMapCommandMapCreated(String mapname) {
        return configuration.getString("mapCommandMapCreated", "&9Du hast die Map &b" + mapname + " &9erfolgreich erstellt!")
                .replace("%mapname%", mapname);
    }

    public String getMapCommandMapAllreadyExists(String mapname) {
        return configuration.getString("mapCommandMapAllreadyExists", "&9Die Map &b" + mapname + " &9existiert bereits!")
                .replace("%mapname%", mapname);
    }

    public String getMapCommandMapIsNowImEditMode(String mapname) {
        return configuration.getString("mapCommandMapIsNowImEditMode", "&9Die Map &b" + mapname + " &9ist nun im EditMode!")
                .replace("%mapname%", mapname);
    }

    public String getMapCommandMapIsAllreadyImEditMode(String mapname) {
        return configuration.getString("mapCommandMapIsAllreadyImEditMode", "&9Die Map &b" + mapname + " &9ist bereits im EditMode!")
                .replace("%mapname%", mapname);
    }

    public String getMapCommandMapDosentExists(String mapname) {
        return configuration.getString("mapCommandMapDosentExists", "&9Die Map &b" + mapname + " &9existiert nicht!")
                .replace("%mapname%", mapname);
    }

    public String getMapCommandMapDosentComplete(String mapname) {
        return configuration.getString("mapCommandMapDosentComplete", "&dDer Map &5" + mapname + " &dfehlen noch wichte Objekte!")
                .replace("%mapname%", mapname);
    }

    public String getMapCommandMapIsComplete(String mapname) {
        return configuration.getString("mapCommandMapIsComplete", "&9Die Map &b" + mapname + " &9ist vollständig!")
                .replace("%mapname%", mapname);
    }

    public String getMapCommandMapSaved(String mapname) {
        return configuration.getString("mapCommandMapSaved", "&9Die Map &b" + mapname + " &9wurde gespeichert!")
                .replace("%mapname%", mapname);
    }

    public String getMapCommandMapIsNotInEditMode(String mapname) {
        return configuration.getString("mapCommandMapIsNotInEditMode", "&9Die Map &b" + mapname + " &9ist nicht im EditMode!")
                .replace("%mapname%", mapname);
    }

    public String getMapCommandMapRemoved(String mapname) {
        return configuration.getString("mapCommandMapRemoved", "&9Du hast die Map &b" + mapname + " &9entfernt!")
                .replace("%mapname%", mapname);
    }

    public String getMapCommandChangedPos1(String mapname) {
        return configuration.getString("mapCommandChangedPos1", "&9Pos1 für die Map &b" + mapname + " &9erfolgreich geändert!")
                .replace("%mapname%", mapname);
    }

    public String getMapCommandChangedPos2(String mapname) {
        return configuration.getString("mapCommandChangedPos2", "&9Pos1 für die Map &b" + mapname + " &9erfolgreich geändert!")
                .replace("%mapname%", mapname);
    }

    public String getMapCommandTeamSizeChanged(String mapname, int teamsize) {
        return configuration.getString("mapCommandTeamSizeChanged", "&9Die Teamgröße für &b" + mapname + " &9ist nun &b" + teamsize + "&9!")
                .replace("%mapname%", mapname).replace("%teamsize%", "" + teamsize);
    }

    public String getMapCommandNotANumber(String input) {
        return configuration.getString("mapCommandNotANumber", "&dDie Zahl &5" + input + " &dist keine Zahl!")
                .replace("%input%", input);
    }

    public String getMapCommandSpawnerAdded(String mapname) {
        return configuration.getString("mapCommandSpawnerAdded", "&9Du hast einen Spawner zu &b" + mapname + " &9hinzugefügt!")
                .replace("%mapname%", mapname);
    }

    public String getMapCommandNotAMaterial(String material) {
        return configuration.getString("mapCommandNotAMaterial", "&dDas Material &5" + material + " &dexistiert nicht!")
                .replace("%material%", material);
    }

    public String getMapCommandAllSpawnersDeletes(double distance) {
        return configuration.getString("mapCommandAllSpawnersDeletes", "&9Du hast alle Spawner im Umkreis von &b" + distance + " &9gelöscht!")
                .replace("%distance%", "" + distance);
    }

    public String getMapCommandTeamAdded(String mapname, String teamname) {
        return configuration.getString("mapCommandTeamAdded", "&9Du hast das Team &b" + teamname + " &9zur Map &b" + mapname + " &9hinzugefügt!")
                .replace("%mapname%", mapname).replace("%teamname%", teamname);
    }

    public String getMapCommandTeamAllreadyExists(String teamname) {
        return configuration.getString("mapCommandTeamAllreadyExists", "&9Das Team &b" + teamname + " &9existiert bereits!")
                .replace("%teamname%", teamname);
    }

    public String getMapCommandTeamRemoved(String teamname) {
        return configuration.getString("mapCommandTeamRemoved", "&9Das Team &b" + teamname + " &9wurde gelöscht!")
                .replace("%teamname%", teamname);
    }

    public String getMapCommandTeamDosentExists(String teamname) {
        return configuration.getString("mapCommandTeamDosentExists", "&dDas Team &5" + teamname + " &dexistiert nicht!")
                .replace("%teamname%", teamname);
    }

    public String getMapCommandTeamVillagerSet(String teamname) {
        return configuration.getString("mapCommandTeamVillagerSet", "&9Die Villager Position für &b" + teamname + " &9wurde gesetzt!")
                .replace("%teamname%", teamname);
    }

    public String getMapCommandTeamBedSet(String teamname) {
        return configuration.getString("mapCommandTeamBedSet", "&9Die Bett Position für &b" + teamname + " &9wurde gesetzt!")
                .replace("%teamname%", teamname);
    }

    public String getMapCommandTeamSpawnSet(String teamname) {
        return configuration.getString("mapCommandTeamSpawnSet", "&9Die Spawn Position für &b" + teamname + " &9wurde gesetzt!")
                .replace("%teamname%", teamname);
    }

    public String getMapCommandTeamColorSet(String teamname) {
        return configuration.getString("mapCommandTeamColorSet", "&9Die Teamfarbe Position für &b" + teamname + " &9wurde gesetzt!")
                .replace("%teamname%", teamname);
    }
}
