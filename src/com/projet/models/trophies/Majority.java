package com.projet.models.trophies;

import com.projet.models.players.Player;
import com.projet.models.trophies.visitor.Visitor;

/**
 * Classe du trophée majority implémentant le pattern visitor
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public class Majority implements Trophy {

    private int majorityValue;

    public Majority(int majorityValue) {
        this.majorityValue = majorityValue;
    }

    @Override
    public Player accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Majority " + majorityValue;
    }

    public int getMajorityValue() {
        return majorityValue;
    }
}
