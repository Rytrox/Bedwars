package de.rytrox.bedwars.team;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class Team {

    private Set<Player> members;
    private String teamName;
    private Material teamItem;
    private ChatColor color;
    private boolean bed;

    public Team(String teamName, Material teamItem, int maxTeamSize, ChatColor color) {
        this.teamName = teamName;
        this.teamItem = teamItem;
        this.members = new HashSet<>(maxTeamSize);
        this.color = color;
        this.bed = true;
    }

    /**
     * Gibt ein Set mit allen Spielern des Teams zurück
     *
     * @return ein Set mit allen Mitgliedern des Teams
     */
    public Set<Player> getMembers() {
        return new HashSet<>(members);
    }

    /**
     * fügt ein Spieler dem Team hinzu
     *
     * @param member der hinzuzufügende Spieler
     * @return gibt zurück, ob der Spieler schon zum Team gehörte
     */
    public boolean addMember(Player member) {
        return members.add(member);
    }

    /**
     * entfernt einem Spieler vom Team
     *
     * @param member der zu entfernende Spieler
     * @return gibt zurück, ob der Spieler in dem Team war
     */
    public boolean removeMember(Player member) {
        return members.remove(member);
    }

    /**
     * Gibt den Teamnamen zurück
     *
     * @return Teamname
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Setzt einen neuen Teamnamen
     *
     * @param teamName der neue Teamname
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Gibt das TeamItem zurück
     *
     * @return teamItem
     */
    public ItemStack getTeamItem() {
        return new ItemStack(teamItem);
    }

    /**
     * Setzt ein neues TeamItem
     *
     * @param teamItem das neue TeamItem
     */
    public void setTeamItem(Material teamItem) {
        this.teamItem = teamItem;
    }

    /**
     * Gibt die TeamFarbe zurück
     *
     * @return teamfarbe
     */
    public ChatColor getColor() { return color; }

    /**
     * setzt die Farbe des Teams
     *
     * @param color die zu setzende Farbe
     */
    public void setColor(ChatColor color) { this.color = color; }

    /**
     * Gibt zurück, ob das Team noch ein Bett hat
     *
     * @return hat das Team noch ein Bett
     */
    public boolean hasBed() {
        return bed;
    }

    /**
     * setzt, ob das Team noch ein Bett hat
     *
     * @param bed hat das Team noch ein Bett
     */
    public void setBed(boolean bed) {
        this.bed = bed;
    }
}
