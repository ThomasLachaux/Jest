package com.projet.strategies;

import com.projet.Card;
import com.projet.Color;
import com.projet.players.Player;

import java.util.ArrayList;

public class BlackStrategy extends BotStrategy {

    public BlackStrategy(String name) {
        super(name);
    }

    // Affiche la carte la plus haute en numero et si pareil, plus haute en couleur
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

    @Override
    public Card askWhichCardToSteal(Player stolenPlayer) {
        Card chosenCard = stolenPlayer.stealVisibleCard();
        hasChoseCard(chosenCard);
        return chosenCard;
    }
}
