package de.rytrox.bedwars.database.entity;

import de.rytrox.bedwars.utils.Completable;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table (name = "`Teams`")
public class Team implements Completable {

    @Id
    @Column (name = "id", nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "`name`", nullable = false)
    private String name;

    @Enumerated (EnumType.ORDINAL)
    @Column (name = "`color`", nullable = false)
    private ChatColor color;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "`villager`", nullable = false)
    private Location villager;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "`spawn`", nullable = false)
    private Location spawn;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "`bed`", nullable = false)
    private Location bed;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "`map`", nullable = false)
    private Map map;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public ChatColor getColor() {
        return color;
    }

    public void setColor(@NotNull ChatColor color) {
        this.color = color;
    }

    public void setColor(@NotNull Character color) {
        this.color = ChatColor.getByChar(color);
    }

    @NotNull
    public Location getVillager() {
        return villager;
    }

    public void setVillager(@NotNull Location villager) {
        this.villager = villager;
    }

    @NotNull
    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(@NotNull Location spawn) {
        this.spawn = spawn;
    }

    @NotNull
    public Location getBed() {
        return bed;
    }

    public void setBed(@NotNull Location bed) {
        this.bed = bed;
    }

    @NotNull
    public Map getMap() {
        return map;
    }

    public void setMap(@NotNull Map map) {
        this.map = map;
    }


    @Override
    public boolean checkComplete() {
        if (!villager.checkComplete() || !bed.checkComplete() || !spawn.checkComplete()) return false;
        return name != null && color != null && villager != null && spawn != null && bed != null;
    }
}
