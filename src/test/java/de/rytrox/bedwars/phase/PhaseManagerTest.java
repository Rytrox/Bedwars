package de.rytrox.bedwars.phase;

import de.rytrox.bedwars.Bedwars;
import de.rytrox.bedwars.phase.events.PhaseChangeEvent;
import de.rytrox.bedwars.phase.phases.EndPhase;
import de.rytrox.bedwars.phase.phases.GamePhase;
import de.rytrox.bedwars.phase.phases.IngamePhase;
import de.rytrox.bedwars.phase.phases.LobbyPhase;

import de.rytrox.bedwars.team.TeamManager;
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
@PrepareForTest({ PhaseManager.class, LobbyPhase.class })
public class PhaseManagerTest {

    private PhaseManager manager;

    @Mock
    private Bedwars main;

    @Mock
    private LobbyPhase mockLobbyPhase;

    @Mock
    private IngamePhase mockIngamePhase;

    @Mock
    private EndPhase mockEndPhase;

    @Mock
    private PluginManager pluginManager;

    @Mock
    private TeamManager teamChoosingManager;

    @Before
    public void setup() throws Exception {
        PowerMockito.whenNew(TeamManager.class).withNoArguments()
                .thenReturn(teamChoosingManager);
        PowerMockito.whenNew(LobbyPhase.class).withAnyArguments()
                .thenReturn(mockLobbyPhase);
        Mockito.doNothing().when(mockLobbyPhase).onEnable();

        this.manager = new PhaseManager(main);
    }

    @Test
    public void shouldStartWithLobbyPhaseAndHaveCorrectOrder() {
        Mockito.doReturn(mockIngamePhase).when(mockLobbyPhase).next();
        Mockito.doReturn(mockEndPhase).when(mockIngamePhase).next();
        Mockito.doReturn(mockLobbyPhase).when(mockEndPhase).next();

        assertTrue(this.manager.getCurrentPhase() instanceof LobbyPhase);
        assertTrue(this.manager.getCurrentPhase().next() instanceof IngamePhase);
        assertTrue(this.manager.getCurrentPhase().next().next() instanceof EndPhase);
        assertTrue(this.manager.getCurrentPhase().next().next().next() instanceof LobbyPhase);
    }

    @Test
    public void shouldEnableNextPhase() throws IllegalAccessException {
        FieldUtils.writeDeclaredField(manager, "currentPhase", mockLobbyPhase, true);

        try(MockedStatic<Bukkit> mockedStatic = Mockito.mockStatic(Bukkit.class)) {
            mockedStatic.when(Bukkit::getPluginManager).thenReturn(pluginManager);

            Mockito.doReturn(mockIngamePhase).when(mockLobbyPhase).next();
            Mockito.doNothing().when(mockIngamePhase).onEnable();
            Mockito.doNothing().when(pluginManager).callEvent(Mockito.any(Event.class));
            Mockito.doNothing().when(mockLobbyPhase).onDisable();

            manager.next();

            Mockito.verify(mockLobbyPhase, Mockito.times(1)).onDisable();
            Mockito.verify(mockIngamePhase, Mockito.times(1)).onEnable();
        }


    }

    @Test
    public void shouldNotStartNewPhaseWhenEventIsCancelled() throws Exception {
        PhaseChangeEvent mock = PowerMockito.mock(PhaseChangeEvent.class);
        FieldUtils.writeDeclaredField(manager, "currentPhase", mockLobbyPhase, true);
        Mockito.doReturn(mockIngamePhase).when(mockLobbyPhase).next();

        PowerMockito.whenNew(PhaseChangeEvent.class).withAnyArguments()
                .thenReturn(mock);
        PowerMockito.when(mock.isCancelled()).thenAnswer(invocation -> true);
        try(MockedStatic<Bukkit> mockedStatic = Mockito.mockStatic(Bukkit.class)) {
            mockedStatic.when(Bukkit::getPluginManager).thenReturn(pluginManager);
            Mockito.doNothing().when(pluginManager).callEvent(Mockito.any(Event.class));

            manager.next();

            Mockito.verify(mockLobbyPhase, Mockito.times(0)).onDisable();
            Mockito.verify(mockIngamePhase, Mockito.times(0)).onEnable();
        }
    }
}
