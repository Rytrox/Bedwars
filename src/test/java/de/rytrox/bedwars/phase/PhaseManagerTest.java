package de.rytrox.bedwars.phase;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.phase.events.PhaseChangeEvent;
import de.rytrox.bedwars.phase.phases.EndPhase;
import de.rytrox.bedwars.phase.phases.GamePhase;
import de.rytrox.bedwars.phase.phases.IngamePhase;
import de.rytrox.bedwars.phase.phases.LobbyPhase;

import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PhaseManager.class })
public class PhaseManagerTest {

    private PhaseManager manager;

    @Mock
    private Bedwars main;

    @Mock
    private GamePhase mockPhase;

    @Mock
    private PluginManager pluginManager;

    @Before
    public void setup() {
        this.manager = new PhaseManager(main);
    }

    @Test
    public void shouldStartWithLobbyPhaseAndHaveCorrectOrder() {
        assertTrue(this.manager.getCurrentPhase() instanceof LobbyPhase);
        assertTrue(this.manager.getCurrentPhase().next() instanceof IngamePhase);
        assertTrue(this.manager.getCurrentPhase().next().next() instanceof EndPhase);
        assertTrue(this.manager.getCurrentPhase().next().next().next() instanceof LobbyPhase);
    }

    @Test
    public void shouldEnableNextPhase() throws IllegalAccessException {
        FieldUtils.writeDeclaredField(manager, "currentPhase", mockPhase, true);

        try(MockedStatic<Bukkit> mockedStatic = Mockito.mockStatic(Bukkit.class)) {
            mockedStatic.when(Bukkit::getPluginManager).thenReturn(pluginManager);

            Mockito.doReturn(mockPhase).when(mockPhase).next();
            Mockito.doNothing().when(mockPhase).onEnable();
            Mockito.doNothing().when(pluginManager).callEvent(Mockito.any(Event.class));
            Mockito.doNothing().when(mockPhase).onDisable();

            manager.next();

            Mockito.verify(mockPhase, Mockito.times(1)).onDisable();
            Mockito.verify(mockPhase, Mockito.times(1)).onEnable();
        }


    }

    @Test
    public void shouldNotStartNewPhaseWhenEventIsCancelled() throws Exception {
        PhaseChangeEvent mock = PowerMockito.mock(PhaseChangeEvent.class);
        FieldUtils.writeDeclaredField(manager, "currentPhase", mockPhase, true);

        PowerMockito.whenNew(PhaseChangeEvent.class).withAnyArguments()
                .thenReturn(mock);
        PowerMockito.when(mock.isCancelled()).thenAnswer(invocation -> true);
        try(MockedStatic<Bukkit> mockedStatic = Mockito.mockStatic(Bukkit.class)) {
            mockedStatic.when(Bukkit::getPluginManager).thenReturn(pluginManager);
            Mockito.doNothing().when(pluginManager).callEvent(Mockito.any(Event.class));

            manager.next();

            Mockito.verify(mockPhase, Mockito.times(0)).onDisable();
            Mockito.verify(mockPhase, Mockito.times(0)).onEnable();
        }
    }
}
