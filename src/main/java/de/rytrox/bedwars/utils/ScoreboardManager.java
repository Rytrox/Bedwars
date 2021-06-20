package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;
import de.timeout.libs.reflect.Players;
import de.timeout.libs.reflect.Reflections;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class ScoreboardManager {

    public ScoreboardManager(Player player) throws ReflectiveOperationException {
        Bukkit.getServer().getScheduler().runTaskLater(JavaPlugin.getPlugin(Bedwars.class), () -> {
            try {
                create(player);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }, 100L);
    }

    public void create(Player player) throws ReflectiveOperationException {

        Scoreboard board = new Scoreboard();
        ScoreboardObjective objective = board.registerObjective("Bedwars", IScoreboardCriteria.DUMMY, (IChatBaseComponent) new ChatMessage("Bedwars"), IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
        objective.setDisplayName((IChatBaseComponent) new ChatMessage("Bedwars"));

        PacketPlayOutScoreboardObjective removepack = new PacketPlayOutScoreboardObjective(objective, 1);
        PacketPlayOutScoreboardObjective createpack = new PacketPlayOutScoreboardObjective(objective, 0);
        PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, objective);

        PacketPlayOutScoreboardScore placeholder3 = new PacketPlayOutScoreboardScore(ScoreboardServer.Action.CHANGE, "Test <-- geht", "   ", 0);

        player.sendMessage("aosizgd");
        Players.sendPacket(player, createpack);
        Players.sendPacket(player, display);
    }
















    public static void ssscoreboardManager(Player player, int schlagHand) throws ReflectiveOperationException {
        PacketPlayOutAnimation packetPlayOutAnimation = new PacketPlayOutAnimation();

        Field id = Reflections.getField(PacketPlayOutAnimation.class, "a");
        Field hand = Reflections.getField(PacketPlayOutAnimation.class, "b");
        Field entityPlayer = Reflections.getField(EntityPlayer.class, "id");

        Reflections.setValue(id, packetPlayOutAnimation, Reflections.getValue(entityPlayer, Players.getEntityPlayer(player)));
        Reflections.setValue(hand, packetPlayOutAnimation, schlagHand);

        Players.sendPacket(player, packetPlayOutAnimation);
    }
}
