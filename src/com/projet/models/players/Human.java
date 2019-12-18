package com.projet.models.players;

import com.projet.models.utils.Console;
import com.projet.models.strategies.HumanStrategy;

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
