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

    public Team(String teamName, Material teamItem, int maxTeamSize, ChatColor color) {
        this.teamName = teamName;
        this.teamItem = teamItem;
        this.members = new HashSet<>(maxTeamSize);
        this.color = color;
    }

    public Set<Player> getMembers() {
        return new HashSet<>(members);
    }

    public boolean addMember(Player member) {
        return members.add(member);
    }

    public boolean removeMember(Player member) {
        return members.remove(member);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ItemStack getTeamItem() {
        return new ItemStack(teamItem);
    }

    public void setTeamItem(Material teamItem) {
        this.teamItem = teamItem;
    }

    public void setColor(ChatColor color) { this.color = color; }

    public ChatColor getColor() { return color; }
}
