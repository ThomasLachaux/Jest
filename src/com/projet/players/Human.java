package com.projet.players;

import com.projet.Console;

public class Human extends Player {
    @Override
    public String favoriteColor() {
        return Console.GREEN;
    }

    public Human(String name) {
        super(name);
    }


}
