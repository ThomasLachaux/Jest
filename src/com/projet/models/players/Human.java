package com.projet.models.players;

import com.projet.models.strategies.HumanStrategy;
import com.projet.views.Console;

/**
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
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
