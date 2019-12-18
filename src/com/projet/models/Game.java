package com.projet.models;

import com.projet.models.players.Bot;
import com.projet.models.players.Human;
import com.projet.models.players.Player;
import com.projet.models.strategies.BlackStrategy;
import com.projet.models.strategies.RandomStrategy;
import com.projet.models.strategies.Strategy;
import com.projet.models.trophies.TropheyMapping;
import com.projet.models.trophies.Trophy;
import com.projet.models.trophies.visitor.TrophyVisitor;
import com.projet.models.utils.Console;
import com.projet.models.utils.EventType;
import com.projet.models.utils.Observable;
import com.projet.models.utils.Scanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Game extends Observable {

    private static Game instance;
    private LinkedList<Card> stack;
    private ArrayList<Player> players;
    private LinkedList<Card> tmpStack;
    private ArrayList<Trophy> trophies;
    private TropheyMapping tropheyMapping;
    private int extension = 0;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }

        return instance;
    }
    public void partie(){
        notifyObservers(EventType.START_GAME, null);
        players = new ArrayList<>();
        stack = new LinkedList<>();
        tmpStack = new LinkedList<>();
        createCards();
        shuffleCards();
        createPlayers();
        chooseExtension();
        distributeAndShowTrophies();


        // On joue tant que notre pile temporaire n'est plus vide.
        // (Elle est de 6 pour 3 joueurs dans tous les tours sauf le dernier)
        int i = 1;
        do {
            System.out.println("stack: " + stack.size());
            playTurn(i);
            i++;
        } while (tmpStack.size() != 0 || (extension == 1 && stack.size() != 0));


        System.out.println("Fin du jeu !");
        giveTrophies();

        System.out.println(Console.RED + "--- Résultats ---" + Console.RESET);
        for (Player player : players) {
            player.getScore().calculateAll();
        }

        String justification = "| %-16s | %-4d |%n";

        System.out.println("+------------------+------+");
        System.out.println("|      Joueur      | Jest |");
        System.out.println("+------------------+------+");
        for (Player player : players) {
            System.out.format(justification, player.getName(), player.getScore().getPoints());
        }
        System.out.println("+------------------+------+");
    }

    private Game() {

    }

    public void distributeAndShowTrophies() {
        tropheyMapping = new TropheyMapping(TropheyMapping.generateDefaultMapping());

        System.out.println("--- Trophées ---");
        trophies = new ArrayList<>();

        Card cardA = stack.poll();
        Trophy trophyA = tropheyMapping.computeTrophy(cardA);
        trophies.add(trophyA);

        System.out.print(trophyA + " (" + cardA + ")" + "     ");

        // Si il y a 3 joueurs, on ajoute encore un trophée
        if (players.size() == 3) {
            Card cardB = stack.poll();
            Trophy trophyB = tropheyMapping.computeTrophy(cardB);
            trophies.add(trophyB);
            System.out.println(trophyB + " (" + cardB + ")");
        }
    }

    public void createPlayers() {
        System.out.println("Combien y a-t-il de joueurs ? (Entre 3 et 4)");
        int players = Scanner.nextInt(0, 4);
        int bots;
        int botDifficulty = 1;
        System.out.println("Combien y a-il de bots ? (Entre 0 et " + players + ")");
        bots = Scanner.nextInt(0, players);

        System.out.println("Quelle difficulté des bots voulez-vous ?");
        System.out.println("1) Facile     2) Difficile");
        botDifficulty = Scanner.nextInt(2);

        for (int i = 0; i < players - bots; i++) {
            String name = "Player " + (i + 1);
            this.players.add(new Human(name));
        }

        for (int i = 0; i < bots; i++) {
            String name = "Bot " + (i + 1);

            Strategy strategy = botDifficulty == 1 ? new RandomStrategy(name) : new BlackStrategy(name);
            this.players.add(new Bot(name, strategy));
        }
    }

    public void playTurn(int turn) {
        System.out.println(Console.RED + "--- Tour " + turn + " ---" + Console.RESET);

        // On verifie si c'est le premier tour
        if (turn == 1 || extension == 1) {
            distributeCardsFirst();
        } else {
            distributeCard();
        }
        System.out.println(tmpStack.size());
        System.out.println("Il reste " + stack.size() + " cartes");

        // Quelle carte reveler
        if(extension != 2) {
            for (Player player : players) {
                player.askWhichCardToFaceUp();
            }
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
        if (extension == 0 || extension == 2) {
            for (Player player : players) {
                Card cardPlayer = player.pollHand();
                Card cardStack = stack.poll();
                if (cardStack != null) {
                    tmpStack.add(cardPlayer);
                    tmpStack.add(cardStack);
                }
                Collections.shuffle(tmpStack);
            }
        } else if (extension == 1) {
            // Redonner toutes les cartes à la pile et melange
            for (Player player : players) {
                Card cardPlayer = player.pollHand();
                if (stack.size() != 0) {
                    stack.add(cardPlayer);
                }
                Collections.shuffle(stack);
            }
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

    public ArrayList<Trophy> getTrophies() {
        return trophies;
    }

    public void displayCurrentGame() {
        String justification = "| %-16s | %-14s |%n";

        System.out.println("+------------------+----------------+");
        System.out.println("|      Joueur      |     Cartes     |");
        System.out.println("+------------------+----------------+");
        for (Player player : players) {
            System.out.format(justification, player.getName(), player.displayCards());
        }
        System.out.println("+------------------+----------------+");
    }

    public void chooseExtension() {
        System.out.println("voulez vous une extension ?");
        System.out.println("1) Oui     2) Non");
        int tmpChoose = Scanner.nextInt(2);
        if (tmpChoose == 1) {
            System.out.println("Quelle extension voulez vous?");
            System.out.println("1) remise en jeu des cartes dans le stack    2)jouer carte face caché  ");
            extension = Scanner.nextInt(2);
        }

    }

    public void giveTrophies() {
        TrophyVisitor visitor = new TrophyVisitor(players);
        for (Trophy trophy : trophies) {
            Player winner = trophy.accept(visitor);
            if (winner != null) {
                winner.addToJest(tropheyMapping.findCard(trophy));
                System.out.println("Le trophée " + trophy.toString() + " est donnée à " + winner.toString());
            } else {
                System.out.println("Le trophée est donnée à personne");
            }
        }
    }
}
