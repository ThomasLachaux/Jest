package com.projet.trophies;

import com.projet.trophies.visitor.Visitor;

public class Joker implements Trophy {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Joker";
    }
}
