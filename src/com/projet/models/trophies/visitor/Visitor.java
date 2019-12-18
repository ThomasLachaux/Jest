package com.projet.models.trophies.visitor;

import com.projet.models.players.Player;
import com.projet.models.trophies.*;

public interface Visitor {
    Player visit(Highest highest);
    Player visit(Lowest lowest);
    Player visit(Majority majority);
    Player visit(Joker joker);
    Player visit(BestJest bestJest);
    Player visit(NoJoke noJoke);
}
