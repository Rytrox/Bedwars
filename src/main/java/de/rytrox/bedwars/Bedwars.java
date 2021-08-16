package de.rytrox.bedwars;

import de.rytrox.bedwars.utils.ScoreboardManager;
import de.rytrox.bedwars.listeners.ShopListener;
import de.timeout.libs.config.ConfigCreator;
import de.timeout.libs.config.UTFConfig;
import de.timeout.libs.log.ColoredLogger;
import de.timeout.libs.sql.MySQL;
import de.timeout.libs.sql.SQL;
import de.timeout.libs.sql.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;

public class Bedwars extends JavaPlugin {

    private UTFConfig config;
    private final ScoreboardManager scoreboardManager = new ScoreboardManager();
    private SQL database;

    public Bedwars()
    {
        super();
    }

    protected Bedwars(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        // Nutze im Logger ColorCodes mit '&'
        ColoredLogger.enableColoredLogging('&', getLogger(), "&8[&6Bedwars&8]");
        // reload config
        reloadConfig();
        // register Listeners
        Bukkit.getPluginManager().registerEvents(new ShopListener(this), this);
        // loads the database type and the database from the configs
        loadDatabase();
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

    @NotNull
    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    private void loadDatabase() {
        if(config.contains("mysql.enabled")) {
            if(config.getBoolean("mysql.enabled")) {
                String host;
                int port;
                String database;
                String username;
                String password;
                if(config.contains("mysql.host")) host = config.getString("mysql.host"); else host = "localhost";
                if(config.contains("mysql.port")) port = config.getInt("mysql.port"); else port = 3306;
                if(config.contains("mysql.database")) database = config.getString("mysql.database"); else database = "database";
                if(config.contains("mysql.username")) username = config.getString("mysql.username"); else username = "username";
                if(config.contains("mysql.password")) password = config.getString("mysql.password"); else password = "password";
                this.database = new MySQL(host, port, database, username, password);
            }
        }

        File file = new File(this.getDataFolder() + "/database.db");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.database = new SQLite(file);
    }
}
