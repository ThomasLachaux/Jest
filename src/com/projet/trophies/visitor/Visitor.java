package com.projet.trophies.visitor;

import com.projet.trophies.*;

public interface Visitor {
    void visit(Highest highest);
    void visit(Lowest lowest);
    void visit(Majority majority);
    void visit(Joker joker);
    void visit(BestJest bestJest);
    void visit(NoJoke noJoke);
}
