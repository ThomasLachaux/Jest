package com.projet.models.trophies;

import com.projet.models.Color;
import com.projet.models.players.Player;
import com.projet.models.trophies.visitor.Visitor;

/**
 * Classe du trophée lowest implémentant le pattern visitor
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public class Lowest implements Trophy {

    private Color trophyColor;

    public Lowest(Color trophyColor) {
        this.trophyColor = trophyColor;
    }

    @Override
    public Player accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Lowest " + trophyColor;
    }

    public Color getTrophyColor() {
        return trophyColor;
    }
}
