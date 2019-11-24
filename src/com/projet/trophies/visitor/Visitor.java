package com.projet.trophies.visitor;

import com.projet.Player;
import com.projet.trophies.*;

public interface Visitor {
    Player visit(Highest highest);
    Player visit(Lowest lowest);
    Player visit(Majority majority);
    Player visit(Joker joker);
    Player visit(BestJest bestJest);
    Player visit(NoJoke noJoke);
}
