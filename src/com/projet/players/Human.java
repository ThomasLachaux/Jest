package com.projet.players;

import com.projet.Console;
import com.projet.strategies.HumanStrategy;

public class Human extends Player {
    public Human(String name) {
        super(name, new HumanStrategy());
        System.out.println("Création du joueur " + name);
    }

    @Override
    public String favoriteColor() {
        return Console.GREEN;
    }


}
