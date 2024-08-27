package com.zouarimhmd.eonyxTest.service;

import com.zouarimhmd.eonyxTest.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ShowServiceTest {

    private ShowService showService;
    private Trainer trainer1;
    private Trainer trainer2;
    private Spectator spectator;

    @Captor
    private ArgumentCaptor<Trick> trickCaptor;

    @BeforeEach
    void setUp() {
        showService = new ShowService();

        Trick handstand = new Trick("Handstand", TrickType.ACROBATICS);
        Trick juggling = new Trick("Juggling", TrickType.ACROBATICS);
        Trick playingDrums = new Trick("Playing Drums", TrickType.MUSIC);

        Monkey monkey1 = mock(Monkey.class);
        when(monkey1.getTricks()).thenReturn(List.of(handstand, juggling));

        Monkey monkey2 = mock(Monkey.class);
        when(monkey2.getTricks()).thenReturn(List.of(playingDrums));

        trainer1 = new Trainer("Trainer 1", monkey1);
        trainer2 = new Trainer("Trainer 2", monkey2);

        spectator = mock(Spectator.class);
    }

    @Test
    void testPerformShow_HappyPath() {
        showService.performShow(List.of(trainer1, trainer2), spectator);

        verify(trainer1.getMonkey(), times(2)).performTrick(any(Trick.class));
        verify(trainer2.getMonkey(), times(1)).performTrick(any(Trick.class));
        verify(spectator, times(2)).reactToTrick(any(Trick.class), eq(trainer1.getMonkey()));
        verify(spectator, times(1)).reactToTrick(any(Trick.class), eq(trainer2.getMonkey()));
    }

    @Test
    void testPerformShow_UnhappyPath_EmptyTrainerList() {
        assertThrows(IllegalArgumentException.class, () -> showService.performShow(List.of(), spectator));
    }

    @Test
    void testPerformShow_UnhappyPath_NullSpectator() {
        assertThrows(IllegalArgumentException.class, () -> showService.performShow(List.of(trainer1, trainer2), null));
    }

    @Test
    void testPerformShow_UnhappyPath_TrainerWithNoMonkey() {
        Trainer trainerWithoutMonkey = new Trainer("Trainer Without Monkey", null);
        assertThrows(IllegalArgumentException.class, () -> showService.performShow(List.of(trainerWithoutMonkey), spectator));
    }
}
