package com.projet.models.trophies;

import com.projet.models.players.Player;
import com.projet.models.trophies.visitor.Visitor;
/**
 * Classe du trophée no Joke implémentant le pattern visitor
 */
public class NoJoke implements Trophy {

    @Override
    public Player accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "No Joke";
    }
}
