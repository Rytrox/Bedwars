package de.rytrox.bedwars.utils;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.database.entity.Location;
import de.rytrox.bedwars.database.entity.PlayerStatistic;
import de.rytrox.bedwars.database.entity.TopTenSigns;
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
                PlayerStatistic playerStatistic = main.getStatistics().getTopPlayer(sign.getPosition());
                updateSign(sign.getLocation().toBukkitLocation().getBlock(), playerStatistic, sign.getPosition());
            });
        });
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (Objects.equals(event.getLine(0), "BW") && Objects.equals(event.getLine(1), "STATS")
                && Objects.requireNonNull(event.getLine(2)).matches("-?\\d+")) {
            Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
                if (Integer.parseInt(Objects.requireNonNull(event.getLine(2))) <= main.getStatistics().getStatisticSize()
                        && Integer.parseInt(Objects.requireNonNull(event.getLine(2))) > 0) {
                    TopTenSigns topTenSigns = new TopTenSigns();
                    topTenSigns.setLocation(new Location(event.getBlock().getLocation()));
                    topTenSigns.setPosition(Integer.parseInt(Objects.requireNonNull(event.getLine(2))));
                    topTenSignsRepository.saveTopTenSigns(topTenSigns);
                    PlayerStatistic playerStatistic = main.getStatistics().getTopPlayer(Integer.parseInt(Objects.requireNonNull(event.getLine(2))));
                    updateSign(event.getBlock(), playerStatistic, Integer.parseInt(Objects.requireNonNull(event.getLine(2))));
                }
            });
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            Bukkit.getServer().getScheduler().runTaskAsynchronously(main, () -> {
                topTenSignsRepository.findByLocation(event.getBlock().getLocation()).ifPresent(topTenSignsRepository::deleteTopTenSigns);
            });
        }
    }

    private void updateSign(Block block, PlayerStatistic playerStatistic, int place) {
        Bukkit.getServer().getScheduler().runTask(main, () -> {
            if (!(block.getState() instanceof Sign)) return;
            Sign sign = (Sign) block.getState();
            sign.setLine(0, ChatColor.translateAlternateColorCodes('&', "&6&l" + Bukkit.getOfflinePlayer(playerStatistic.getUniqueID()).getName()));
            sign.setLine(1, ChatColor.translateAlternateColorCodes('&', "&d&lPlatz " + place));
            sign.setLine(2, ChatColor.translateAlternateColorCodes('&', "&d&lWins " + playerStatistic.getWins()));
            sign.setLine(3, ChatColor.translateAlternateColorCodes('&', "&d&lKills " + playerStatistic.getKills()));
            sign.update(true);
        });
    }
}
