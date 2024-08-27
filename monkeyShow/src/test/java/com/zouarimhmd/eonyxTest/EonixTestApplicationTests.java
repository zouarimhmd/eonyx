package com.zouarimhmd.eonyxTest;

import com.zouarimhmd.eonyxTest.model.*;
import com.zouarimhmd.eonyxTest.service.ShowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class EonixTestApplicationTests {

	@Autowired
	private ShowService showService;

	@Test
	void testShowPerformance() {
		Trick handstand = new Trick("Handstand", TrickType.ACROBATICS);
		Trick juggling = new Trick("Juggling", TrickType.ACROBATICS);
		Trick playingDrums = new Trick("Playing Drums", TrickType.MUSIC);

		Monkey monkey1 = mock(Monkey.class);
		when(monkey1.getName()).thenReturn("Monkey 1");
		when(monkey1.getTricks()).thenReturn(List.of(handstand, juggling));

		Monkey monkey2 = mock(Monkey.class);
		when(monkey2.getName()).thenReturn("Monkey 2");
		when(monkey2.getTricks()).thenReturn(List.of(playingDrums));

		Trainer trainer1 = new Trainer("Trainer 1", monkey1);
		Trainer trainer2 = new Trainer("Trainer 2", monkey2);

		Spectator spectator = mock(Spectator.class);

		showService.performShow(List.of(trainer1, trainer2), spectator);

		verify(monkey1, times(2)).performTrick(any(Trick.class));
		verify(monkey2, times(1)).performTrick(any(Trick.class));
		verify(spectator, times(1)).reactToTrick(handstand, monkey1);
		verify(spectator, times(1)).reactToTrick(juggling, monkey1);
		verify(spectator, times(1)).reactToTrick(playingDrums, monkey2);
	}
}