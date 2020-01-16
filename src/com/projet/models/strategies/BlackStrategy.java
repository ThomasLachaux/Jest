package com.projet.models.strategies;

import com.projet.models.Card;
import com.projet.models.Color;
import com.projet.models.players.Player;

import java.util.ArrayList;

/**
 * Cette classe décrit la stratégie d'un bot qui essaye de jouer de façon a peu près normale
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public class BlackStrategy extends BotStrategy {

    public BlackStrategy(String name) {
        super(name);
    }

    /**
     * Affiche la carte la plus haute en numero et si pareil, plus haute en couleur
     * @param cardA première carte
     * @param cardB deuxième carte
     * @return carte choisie
     */
    @Override
    public Card askWhichCardToShow(Card cardA, Card cardB) {
        Card chosen;

        if (cardA.getValue() > cardB.getValue())
            chosen = cardA;

        else if (cardA.getValue() < cardB.getValue())
            chosen = cardB;

        else
            chosen = cardA.getColor().getOrder() > cardA.getColor().getOrder() ? cardA : cardB;

        hasChoseCard(chosen);

        return chosen;
    }

    /**
     * Essaye de choisir un joueur qui a un trèfle ou un pique
     * @param otherPlayers autres joueurs volables
     * @return joueur choisi
     */
    @Override
    public Player askWhichPlayerToSteal(ArrayList<Player> otherPlayers) {
        if (otherPlayers.size() == 0) {
            System.out.println(name + " doit se voler lui même");
            return null;
        } else {

            Player chosenPlayer = otherPlayers.get(0);

            for (Player player : otherPlayers) {
                Card visibleCard = player.getVisibleCard();

                if (visibleCard.getColor() == Color.Spade || visibleCard.getColor() == Color.Club) {
                    if (chosenPlayer.getVisibleCard().getValue() > visibleCard.getValue()) {
                        chosenPlayer = player;
                    }
                }

            }

            hasChoseStolenPlayer(chosenPlayer);
            return chosenPlayer;
        }
    }

    /**
     * Vole toujours la carte visible
     * @param stolenPlayer joueur volé
     * @return carte volée
     */
    @Override
    public Card askWhichCardToSteal(Player stolenPlayer) {
        Card chosenCard = stolenPlayer.stealVisibleCard();
        hasChoseCard(chosenCard);
        return chosenCard;
    }
}
