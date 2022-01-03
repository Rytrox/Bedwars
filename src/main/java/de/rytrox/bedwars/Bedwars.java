package de.rytrox.bedwars;

import de.rytrox.bedwars.commands.SetupCommand;
import de.rytrox.bedwars.database.entity.*;
import de.rytrox.bedwars.database.repository.MapRepository;
import de.rytrox.bedwars.database.repository.PlayerStatisticsRepository;
import de.rytrox.bedwars.listeners.ShopListener;
import de.rytrox.bedwars.phase.PhaseManager;
import de.rytrox.bedwars.commands.BedwarsMapCommand;
import de.rytrox.bedwars.map.MapUtils;
import de.rytrox.bedwars.utils.Messages;
import de.rytrox.bedwars.utils.TopTenBoardHandler;
import de.timeout.libs.config.ConfigCreator;
import de.timeout.libs.config.UTFConfig;
import de.timeout.libs.log.ColoredLogger;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.config.dbplatform.h2.H2Platform;
import io.ebean.config.dbplatform.mysql.MySqlPlatform;
import io.ebean.datasource.DataSourceConfig;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.h2.Driver;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class Bedwars extends JavaPlugin {

    private UTFConfig config;
    private UTFConfig language;
    private Messages messages;
    private Database database;
    private PhaseManager phaseManager;
    private MapUtils mapUtils;

    private PlayerStatisticsRepository statisticsRepository;
    private MapRepository mapRepository;

    public Bedwars()
    {
        super();
        // Nutze im Logger ColorCodes mit '&'
        ColoredLogger.enableColoredLogging('&', getLogger(), "&8[&6Bedwars&8]");


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
        Bukkit.getPluginManager().registerEvents(new TopTenBoardHandler(), this);
        Bukkit.getPluginManager().registerEvents(new ShopListener(this), this);
        // register Commands
        Objects.requireNonNull(getCommand("bedwarsmap")).setExecutor(new BedwarsMapCommand());
        Objects.requireNonNull(getCommand("setup")).setExecutor(new SetupCommand());
        // loads the database type and the database from the configs
        loadDatabase();

        this.messages = new Messages();

        this.mapUtils = new MapUtils();

        this.mapRepository = new MapRepository(database);

        this.statisticsRepository = new PlayerStatisticsRepository(database);
        this.phaseManager = new PhaseManager(this);

        Bukkit.getPluginManager().registerEvents(mapUtils, this);
    }

    @Override
    public void onDisable() {
        this.saveConfig();
        DatabaseFactory.shutdown();
    }

    @Override
    public @NotNull UTFConfig getConfig() {
        return config;
    }

    public UTFConfig getLanguage() {
        return language;
    }

    @Override
    public void reloadConfig() {
        try {
            File file = new ConfigCreator(this.getDataFolder(), Paths.get(""))
                    .copyDefaultFile(Paths.get("config.yml"), Paths.get("config.yml"));

            this.config = new UTFConfig(file);

            file = new ConfigCreator(this.getDataFolder(), Paths.get(""))
                    .copyDefaultFile(Paths.get(config.getString("language", "de-de") + ".yml"),
                            Paths.get(config.getString("language", "de-de") + ".yml"));

            this.language = new UTFConfig(file);
        } catch (IOException e) {
            this.getLogger().log(Level.SEVERE, "&cconfig.yml konnte nicht geladen werden!", e);
        }
    }

    @Override
    public void saveConfig() {
        try {
            this.config.save(new File(getDataFolder(), "config.yml"));
            this.language.save(new File(getDataFolder() + "languages/",
                    config.getString("languages", "de-de") + ".yml"));
        } catch (IOException e) {
            this.getLogger().log(Level.SEVERE, "&cconfig.yml konnte nicht gespeichert werden");
        }
    }

    @NotNull
    public Messages getMessages() {
        return messages;
    }

    @NotNull
    public Database getDatabase() {
        return database;
    }

    @NotNull
    public MapRepository getMapRepository() {
        return  mapRepository;
    }

    @NotNull
    public PlayerStatisticsRepository getStatistics() {
        return statisticsRepository;
    }

    @NotNull
    public PhaseManager getPhaseManager() {
        return phaseManager;
    }

    @NotNull
    public MapUtils getMapUtils() {
        return mapUtils;
    }

    /**
     * Loads the database from the information of the config.yml
     */
    private void loadDatabase() {
        ClassLoader originalContextClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.setRegister(true);
        databaseConfig.setDdlCreateOnly(true);
        databaseConfig.setDefaultServer(true);
        databaseConfig.setDdlRun(true);
        databaseConfig.setDdlGenerate(true);
        databaseConfig.setClasses(getDatabaseClasses());
        databaseConfig.setAutoPersistUpdates(true);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();

        if(config.getBoolean("mysql.enabled", false)) {
            databaseConfig.setDatabasePlatform(new MySqlPlatform());

            dataSourceConfig.setDriver("com.mysql.jdbc.Driver");
            dataSourceConfig.setUsername(config.getString("mysql.username", "username"));
            dataSourceConfig.setPassword(config.getString("mysql.password", "password"));
            dataSourceConfig.setUrl(String.format("jdbc:mysql://%s:%d/%s",
                    config.getString("mysql.host", "localhost"),
                    config.getInt("mysql.port", 3306),
                    config.getString("mysql.database", "database")));
        } else {
            try {
                DriverManager.registerDriver(new Driver());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            databaseConfig.setDatabasePlatform(new H2Platform());

            dataSourceConfig.setDriver("org.h2.Driver");
            dataSourceConfig.setUsername("sa");
            dataSourceConfig.setPassword("sa");
            dataSourceConfig.setUrl(String.format("jdbc:h2:%s;MV_STORE=false", new File(getDataFolder(), "Bedwars").getAbsolutePath()));
        }

        databaseConfig.setDataSourceConfig(dataSourceConfig);

        // create database
        try {
            database = DatabaseFactory.create(databaseConfig);
        } catch (Exception e) {
            databaseConfig.setDdlRun(false);
            database = DatabaseFactory.create(databaseConfig);
        }

        Thread.currentThread().setContextClassLoader(originalContextClassLoader);
    }

    /**
     * Hier schreiben wir die Entitys rein
     *
     * @return eine ArrayList aller zu benutzenden Entities
     */
    @NotNull
    public List<Class<?>> getDatabaseClasses() {
        return Arrays.asList(
                PlayerStatistic.class,
                Location.class,
                Map.class,
                Team.class,
                Spawner.class,
                TopTenSign.class
        );
    }
}
