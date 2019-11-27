package com.projet.players;

import com.projet.Card;

import java.util.ArrayList;

public interface Strategy {
    Card askWhichCardToShow(Card cardA, Card cardB);
    Player askWhichPlayerToSteal(ArrayList<Player> otherPlayers);
    Card askWhichCardToSteal(Player stolenPlayer);
}
