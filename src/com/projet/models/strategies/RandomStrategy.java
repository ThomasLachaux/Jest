package com.projet.models.strategies;

import com.projet.models.Card;
import com.projet.models.players.Player;
import com.projet.models.utils.Random;

import java.util.ArrayList;

/**
 * Classe de la strategie ou le bot choisi des cartes aléatoirement
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public class RandomStrategy extends BotStrategy {

    public RandomStrategy(String name) {
        super(name);
    }

    /**
     * Le bot choisi de manière aléatoire une carte à montrer
     */
    @Override
    public Card askWhichCardToShow(Card cardA, Card cardB) {
        Card chosenCard = Math.random() <= 0.5 ? cardA : cardB;
        hasChoseCard(chosenCard);
        return chosenCard;
    }
    /**
     * Le bot choisi de manière aléatoire quel joueur voler
     */
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
    /**
     * Le bot choisi de manière aléatoire une carte à voler
     */
    @Override
    public Card askWhichCardToSteal(Player stolenPlayer) {
        Card stolenCard = stolenPlayer.stealCard(Random.ranInt(0, stolenPlayer.getCurrentCardSize() - 1));
        hasChoseCard(stolenCard);
        return stolenCard;
    }
}
