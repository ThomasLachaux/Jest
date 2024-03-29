package com.projet.models.players;

import com.projet.models.Card;
import com.projet.models.Game;
import com.projet.models.Score;
import com.projet.models.strategies.Strategy;
import com.projet.views.Console;

import java.util.ArrayList;
/**
 * Classe abstraite du joueur
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public abstract class Player implements Comparable<Player> {

    // Equivalent de posée sur le diagramme mais on ne fait pas de français
    private ArrayList<Card> current;

    // Après verification des règle, le talon posée dans le diagramme s'appelle jest
    private ArrayList<Card> jest;

    private String name;

    private Score score;

    private Strategy strategy;

    public abstract String favoriteColor();

    public Player(String name, Strategy strategy) {

        this.strategy = strategy;

        current = new ArrayList<>();
        jest = new ArrayList<>();
        this.name = name;
        score = new Score(this);
    }

    /**
     * Met les cartes face caché
     * @param cardA
     * @param cardB
     */
    public void addCardFaceDown(Card cardA, Card cardB) {
        current.add(cardA.setFaceDown());
        current.add(cardB.setFaceDown());
    }

    /**
     * Surligne le nom du joueur
     */
    public void startPrompt() {
        Game.getInstance().setCurrentPlayer(this);
        System.out.println(favoriteColor() + "--- " + this.name + " ---" + Console.RESET);
    }

    /**
     * Demande quel carte mettre face visible
     */
    public void askWhichCardToFaceUp() {
        startPrompt();

        Card cardA = current.get(0);
        Card cardB = current.get(1);

        Card cardChosen = strategy.askWhichCardToShow(cardA, cardB);

        cardChosen.setFaceUp();
    }

    /**
     * Demande quel joueur voler
     * @param players
     * @return
     */
    public Player askWhichPlayerToSteal(ArrayList<Player> players) {
        startPrompt();

        ArrayList<Player> otherPlayers = new ArrayList<>();

        for (Player player : players) {
            // On n'affiche que les joueurs ayant 2 cartes et pas soit même
            if (player.getCurrentCardSize() == 2 && player != this) {
                otherPlayers.add(player);
            }
        }

        Player stolenPlayer = strategy.askWhichPlayerToSteal(otherPlayers);

        if (otherPlayers.size() == 0) {
            stolenPlayer = this;
        }

        Card stolenCard = strategy.askWhichCardToSteal(stolenPlayer);
        jest.add(stolenCard);

        return stolenPlayer;
    }

    /**
     * Ajoute une carte au jest
     * @param card
     */
    public void addToJest(Card card) {
        jest.add(card);
    }

    public int getCurrentCardSize() {
        return current.size();
    }

    public String getName() {
        return name;
    }

    public Card getCard(int index) {
        return current.get(index);
    }

    /**
     * Affiche les cartes
     * @param withIndexes
     * @return
     */
    public String displayCards(boolean withIndexes) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < current.size(); i++) {
            if (withIndexes) {
                builder.append(i + 1);
                builder.append(") ");
            }

            builder.append(current.get(i).toStringFromOutside());

            if(i != current.size() - 1) {
                builder.append("     ");
            }
        }

        return builder.toString();
    }

    public String displayCards() {
        return displayCards(false);
    }

    /**
     * Affiche quelle carte a été volé et la retire de la main du joueur
     * @param index
     * @return
     */
    public Card stealCard(int index) {
        System.out.println(name + " a  perdu un " + current.get(index).toString());
        return current.remove(index);
    }

    /**
     * Permet de voler la carte non visible
     * @return
     */
    public Card stealVisibleCard() {
        int index = current.indexOf(getVisibleCard());

        return stealCard(index);
    }

    @Override
    public String toString() {
        return name;
    }

    public Card getVisibleCard() {
        Card cardA = current.get(0);
        Card cardB = current.get(1);

        return cardA.isFaceUp() ? cardA : cardB;
    }

    public Card pollHand() {
        return current.remove(0);
    }

    @Override
    public int compareTo(Player o) {
        int result = o.getVisibleCard().getValue() - getVisibleCard().getValue();

        if (result == 0) {
            result = o.getVisibleCard().getColor().getOrder() - getVisibleCard().getColor().getOrder();
        }

        return result;
    }

    public int getJestSize() {
        return jest.size();
    }

    public ArrayList<Card> getJest() {
        return jest;
    }

    public Score getScore() {
        return score;
    }

}
