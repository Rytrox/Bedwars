package de.rytrox.bedwars;

import de.rytrox.bedwars.listeners.InvClickListener;
import de.rytrox.bedwars.listeners.VillagerClickListener;
import de.timeout.libs.config.ConfigCreator;
import de.timeout.libs.config.UTFConfig;
import de.timeout.libs.log.ColoredLogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;

public final class Bedwars extends JavaPlugin {

    private UTFConfig config;

    @Override
    public void onEnable() {
        // Nutze im Logger ColorCodes mit '&'
        ColoredLogger.enableColoredLogging('&', getLogger(), "&8[&6Bedwars&8]");
        // reload config
        reloadConfig();

        Bukkit.getPluginManager().registerEvents(new InvClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new VillagerClickListener(), this);
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    @Override
    public @NotNull UTFConfig getConfig() {
        return config;
    }

    @Override
    public void reloadConfig() {
        try {
            File file = new ConfigCreator(this.getDataFolder(), Paths.get(""))
                .copyDefaultFile(Paths.get("config.yml"), Paths.get("config.yml"));

            this.config = new UTFConfig(file);
        } catch (IOException e) {
            this.getLogger().log(Level.SEVERE, "&cconfig.yml konnte nicht geladen werden!", e);
        }
    }

    @Override
    public void saveConfig() {
        try {
            this.config.save(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            this.getLogger().log(Level.SEVERE, "&cconfig.yml konnte nicht gespeichert werden");
        }
    }
}
