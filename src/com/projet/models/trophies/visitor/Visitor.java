package com.projet.models.trophies.visitor;

import com.projet.models.players.Player;
import com.projet.models.trophies.*;


/**
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public interface Visitor {
    Player visit(Highest highest);
    Player visit(Lowest lowest);
    Player visit(Majority majority);
    Player visit(Joker joker);
    Player visit(BestJest bestJest);
    Player visit(NoJoke noJoke);
}
