package com.projet.models.strategies;

import com.projet.models.Card;
import com.projet.models.Game;
import com.projet.models.utils.EventType;
import com.projet.models.utils.Scanner;
import com.projet.models.players.Player;

import java.util.ArrayList;

public class HumanStrategy implements Strategy {
    @Override
    public Card askWhichCardToShow(Card cardA, Card cardB) {
        System.out.println("Quelle carte choisissez-vous de mettre face visible ?");
        System.out.print("1) " + cardA.toString() + "     ");
        System.out.println("2) " + cardB.toString());

        Game.getInstance().notifyObservers(EventType.CHOOSE_CARD);
        int response = Scanner.nextInt(2);
        Game.getInstance().notifyObservers(EventType.CHOOSED_CARD);


        return response == 1 ? cardA : cardB;
    }

    @Override
    public Player askWhichPlayerToSteal(ArrayList<Player> otherPlayers) {
        Game.getInstance().notifyObservers(EventType.STEAL_PLAYER, otherPlayers);
        if(otherPlayers.size() == 0) {
            System.out.println("Vous êtes obligé de voler vous même :/");
            return null;
        }

        else {
            System.out.println("Quel joueur voler ?");

            for (int i = 0; i < otherPlayers.size(); i++) {
                Player player = otherPlayers.get(i);
                System.out.print((i + 1) + ") " + player.getName() + "     ");
            }
            System.out.println();
            Player stolenPlayer = otherPlayers.get(Scanner.nextInt(otherPlayers.size()) - 1);
            Game.getInstance().notifyObservers(EventType.STOLE_PLAYER, otherPlayers);

            return stolenPlayer;
        }
    }

    @Override
    public Card askWhichCardToSteal(Player stolenPlayer) {
        System.out.println("Quelle carte voler ?");
        Game.getInstance().notifyObservers(EventType.STEAL_CARD, stolenPlayer);
        System.out.println(stolenPlayer.displayCards(true));
        int response = Scanner.nextInt(stolenPlayer.getCurrentCardSize()) - 1;
        Game.getInstance().notifyObservers(EventType.STOLE_CARD, stolenPlayer);

        return stolenPlayer.stealCard(response);
    }
}
