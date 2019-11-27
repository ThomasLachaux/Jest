package com.projet.trophies;

import com.projet.players.Player;
import com.projet.trophies.visitor.Visitor;

public interface Trophy {
    Player accept(Visitor visitor);
}
