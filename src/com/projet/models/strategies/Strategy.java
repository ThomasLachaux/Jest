package com.projet.models.strategies;

import com.projet.models.Card;
import com.projet.models.players.Player;

import java.util.ArrayList;

/**
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public interface Strategy {
    Card askWhichCardToShow(Card cardA, Card cardB);
    Player askWhichPlayerToSteal(ArrayList<Player> otherPlayers);
    Card askWhichCardToSteal(Player stolenPlayer);
}
