package de.rytrox.bedwars.database.entity;

import de.rytrox.bedwars.utils.Completable;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.text.NumberFormat;
import java.util.Objects;

@Entity
@Table (name = "`Locations`")
public class Location implements Completable {

    @Id
    @Column (name = "id", nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "`world`", nullable = false, length = 100)
    private String world;

    @Column (name = "`x`", nullable = false)
    private Double x;

    @Column (name = "`y`", nullable = false)
    private Double y;

    @Column (name = "`z`", nullable = false)
    private Double z;

    @Column (name = "`yaw`", nullable = false)
    private Float yaw;

    @Column (name = "`pitch`", nullable = false)
    private Float pitch;

    public Location() {
    }

    public Location(@NotNull org.bukkit.Location location) {
        setWorld(Objects.requireNonNull(location.getWorld()).getName());
        setX(location.getX());
        setY(location.getY());
        setZ(location.getZ());
        setYaw(location.getYaw());
        setPitch(location.getPitch());
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id) {
        this.id = id;
    }

    @NotNull
    public String getWorld() {
        return world;
    }

    public void setWorld(@NotNull String world) {
        this.world = world;
    }

    @NotNull
    public Double getX() {
        return x;
    }

    public void setX(@NotNull Double x) {
        this.x = x;
    }

    @NotNull
    public Double getY() {
        return y;
    }

    public void setY(@NotNull Double y) {
        this.y = y;
    }

    @NotNull
    public Double getZ() {
        return z;
    }

    public void setZ(@NotNull Double z) {
        this.z = z;
    }

    @NotNull
    public Float getYaw() {
        return yaw;
    }

    public void setYaw(@NotNull Float yaw) {
        this.yaw = yaw;
    }

    @NotNull
    public Float getPitch() {
        return pitch;
    }

    public void setPitch(@NotNull Float pitch) {
        this.pitch = pitch;
    }

    @NotNull
    public org.bukkit.Location toBukkitLocation() {
        return new org.bukkit.Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public void fromBukkitLocation(@NotNull org.bukkit.Location location) {
        setWorld(Objects.requireNonNull(location.getWorld()).getName());
        setX(location.getX());
        setY(location.getX());
        setZ(location.getX());
        setYaw(location.getYaw());
        setPitch(location.getPitch());
    }

    private String format(double value) {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        return formatter.format(value).replace(',', '.');
    }

    @Override
    public String toString() {
        return String.format("world: %s, x: %s, y: %s, z: %s, yaw: %s, pitch: %s", world, this.format(x),
                this.format(y), this.format(z), this.format(yaw), this.format(pitch));
    }

    @Override
    public boolean checkComplete() {
        return world != null && x != null && y != null && z != null && yaw != null && pitch != null;
    }
}
