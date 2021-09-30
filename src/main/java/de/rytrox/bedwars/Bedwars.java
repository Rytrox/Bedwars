package de.rytrox.bedwars;

import de.rytrox.bedwars.map.MapUtils;
import de.rytrox.bedwars.utils.ScoreboardManager;
import de.rytrox.bedwars.listeners.ShopListener;
import de.rytrox.bedwars.utils.Statistics;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

public class Bedwars extends JavaPlugin {

    private UTFConfig config;
    private final ScoreboardManager scoreboardManager = new ScoreboardManager();
    private SQL db;
    private Statistics statistics;
    private MapUtils mapUtils;

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
        // creates a Statistics instance
        statistics = new Statistics(db);
        // updates the Statistics Datatable
        statistics.updateTable();
        // creates a MapUtils instance
        mapUtils = new MapUtils(db);
        // updates the MapUtils Datatables
        mapUtils.updateTables();
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

    @NotNull
    public SQL getDatabase() {
        return db;
    }

    @NotNull
    public Statistics getStatistics() {
        return statistics;
    }

    @NotNull
    public MapUtils getMapUtils() {
        return mapUtils;
    }

    /**
     * Loads the database from the information of the config.yml
     */
    private void loadDatabase() {
        if(config.getBoolean("mysql.enabled", false)) {
            int port = config.getInt("mysql.port", 3306);
            String host = config.getString("mysql.host", "localhost");
            String database = config.getString("mysql.database", "database");
            String username = config.getString("mysql.username", "username");
            String password = config.getString("mysql.password", "password");
            db = new MySQL(host, port, database, username, password);
            return;
        }

        File file = new File(this.getDataFolder(), "database.db");
        if(!file.exists()) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                this.getLogger().log(Level.SEVERE, "&4SQLiteDB konnte nicht erstellt werden!", e);
            }
        }
        db = new SQLite(file);
    }
}
