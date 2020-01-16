package com.projet.models.trophies;

import com.projet.models.players.Player;
import com.projet.models.trophies.visitor.Visitor;

/**
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public interface Trophy {
    Player accept(Visitor visitor);
}
