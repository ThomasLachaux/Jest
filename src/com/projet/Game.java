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

        for(int i = 0; i < stack.size() / players.size(); i++) {
            // Les tours vont de 1 à n
            playTurn(i + 1);
        }
    }

    public void playTurn(int turn) {
        distributeCards();

        // Quelle carte reveler
        for (Player player : players) {
            player.askWhichCardToFaceUp();
        }

        displayCurrentGame();

        ArrayList<Player> hasntPlayedPlayers = new ArrayList<>(players);
        Collections.sort(hasntPlayedPlayers);

        Player stealerPlayer = hasntPlayedPlayers.get(0);

        // Il se passe x actions (x le nombre de joueurs)
        for(int i = 0; i < players.size(); i++) {
            // Quelle carte voler ?

            Player stolenPlayer = stealerPlayer.askWhichPlayerToSteal(players);

            // On enleve le joueur des hasn't played players
            hasntPlayedPlayers.remove(stealerPlayer);

            // if stolenPlayer hasn't played
            if(stolenPlayer.getJestSize() == turn - 1) {
                stealerPlayer = stolenPlayer;
            }

            else {
                if(hasntPlayedPlayers.size() > 0)
                    stealerPlayer = hasntPlayedPlayers.get(0);
            }
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
