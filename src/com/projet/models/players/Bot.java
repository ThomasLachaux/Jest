package com.projet.models.players;

import com.projet.models.strategies.Strategy;
import com.projet.views.Console;

/**
 * Classe d'un bot
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
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
