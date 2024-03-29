package com.projet.models;

import com.projet.models.players.Bot;
import com.projet.models.players.Human;
import com.projet.models.players.Player;
import com.projet.models.strategies.BlackStrategy;
import com.projet.models.strategies.RandomStrategy;
import com.projet.models.strategies.Strategy;
import com.projet.models.trophies.TrophyMapping;
import com.projet.models.trophies.Trophy;
import com.projet.models.trophies.visitor.TrophyVisitor;
import com.projet.models.utils.Entry;
import com.projet.models.utils.EventType;
import com.projet.models.utils.Observable;
import com.projet.models.utils.Scanner;
import com.projet.views.Console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
/**
 * Classe principale du moteur du jeu contenant le déroulé de la partie
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public class Game extends Observable implements Runnable {

    private static Game instance;
    private LinkedList<Card> stack;
    private ArrayList<Player> players;
    private LinkedList<Card> tmpStack;
    private ArrayList<Trophy> trophies;
    private TrophyMapping trophyMapping;
    private int extension = 0;

    private Player currentPlayer;

    /**
     * Méthode pour le singleton si il n'existe pas d'instance on en créer une
     * @return instance
     */
    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }

        return instance;
    }

    /**
     * Déroulé d'une partie ( création des cartes,choix des paramètres du jeu, distibution par tour, calcul des scores
     */
    @Override
    public void run() {
        notifyObservers(EventType.GAME_START, null);
        players = new ArrayList<>();
        stack = new LinkedList<>();
        tmpStack = new LinkedList<>();
        createCards();
        shuffleCards();
        createPlayers();
        chooseExtension();


        distributeAndShowTrophies();
        notifyObservers(EventType.GAME_SET_UP, players);



        // On joue tant que notre pile temporaire n'est plus vide.
        // (Elle est de 6 pour 3 joueurs dans tous les tours sauf le dernier)
        int i = 1;
        do {
            playTurn(i);
            i++;
        } while (tmpStack.size() != 0 || (extension == 1 && stack.size() != 0));

        for (Player player : players) {
            player.addToJest(player.getCard(0));
        }

        System.out.println("Fin du jeu !");
        giveTrophies();

        System.out.println(com.projet.views.Console.RED + "--- Résultats ---" + com.projet.views.Console.RESET);
        for (Player player : players) {
            player.getScore().calculateAll();
        }

        notifyObservers(EventType.SCORE_GIVEN);

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

    /**
     * Affiche les trophées en supprimant les trophées de la pile de carte
     */
    public void distributeAndShowTrophies() {
        trophyMapping = new TrophyMapping(TrophyMapping.generateDefaultMapping());

        System.out.println("--- Trophées ---");
        trophies = new ArrayList<>();

        Card cardA = stack.poll();
        Trophy trophyA = trophyMapping.computeTrophy(cardA);
        trophies.add(trophyA);

        System.out.print(trophyA + " (" + cardA + ")" + "     ");

        // Si il y a 3 joueurs, on ajoute encore un trophée
        if (players.size() == 3) {
            Card cardB = stack.poll();
            Trophy trophyB = trophyMapping.computeTrophy(cardB);
            trophies.add(trophyB);
            System.out.println(trophyB + " (" + cardB + ")");
        }
    }

    /**
     * Demande et crée les joueurs et les bots
     */
    public void createPlayers() {

        int players = Scanner.nextInt(0, 4);
        int bots;
        int botDifficulty = 1;
        bots = Scanner.nextInt(0, players);

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

    /**
     * Joue un tour
     * @param turn index du tour, commence à 1
     */
    public void playTurn(int turn) {
        System.out.println(Console.RED + "--- Tour " + turn + " ---" + com.projet.views.Console.RESET);

        // On verifie si c'est le premier tour
        if (turn == 1 || extension == 1) {
            distributeCardsFirst();
        } else {
            distributeCard();
        }
        System.out.println("Il reste " + stack.size() + " cartes");

        // Notifie la Console et l'interfae que le jeu demarre
        notifyObservers(EventType.TURN_START, turn);

        // Quelle carte reveler
        if (extension != 2) {
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
        if (stack.size() != 0) {
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
    }

    /**
     * Créer les cartes et les ajoute à la pile des cartes
     */
    public void createCards() {
        for (int i = 1; i <= 4; i++) {
            for (Color color : Color.values()) {
                if (color != Color.Jocker)
                    stack.add(new Card(color, i));
            }
        }

        stack.add(new Card(Color.Jocker, 0));
    }

    /**
     * Mélange les cartes
     */
    public void shuffleCards() {
        Collections.shuffle(stack);
    }

    /**
     * Fait une distribution des cartes. Appelée que pour le premier tour
     */
    public void distributeCardsFirst() {
        System.out.println("Distribution des cartes...");
        for (Player player : players) {
            player.addCardFaceDown(stack.poll(), stack.poll());
        }
    }

    /**
     * Fait une redistribution des cartes. Appelée à partir du 2ème tour
     */
    public void distributeCard() {
        System.out.println("Redistribution des cartes...");
        for (Player player : players) {
            player.addCardFaceDown(tmpStack.poll(), tmpStack.poll());
        }
    }

    /**
     * Getter des trophés
     * @return trophés
     */
    public ArrayList<Trophy> getTrophies() {
        return trophies;
    }

    /**
     * affiche le jeu dans la console
     */
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

    /**
     * permet de choisir l'extension
     */
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

    /**
     * distribue les trophés a la fin du jeu
     */
    public void giveTrophies() {
        TrophyVisitor visitor = new TrophyVisitor(players);
        for (int i = 0; i < trophies.size(); i++) {
            Trophy trophy = trophies.get(i);
            Player winner = trophy.accept(visitor);
            if (winner != null) {
                winner.addToJest(trophyMapping.findCard(trophy));
                // Fait un mapping entre l'index du trohpé et le gagnant
                Entry<Integer, Player> mapping = new Entry<>(i, winner);
                notifyObservers(EventType.TROPHEY_GIVEN, mapping);
                System.out.println("Le trophée " + trophy.toString() + " est donnée à " + winner.toString());
            } else {
                System.out.println("Le trophée est donnée à personne");
            }
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public TrophyMapping getTrophyMapping() {
        return trophyMapping;
    }
}
