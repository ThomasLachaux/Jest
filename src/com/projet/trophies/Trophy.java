package com.projet.trophies;

import com.projet.Card;
import com.projet.Color;

public abstract class Trophy {

    public static Trophy computeTrophy(Card card) {
        switch (card.getColor()) {
            case Spade:
                switch (card.getValue()) {
                    case 1:
                        return new Highest(Color.Club);

                    case 2:
                        return new Majority(3);

                    case 3:
                        return new Majority(2);

                    case 4:
                        return new Lowest(Color.Club);
                }
                break;

            case Club:
                switch (card.getValue()) {
                    case 1:
                        return new Highest(Color.Spade);

                    case 2:
                        return new Lowest(Color.Heart);

                    case 3:
                        return new Highest(Color.Heart);

                    case 4:
                        return new Lowest(Color.Spade);
                }
                break;

            case Diamond:
                switch (card.getValue()) {
                    case 1:
                        return new Majority(4);

                    case 2:
                        return new Highest(Color.Diamond);

                    case 3:
                        return new Lowest(Color.Diamond);

                    case 4:
                        return new NoJoke();
                }
                break;

            case Heart:
                return new Joker();

            case Jocker:
                return new BestJest();
        }

        try {
            throw new Exception("Erreur");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
