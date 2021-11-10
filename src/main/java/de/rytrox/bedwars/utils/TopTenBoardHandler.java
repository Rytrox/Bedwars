package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Location;
import de.rytrox.bedwars.database.entity.PlayerStatistic;
import de.rytrox.bedwars.database.entity.TopTenSign;
import de.rytrox.bedwars.database.repository.TopTenSignsRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class TopTenBoardHandler implements Listener {

    private final Bedwars main = JavaPlugin.getPlugin(Bedwars.class);
    private final TopTenSignsRepository topTenSignsRepository = new TopTenSignsRepository(main.getDatabase());

    public TopTenBoardHandler() {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
            topTenSignsRepository.findAllSigns().forEach(sign -> {
                PlayerStatistic playerStatistic = main.getStatistics().getTopPlayer(sign.getPosition(), sign.getSorted());
                updateSign(sign.getLocation().toBukkitLocation().getBlock(), playerStatistic, sign.getPosition(), sign.getSorted());
            });
        });
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (Objects.equals(event.getLine(0), "BW") && Objects.equals(event.getLine(1), "STATS")
                && Objects.requireNonNull(event.getLine(2)).matches("-?\\d+")) {
            if (!"wins".equalsIgnoreCase(event.getLine(3)) && !"games".equalsIgnoreCase(event.getLine(3))
                    && !"kills".equalsIgnoreCase(event.getLine(3))) return;
            Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
                if (Integer.parseInt(Objects.requireNonNull(event.getLine(2))) > 0) {
                    TopTenSign topTenSign = new TopTenSign();
                    topTenSign.setLocation(new Location(event.getBlock().getLocation()));
                    topTenSign.setPosition(Integer.parseInt(Objects.requireNonNull(event.getLine(2))));
                    topTenSign.setSorted(Objects.requireNonNull(Objects.requireNonNull(event.getLine(3)).toLowerCase()));
                    topTenSignsRepository.saveTopTenSign(topTenSign);
                    PlayerStatistic playerStatistic = main.getStatistics().getTopPlayer(
                            Integer.parseInt(Objects.requireNonNull(event.getLine(2))), Objects.requireNonNull(event.getLine(3)).toLowerCase());
                    updateSign(event.getBlock(), playerStatistic, Integer.parseInt(Objects.requireNonNull(event.getLine(2))),
                            Objects.requireNonNull(event.getLine(3)).toLowerCase());
                }
            });
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
                topTenSignsRepository.findByLocation(event.getBlock().getLocation()).ifPresent(topTenSignsRepository::deleteTopTenSign);
            });
        }
    }

    private void updateSign(Block block, PlayerStatistic playerStatistic, int place, String sorted) {
        Bukkit.getServer().getScheduler().runTask(main, () -> {
            if (!(block.getState() instanceof Sign)) return;
            Sign sign = (Sign) block.getState();
            sign.setLine(0, ChatColor.translateAlternateColorCodes('&', "&f&l" + Bukkit.getOfflinePlayer(playerStatistic.getUniqueID()).getName()));
            sign.setLine(1, ChatColor.translateAlternateColorCodes('&', "&7Stats"));
            sign.setLine(2, ChatColor.translateAlternateColorCodes('&', "&bPlatz " + place));
            switch (sorted) {
                case "kills":
                    sign.setLine(3, ChatColor.translateAlternateColorCodes('&', "&b&lKills " + playerStatistic.getKills()));
                    break;
                case "games":
                    sign.setLine(3, ChatColor.translateAlternateColorCodes('&', "&b&lGames " + playerStatistic.getGames()));
                    break;
                default: sign.setLine(3, ChatColor.translateAlternateColorCodes('&', "&b&lWins " + playerStatistic.getWins()));
            }
            sign.update(true);
        });
    }
}
