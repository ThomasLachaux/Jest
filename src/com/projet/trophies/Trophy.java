package com.projet.trophies;

import com.projet.Player;
import com.projet.trophies.visitor.Visitor;

public interface Trophy {
    Player accept(Visitor visitor);
}
