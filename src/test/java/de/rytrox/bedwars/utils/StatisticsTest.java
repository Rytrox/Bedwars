package de.rytrox.bedwars.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import de.timeout.libs.sql.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsTest {

    private final Player player = Bukkit.getPlayer("121a9207cd9a4717ba06bb96667492f1");
    private final Path path = Paths.get("src", "test", "resources", "database.db");
    private final SQLite sqLite = new SQLite(path.toFile());
    private final Statistics statistics = new Statistics(sqLite);

    @BeforeEach
    public void mock() {
        MockBukkit.mock();
    }

    @Test
    void testGetValue() throws SQLException {
        assertEquals(0, statistics.getValue(player, "games"));
        assertEquals(0, statistics.getValue(player, "wins"));
        assertEquals(0, statistics.getValue(player, "kills"));
        assertEquals(0, statistics.getValue(player, "deaths"));
    }

    @Test
    void testSetValue() throws SQLException {
        statistics.setValue(player, "games", 1);
        statistics.setValue(player, "wins", 2);
        statistics.setValue(player, "kills", 3);
        statistics.setValue(player, "deaths", 4);
        assertEquals(1, statistics.getValue(player, "games"));
        assertEquals(2, statistics.getValue(player, "wins"));
        assertEquals(3, statistics.getValue(player, "kills"));
        assertEquals(4, statistics.getValue(player, "deaths"));
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }
}
