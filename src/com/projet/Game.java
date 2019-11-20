package com.projet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Game {

    private static Game instance;
    private LinkedList<Card> stack;
    private ArrayList<Player> players;
    private LinkedList<Card> tmpDeck;

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
        tmpDeck = new LinkedList<>();
        createCards();
        shuffleCards();
        createPlayers(3);

        while(stack.size() > 0) {
           playTurn();
        }
    }

    public void playTurn() {
        // On verifie si c'est le premier tour
        if(players.get(0).getJestSize() == 0){
            distributeCardsFirst();
        }
        else {
            distributeCard();
        }

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

        // Redonner toutes les cartes à la pile temporaire
        for(Player player : players) {
           Card cardPlayer =  player.pollHand();
           Card cardStack = stack.poll();
           tmpDeck.add(cardPlayer);
           tmpDeck.add(cardStack);
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

    public void distributeCardsFirst() {
        System.out.println("Distribution des cartes...");
        for (Player player : players) {
            player.addCardFaceDown(stack.poll(), stack.poll());
        }
        System.out.println("Nombre de cartes restantes: " + stack.size());
    }
    public void distributeCard() {
        System.out.println("Redistribution des cartes...");
        for (Player player : players) {
            player.addCardFaceDown(tmpDeck.poll(), tmpDeck.poll());
        }
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

}
