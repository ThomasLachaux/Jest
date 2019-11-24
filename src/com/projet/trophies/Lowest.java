package com.projet.trophies;

import com.projet.Color;
import com.projet.trophies.visitor.Visitor;

public class Lowest implements Trophy {

    private Color trophyColor;

    public Lowest(Color trophyColor) {
        this.trophyColor = trophyColor;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Lowest " + trophyColor;
    }
}
