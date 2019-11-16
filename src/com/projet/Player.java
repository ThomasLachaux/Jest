package com.projet;

import java.util.*;

public class Player implements Comparable<Player> {

    // Equivalent de posée sur le diagramme mais on ne fait pas de français
    private ArrayList<Card> current;

    // Après verification des règle, le talon posée dans le diagramme s'appelle jest
    private ArrayList<Card> jest;

    private String name;

    public Player(String name) {
        System.out.println("Création du joueur " + name);
        current = new ArrayList<>();
        jest = new ArrayList<>();
        this.name = name;
    }

    // On a bien 2 cartes par joueurs tout le temps ????
    // todo: faire des validations/exceptions
    public void addCardFaceDown(Card cardA, Card cardB) {
        current.add(cardA.setFaceDown());
        current.add(cardB.setFaceDown());
    }

    public Scanner startPrompt() {
        Scanner scanner = new Scanner(System.in);

        System.out.println(this.toString() + ":");
        return scanner;
    }

    public void askWhichCardToFaceUp() {
        Scanner scanner = startPrompt();
        System.out.println("1) " + cardA().toString());
        System.out.println("2) " + cardB().toString());

        System.out.println("Quelle carte choisissez-vous de mettre face visible ?");
        // todo: faire de la validation
        chooseFaceUpCard(scanner.nextInt() - 1);
    }

    public void askWhichPlayerToSteal(ArrayList<Player> players) {

        int i = 1;
        ArrayList<Player> otherPlayers = new ArrayList<>();
        System.out.println("Quel joueur voler ?");
        for(Player player: players) {
            if(this != player) {
                System.out.println(i + ") " + player.getName());
                otherPlayers.add(player);
                i++;
            }
        }

        Scanner scanner = startPrompt();

        System.out.println("Quelle carte voler ?");

        // todo: validation !
        Player stolenPlayer = otherPlayers.get(scanner.nextInt() - 1);
        System.out.println(stolenPlayer.displayCards(true));

        // todo: validation si 2 cartes
        // todo: validation scanner !
        Card stolenCard = stolenPlayer.stealCard(scanner.nextInt());
        jest.add(stolenCard);
    }

    public String getName() {
        return name;
    }

    public String displayCards() {
        return cardA().toStringFromOutside() + " " + cardB().toStringFromOutside();
    }

    public String displayCards(boolean withIndexes) {
        return withIndexes ? "1) " + cardA().toStringFromOutside() + " 2) " + cardB().toStringFromOutside() : displayCards();
    }

    public Card stealCard(int index) {
        return current.remove(index);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Ajoute une carte dans sa main
     * @param card
     */
    public void giveCard(Card card) {

    }

    // Dans le diagramme: put
    public void chooseFaceUpCard(int index) {
        current.get(index).setFaceUp();
    }

    // Pour l'instant, dans le MVP, on ne passe pas play en abstract
    public void play() {

    }

    /**
     * Raccourci pour rendre le code plus lisible
     */
    public Card cardA() {
        return current.get(0);
    }

    public Card cardB() {
        return current.get(1);
    }

    public Card getVisibleCard() {
        return cardA().isFaceUp() ? cardA() : cardB();
    }

    // todo: jocker = 0
    // todo: egalité pas prise en compte
    @Override
    public int compareTo(Player o) {
        return  o.getVisibleCard().getValue() - getVisibleCard().getValue();
    }
}
