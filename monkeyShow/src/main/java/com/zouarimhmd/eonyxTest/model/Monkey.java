package com.zouarimhmd.eonyxTest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Monkey {
    private String name;
    private List<Trick> tricks;

    public void performTrick(Trick trick) {
        System.out.println(name + " performs trick: " + trick.getName());
    }
}
