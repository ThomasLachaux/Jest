package com.projet.strategies;

import com.projet.Card;
import com.projet.players.Player;
import com.projet.utils.Random;

import java.util.ArrayList;

public class RandomStrategy extends BotStrategy {

    public RandomStrategy(String name) {
        super(name);
    }

    @Override
    public Card askWhichCardToShow(Card cardA, Card cardB) {
        Card chosenCard = Math.random() <= 0.5 ? cardA : cardB;
        hasChoseCard(chosenCard);
        return chosenCard;
    }

    @Override
    public Player askWhichPlayerToSteal(ArrayList<Player> otherPlayers) {
        if(otherPlayers.size() == 0) {
            System.out.println(name + " doit se voler lui même");
            return null;
        }

        else {
            Player chosenPlayer = otherPlayers.get(Random.ranInt(0, otherPlayers.size() - 1));
            hasChoseStolenPlayer(chosenPlayer);
            return chosenPlayer;
        }
    }

    @Override
    public Card askWhichCardToSteal(Player stolenPlayer) {
        Card stolenCard = stolenPlayer.stealCard(Random.ranInt(0, stolenPlayer.getCurrentCardSize() - 1));
        hasChoseCard(stolenCard);
        return stolenCard;
    }
}
