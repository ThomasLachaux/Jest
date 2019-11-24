package com.projet.trophies;

import com.projet.trophies.visitor.Visitor;

public class Majority implements Trophy {

    private int majorityValue;

    public Majority(int majorityValue) {
        this.majorityValue = majorityValue;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Majority " + majorityValue;
    }
}
