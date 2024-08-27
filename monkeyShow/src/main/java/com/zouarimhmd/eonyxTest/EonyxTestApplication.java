package com.zouarimhmd.eonyxTest;

import com.zouarimhmd.eonyxTest.model.*;
import com.zouarimhmd.eonyxTest.service.ShowService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class EonyxTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EonyxTestApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ShowService showService) {
		return args -> {
			// Create Tricks
			Trick handstand = new Trick("Handstand", TrickType.ACROBATICS);
			Trick juggling = new Trick("Juggling", TrickType.ACROBATICS);
			Trick playingDrums = new Trick("Playing Drums", TrickType.MUSIC);
			Trick singing = new Trick("Singing", TrickType.MUSIC);

			// Create Monkeys
			Monkey monkey1 = new Monkey("Monkey 1", List.of(handstand, juggling));
			Monkey monkey2 = new Monkey("Monkey 2", List.of(playingDrums, singing));

			// Create Trainers
			Trainer trainer1 = new Trainer("Trainer 1", monkey1);
			Trainer trainer2 = new Trainer("Trainer 2", monkey2);

			// Create Spectator
			Spectator spectator = new Spectator();

			// Run the show
			showService.performShow(List.of(trainer1, trainer2), spectator);
		};
	}
}
