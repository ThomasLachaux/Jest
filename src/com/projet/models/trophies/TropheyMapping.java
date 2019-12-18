package com.projet.models.trophies;

import com.projet.models.Card;
import com.projet.models.Color;

import java.util.HashMap;
import java.util.Map;

public class TropheyMapping {

    private HashMap<Card, Trophy> map;

    public TropheyMapping(HashMap<Card, Trophy> map) {
        this.map = map;
    }

    public static HashMap<Card, Trophy> generateDefaultMapping() {
        HashMap<Card, Trophy> trophies = new HashMap<>();

        // Spades
        trophies.put(new Card(Color.Spade, 1), new Highest(Color.Club));
        trophies.put(new Card(Color.Spade, 2), new Majority(3));
        trophies.put(new Card(Color.Spade, 3), new Majority(2));
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

    public Trophy computeTrophy(Card card) {
        return map.get(card);
    }

    public Card findCard(Trophy find) {
        for(Map.Entry<Card, Trophy> trophy : map.entrySet()) {
            if(find == trophy.getValue()) {
                return trophy.getKey();
            }
        }
        return null;
    }

}
