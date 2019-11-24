package com.projet.trophies;

import com.projet.Card;
import com.projet.Color;
import com.projet.Player;
import com.projet.trophies.visitor.Visitor;

import java.util.HashMap;
import java.util.Map;

public interface Trophy {

    Player accept(Visitor visitor);

    static HashMap<Card, Trophy> getTrophies() {
        HashMap<Card, Trophy> trophies = new HashMap<>();

        // Spades
        trophies.put(new Card(Color.Spade, 1), new Highest(Color.Club));
        trophies.put(new Card(Color.Spade, 2), new Majority(3));
        trophies.put(new Card(Color.Spade, 2), new Majority(2));
        trophies.put(new Card(Color.Spade, 4), new Lowest(Color.Club));

        // Clubs
        trophies.put(new Card(Color.Club, 1), new Highest(Color.Spade));
        trophies.put(new Card(Color.Club, 2), new Lowest(Color.Heart));
        trophies.put(new Card(Color.Club, 3), new Highest(Color.Heart));
        trophies.put(new Card(Color.Club, 4), new Lowest(Color.Spade));

        // Diamonds
        trophies.put(new Card(Color.Diamond, 1), new Majority(4));
        trophies.put(new Card(Color.Diamond, 2), new Highest(Color.Diamond));
        trophies.put(new Card(Color.Diamond, 3), new Lowest(Color.Diamond));
        trophies.put(new Card(Color.Diamond, 4), new NoJoke());

        // Hearts
        for(int i = 1; i <= 4; i++) {
            trophies.put(new Card(Color.Heart, i), new Joker());
        }

        trophies.put(new Card(Color.Jocker, 0), new BestJest());

        return trophies;
    }

    static Trophy computeTrophy(Card card) {
        return getTrophies().get(card);
    }

    static Card findCard(Trophy find) {
        for(Map.Entry<Card, Trophy> trophy : getTrophies().entrySet()) {
            if(find == trophy.getValue()) {
                return trophy.getKey();
            }
        }
        return null;
    }
}
