package com.projet.trophies;

import com.projet.players.Player;
import com.projet.trophies.visitor.Visitor;

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
