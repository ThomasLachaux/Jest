package com.projet.trophies;

import com.projet.trophies.visitor.Visitor;

public class NoJoke implements Trophy {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "No Joke";
    }
}
