package com.zouarimhmd.eonyxTest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Trainer {
    private String name;
    private Monkey monkey;

    public void askMonkeyToPerformTricks() {
        monkey.getTricks().forEach(monkey::performTrick);
    }
}