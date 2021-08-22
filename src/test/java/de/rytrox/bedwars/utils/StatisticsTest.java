package de.rytrox.bedwars.utils;

import de.timeout.libs.sql.QueryBuilder;
import de.timeout.libs.sql.SQLite;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsTest {

    @Mock
    private Player player;

    @Mock
    private SQLite db;

    @Mock
    private QueryBuilder query;

    private Statistics statistics;

    @Before
    public void mock() {
        statistics = new Statistics(db);
    }

    @Test
    public void shouldReturnInvalid() throws SQLException {
        assertNull(statistics.getValue(player, "29438765"));
    }

    @Test
    public void shouldReturnDefaultValue() throws SQLException {
        Mockito.when(player.getUniqueId()).thenAnswer(invocationOnMock -> UUID.fromString("121a9207-cd9a-4717-ba06-bb96667492f1"));
        Mockito.when(db.prepare(Mockito.eq("SELECT games FROM Stats WHERE uuid = ?"), Mockito.eq("121a9207-cd9a-4717-ba06-bb96667492f1")))
                .thenReturn(query);

        assertEquals(query, statistics.getValue(player, "games"));
    }
}
