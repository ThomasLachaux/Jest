package com.projet.players;

import com.projet.Card;

import java.util.ArrayList;

public class Display {

    private boolean isHuman;

    public Display(Player player) {
        isHuman = player instanceof Human;
    }

    public void playerCreation(String name) {
        if(isHuman)
            System.out.println("Création du joueur " + name);

        else
            System.out.println("Création du bot " + name);
    }

    public void askWhatCardToShow(Card cardA, Card cardB) {
        if(isHuman) {
            System.out.println("Quelle carte choisissez-vous de mettre face visible ?");
            System.out.print("1) " + cardA.toString() + "     ");
            System.out.println("2) " + cardB.toString());
        }
    }

    public void askWhichPlayerToSteal(ArrayList<Player> otherPlayers) {
        if(isHuman) {

            if(otherPlayers.size() == 0) {
                System.out.println("Vous êtes obligé de voler vous même :/");
            }

            else {
                System.out.println("Quel joueur voler ?");

                for (int i = 0; i < otherPlayers.size(); i++) {
                    Player player = otherPlayers.get(i);
                    System.out.print(i + ") " + player.getName() + "     ");
                }
            }
        }
    }

    public void askWhichCardToSteal(Player stolenPlayer) {
        if(isHuman) {
            System.out.println("Quelle carte voler ?");
            System.out.println(stolenPlayer.displayCards(true));
        }
    }
}
