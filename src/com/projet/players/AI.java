package com.projet.players;

import com.projet.Console;
import com.projet.strategies.RandomStrategy;

public class AI extends Player {


    public AI(String name) {
        super(name, new RandomStrategy());
        System.out.println("Création du bot " + name);
    }

    @Override
    public String favoriteColor() {
        return Console.YELLOW;
    }
}
