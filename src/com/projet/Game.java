package com.projet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Game {

    private static Game instance;
    private LinkedList<Card> cards;
    private ArrayList<Player> players;

    public static Game getInstance() {
        if(instance == null) {
            System.out.println("JEST");
            instance = new Game();
        }

        return instance;
    }

    private Game() {
        players = new ArrayList<>();
        cards = new LinkedList<>();
        createCards();
        shuffleCards();
        createPlayers(3);

        while(cards.size() > 0) {
           playTurn();
        }
    }

    public void playTurn() {
        distributeCards();

        for (Player player : players) {
            player.askWhichCardToFaceUp();
        }

        displayCurrentGame();
        Collections.sort(players);

        for(Player player : players) {
            player.askWhichPlayerToSteal(players);
        }
    }

    public void createCards() {
        for(int i = 1; i <= 4; i++) {
            for(Color color : Color.values()) {
                if(color != Color.Jocker)
                    cards.add(new Card(color, i));
            }
        }

        cards.add(new Card(Color.Jocker, 1));
    }

    public void shuffleCards() {
        Collections.shuffle(cards);
    }

    public void distributeCards() {
        System.out.println("Distribution des cartes...");
        for (Player player : players) {
            player.addCardFaceDown(cards.poll(), cards.poll());
        }
    }

    public void createPlayers(int number) {
        for(int i = 0; i < number; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
    }

    public void displayCurrentGame() {
        for(Player player: players) {
            System.out.println(player.getName() + ": " + player.displayCards());
        }
    }
}
