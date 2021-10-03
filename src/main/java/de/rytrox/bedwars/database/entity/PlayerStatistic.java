package de.rytrox.bedwars.database.entity;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "`PlayerStatistics`")
public class PlayerStatistic {

    @Id
    @Column(name = "`uuid`", length = 36, nullable = false)
    private String uuid;

    @Column(name = "`games`", nullable = false)
    private Integer games;

    @Column(name = "`wins`", nullable = false)
    private Integer wins;

    @Column(name = "`kills`", nullable = false)
    private Integer kills;

    @Column(name = "`deaths`", nullable = false)
    private Integer deaths;

    /**
     * Den Default-Konstruktor muss jedes Entity besitzen!
     */
    public PlayerStatistic() {
        this.deaths = 0;
        this.games = 0;
        this.wins = 0;
        this.kills = 0;
    }

    /**
     * Erstelle eine neue Statistik basierend auf einen Spieler
     *
     * @param player den Spieler. Kann nicht null sein
     */
    public PlayerStatistic(@NotNull OfflinePlayer player) {
        this();

        this.uuid = player.getUniqueId().toString();
    }

    @ApiStatus.Internal
    public @NotNull String getUuid() {
        return uuid;
    }

    @ApiStatus.Internal
    public void setUuid(@NotNull String uuid) {
        this.uuid = uuid;
    }

    @NotNull
    public UUID getUniqueID() {
        return UUID.fromString(this.uuid);
    }

    @NotNull
    public Integer getGames() {
        return games;
    }

    public void setGames(@NotNull Integer games) {
        this.games = games;
    }

    @NotNull
    public Integer getWins() {
        return wins;
    }

    public void setWins(@NotNull Integer wins) {
        this.wins = wins;
    }

    @NotNull
    public Integer getKills() {
        return kills;
    }

    public void setKills(@NotNull Integer kills) {
        this.kills = kills;
    }

    @NotNull
    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(@NotNull Integer deaths) {
        this.deaths = deaths;
    }
}
