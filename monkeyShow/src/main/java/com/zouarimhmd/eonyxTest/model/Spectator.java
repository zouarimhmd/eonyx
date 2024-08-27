package com.zouarimhmd.eonyxTest.model;

import lombok.Data;
import lombok.NoArgsConstructor;

public class Spectator {

    public void reactToTrick(Trick trick, Monkey monkey) {
        if (trick.getType() == TrickType.ACROBATICS) {
            System.out.println("Spectator applauds during the acrobatics trick '" + trick.getName() + "' by " + monkey.getName());
        } else if (trick.getType() == TrickType.MUSIC) {
            System.out.println("Spectator whistles during the music trick '" + trick.getName() + "' by " + monkey.getName());
        }
    }
}