package com.projet.models.trophies;

import com.projet.models.players.Player;
import com.projet.models.trophies.visitor.Visitor;

/**
 * Classe du best jest implémentant le pattern visitor
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public class BestJest implements Trophy {
    @Override
    public Player accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Best Jest";
    }
}
