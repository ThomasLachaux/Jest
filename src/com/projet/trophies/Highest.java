package com.projet.trophies;

import com.projet.Color;
import com.projet.Player;
import com.projet.trophies.visitor.Visitor;

public class Highest implements Trophy {

    private Color trophyColor;

    public Highest(Color trophyColor) {
        this.trophyColor = trophyColor;
    }

    @Override
    public Player accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Highest " + trophyColor;
    }

    public Color getTrophyColor() {
        return trophyColor;
    }
}
