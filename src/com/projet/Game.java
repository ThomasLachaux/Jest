package com.projet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Game {

    private static Game instance;
    private LinkedList<Card> stack;
    private ArrayList<Player> players;

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }

        return instance;
    }

    private Game() {
        Console.startGame();
        players = new ArrayList<>();
        stack = new LinkedList<>();
        createCards();
        shuffleCards();
        createPlayers(3);

        while(stack.size() > 0) {
           playTurn();
        }
    }

    public void playTurn() {
        distributeCards();

        // Quelle carte reveler
        for (Player player : players) {
            player.askWhichCardToFaceUp();
        }

        displayCurrentGame();
        Collections.sort(players);

        // Quelle carte voler ?
        for(Player player : players) {
            player.askWhichPlayerToSteal(players);
        }

        // Redonner toutes les cartes à la pile
        for(Player player : players) {
            player.giveCardsToStack(this);
        }
    }

    public void createCards() {
        for(int i = 1; i <= 4; i++) {
            for(Color color : Color.values()) {
                if(color != Color.Jocker)
                    stack.add(new Card(color, i));
            }
        }

        stack.add(new Card(Color.Jocker, 0));
    }

    public void shuffleCards() {
        Collections.shuffle(stack);
    }

    public void distributeCards() {
        System.out.println("Distribution des cartes...");
        for (Player player : players) {
            player.addCardFaceDown(stack.poll(), stack.poll());
        }
        System.out.println("Nombre de cartes restantes: " + stack.size());
    }

    public void createPlayers(int number) {
        for(int i = 0; i < number; i++) {
            players.add(new Player("Joueur " + (i + 1)));
        }
    }

    public void displayCurrentGame() {
        for(Player player: players) {
            System.out.println(player.getName() + ": " + player.displayCards());
        }
    }

    public void addToStack(Card card) {
        stack.add(card);
    }
}
