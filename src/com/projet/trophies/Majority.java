package com.projet.trophies;

import com.projet.players.Player;
import com.projet.trophies.visitor.Visitor;

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
