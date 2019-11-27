package com.projet.strategies;

import com.projet.Card;
import com.projet.players.Player;
import com.projet.players.Strategy;

import java.util.ArrayList;

public class RandomStrategy implements Strategy {
    @Override
    public Card askWhichCardToShow(Card cardA, Card cardB) {
        return null;
    }

    @Override
    public Player askWhichPlayerToSteal(ArrayList<Player> otherPlayers) {
        return null;
    }

    @Override
    public Card askWhichCardToSteal(Player stolenPlayer) {
        return null;
    }
}
