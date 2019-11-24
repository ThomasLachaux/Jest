package com.projet;

import com.projet.trophies.Trophy;
import com.projet.trophies.visitor.TrophyVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Game {

    private static Game instance;
    private LinkedList<Card> stack;
    private ArrayList<Player> players;
    private LinkedList<Card> tmpStack;
    private ArrayList<Trophy> trophies;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }

        return instance;
    }

    private Game() {
        Console.startGame();
        players = new ArrayList<>();
        stack = new LinkedList<>();
        tmpStack = new LinkedList<>();
        createCards();
        shuffleCards();
        createPlayers(3);
        distributeAndShowTrophies();

        // On joue tant que notre pile temporaire n'est plus vide.
        // (Elle est de 6 pour 3 joueurs dans tous les tours sauf le dernier)
        int i = 1;
        do {
            playTurn(i);
            i++;
        } while (tmpStack.size() != 0);

        System.out.println("Fin du jeu !");
    }

    public void distributeAndShowTrophies() {
        System.out.println("--- Trophées ---");
        trophies = new ArrayList<>();

        Card cardA = stack.poll();
        Trophy trophyA = Trophy.computeTrophy(cardA);

        System.out.print(trophyA + " (" + cardA + ")" + "     ");

        // Si il y a 3 joueurs, on ajoute encore un trophée
        if (players.size() == 3) {
            Card cardB = stack.poll();
            Trophy trophyB = Trophy.computeTrophy(cardB);
            System.out.println(trophyB + " (" + cardB + ")");
        }
    }

    public void playTurn(int turn) {
        System.out.println("--- Tour " + turn + " ---");
        // On verifie si c'est le premier tour
        if (turn == 1) {
            distributeCardsFirst();
        } else {
            distributeCard();
        }

        System.out.println("Il reste " + stack.size() + " cartes");

        // Quelle carte reveler
        for (Player player : players) {
            player.askWhichCardToFaceUp();
        }

        displayCurrentGame();

        ArrayList<Player> hasntPlayedPlayers = new ArrayList<>(players);
        Collections.sort(hasntPlayedPlayers);

        Player stealerPlayer = hasntPlayedPlayers.get(0);

        // Il se passe x actions (x le nombre de joueurs)
        for (int i = 0; i < players.size(); i++) {
            // Quelle carte voler ?

            Player stolenPlayer = stealerPlayer.askWhichPlayerToSteal(players);

            // On enleve le joueur des hasn't played players
            hasntPlayedPlayers.remove(stealerPlayer);

            // if stolenPlayer hasn't played
            if (stolenPlayer.getJestSize() == turn - 1) {
                stealerPlayer = stolenPlayer;
            } else {
                if (hasntPlayedPlayers.size() > 0)
                    stealerPlayer = hasntPlayedPlayers.get(0);
            }
        }

        // Redonner toutes les cartes à la pile temporaire et melange
        for (Player player : players) {
            Card cardPlayer = player.pollHand();
            Card cardStack = stack.poll();
            if (cardStack != null) {
                tmpStack.add(cardPlayer);
                tmpStack.add(cardStack);
            }
            Collections.shuffle(tmpStack);
        }
    }

    public void createCards() {
        for (int i = 1; i <= 4; i++) {
            for (Color color : Color.values()) {
                if (color != Color.Jocker)
                    stack.add(new Card(color, i));
            }
        }

        stack.add(new Card(Color.Jocker, 0));
    }

    public void shuffleCards() {
        Collections.shuffle(stack);
    }

    public void distributeCardsFirst() {
        System.out.println("Distribution des cartes...");
        for (Player player : players) {
            player.addCardFaceDown(stack.poll(), stack.poll());
        }
    }

    public void distributeCard() {
        System.out.println("Redistribution des cartes...");
        for (Player player : players) {
            player.addCardFaceDown(tmpStack.poll(), tmpStack.poll());
        }
    }

    public void createPlayers(int number) {
        for (int i = 0; i < number; i++) {
            players.add(new Player("Joueur " + (i + 1)));
        }
    }

    public ArrayList<Trophy> getTrophies() {
        return trophies;
    }

    public void displayCurrentGame() {
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.displayCards());
        }
    }

    public void giveTrophies() {
        TrophyVisitor visitor = new TrophyVisitor(players);

        for(Trophy trophy : trophies) {
            Player winner = trophy.accept(visitor);
            winner.addToJest(Trophy.findCard(trophy));
            System.out.println("Le trophée " + trophy.toString() + " est donnée à " + winner.toString());
        }
    }
}
