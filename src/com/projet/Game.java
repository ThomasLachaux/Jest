package com.projet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Game {

    private static Game instance;
    private LinkedList<Card> cards;
    private ArrayList<Player> players;

    public static Game getInstance() {
        if(instance == null)
            instance = new Game();

        return instance;
    }

    private Game() {
        players = new ArrayList<>();
        cards = new LinkedList<>();
        createCards();
        createPlayers(3);

    }

    public void createCards() {
        for(int i = 1; i <= 5; i++) {
            for(Card.Color color : Card.Color.values()) {
                cards.push(new Card(color, i));
            }
        }
    }

    public void shuffleCards() {
        Collections.shuffle(cards);
    }

    public void distributeCards() {

    }

    public void createPlayers(int number) {
        for(int i = 0; i < number; i++) {
            players.add(new Player());
        }
    }

}
