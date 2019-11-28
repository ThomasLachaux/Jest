package com.projet.strategies;

import com.projet.Card;
import com.projet.players.Player;

import java.util.ArrayList;

public interface Strategy {
    Card askWhichCardToShow(Card cardA, Card cardB);
    Player askWhichPlayerToSteal(ArrayList<Player> otherPlayers);
    Card askWhichCardToSteal(Player stolenPlayer);
}
