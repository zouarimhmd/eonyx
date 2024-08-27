package com.zouarimhmd.eonyxTest.service;

import com.zouarimhmd.eonyxTest.model.Spectator;
import com.zouarimhmd.eonyxTest.model.Trainer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

    public void performShow(List<Trainer> trainers, Spectator spectator) {
        for (Trainer trainer : trainers) {
            trainer.askMonkeyToPerformTricks();
            trainer.getMonkey().getTricks().forEach(trick ->
                    spectator.reactToTrick(trick, trainer.getMonkey())
            );
        }
    }
}