package com.projet.models.trophies;

import com.projet.models.players.Player;
import com.projet.models.trophies.visitor.Visitor;

public interface Trophy {
    Player accept(Visitor visitor);
}
