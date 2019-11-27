package com.projet.players;

import com.projet.utils.Console;

public class Bot extends Player {
    public Bot(String name, Strategy strategy) {
        super(name, strategy);
        System.out.println("Création du bot " + name);
    }

    @Override
    public String favoriteColor() {
        return Console.YELLOW;
    }
}
