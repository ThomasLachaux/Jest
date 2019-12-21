package com.projet.models.strategies;

import com.projet.models.Card;
import com.projet.models.utils.Scanner;
import com.projet.models.players.Player;

import java.util.ArrayList;

public class HumanStrategy implements Strategy {
    @Override
    public Card askWhichCardToShow(Card cardA, Card cardB) {
        System.out.println("Quelle carte choisissez-vous de mettre face visible ?");
        System.out.print("1) " + cardA.toString() + "     ");
        System.out.println("2) " + cardB.toString());

        int response = Scanner.nextInt(2);


        return response == 1 ? cardA : cardB;
    }

    @Override
    public Player askWhichPlayerToSteal(ArrayList<Player> otherPlayers) {
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
            return otherPlayers.get(Scanner.nextInt(otherPlayers.size()) - 1);
        }
    }

    @Override
    public Card askWhichCardToSteal(Player stolenPlayer) {
        System.out.println("Quelle carte voler ?");
        System.out.println(stolenPlayer.displayCards(true));

        return stolenPlayer.stealCard(Scanner.nextInt(stolenPlayer.getCurrentCardSize()) - 1);
    }
}
