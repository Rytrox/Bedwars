package de.rytrox.bedwars.database.entity;

import de.rytrox.bedwars.utils.Completable;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

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

    @Transient
    private boolean hasBed = true;

    @Transient
    private final Set<Player> members = new HashSet<>();

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

    public ChatColor getColor() {
        return color;
    }

    public void setColor(@NotNull ChatColor color) {
        this.color = color;
    }

    public void setColor(@NotNull Character color) {
        this.color = ChatColor.getByChar(color);
    }

    public Location getVillager() {
        return villager;
    }

    public void setVillager(@NotNull Location villager) {
        this.villager = villager;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(@NotNull Location spawn) {
        this.spawn = spawn;
    }

    public Location getBed() {
        return bed;
    }

    public void setBed(@NotNull Location bed) {
        this.bed = bed;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(@NotNull Map map) {
        this.map = map;
    }

    public boolean isHasBed() {
        return hasBed;
    }

    public void setHasBed(boolean hasBed) {
        this.hasBed = hasBed;
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

    @Override
    public boolean checkComplete() {
        return Stream.of(spawn, bed)
                .allMatch(object -> object != null && object.checkComplete());
    }
}
