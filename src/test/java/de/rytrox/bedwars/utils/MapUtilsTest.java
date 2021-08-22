package de.rytrox.bedwars.utils;

import de.timeout.libs.sql.SQLite;
import org.bukkit.Location;
import org.bukkit.World;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MapUtilsTest {

    MapUtils mapUtils;

    @Mock
    World world;

    Location location;

    @Before
    public void mock() {
        mapUtils = new MapUtils(new SQLite(new File("")));
        location = new Location(world, 0.1, 0.1, 0.1, 0.5F, 0.5F);
    }

    @Test
    public void testConvertLocationToString() {
        Mockito.when(world.getName()).thenAnswer(invocationOnMock -> "World");
        assertEquals("World - 0,100000 - 0,100000 - 0,100000 - 0,500000 - 0,500000", mapUtils.convertLocationToString(location));
    }

    /*@Test
    public void testConvertStringToLocation() {
        Mockito.when(world.getName()).thenAnswer(invocationOnMock -> "World");
        Mockito.when(Bukkit.getWorld("World")).thenReturn(world);
        Location newLocation = mapUtils.convertStringToLocation("World - 0,100000 - 0,100000 - 0,100000 - 0,500000 - 0,500000");
        assertEquals("World", newLocation.getWorld().getName());
        assertEquals(0.1, newLocation.getX());
        assertEquals(0.1, newLocation.getY());
        assertEquals(0.1, newLocation.getZ());
        assertEquals(0.5F, newLocation.getYaw());
        assertEquals(0.5F, newLocation.getPitch());
    }*/
}
