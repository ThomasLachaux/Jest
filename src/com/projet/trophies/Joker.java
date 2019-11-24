package com.projet.trophies;

import com.projet.Player;
import com.projet.trophies.visitor.Visitor;

public class Joker implements Trophy {
    @Override
    public Player accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Joker";
    }
}
