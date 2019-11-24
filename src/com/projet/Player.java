package com.projet;

import java.util.ArrayList;

public class Player implements Comparable<Player> {

    // Equivalent de posée sur le diagramme mais on ne fait pas de français
    private ArrayList<Card> current;

    // Après verification des règle, le talon posée dans le diagramme s'appelle jest
    private ArrayList<Card> jest;

    private String name;

    private Score score;

    public Player(String name) {
        System.out.println("Création du joueur " + name);
        current = new ArrayList<>();
        jest = new ArrayList<>();
        this.name = name;
        score = new Score(this);
    }

    // On a bien 2 cartes par joueurs tout le temps ????
    // todo: faire des validations/exceptions
    public void addCardFaceDown(Card cardA, Card cardB) {
        current.add(cardA.setFaceDown());
        current.add(cardB.setFaceDown());
    }

    public void startPrompt() {
        System.out.println("--- " + this.name + " ---");
    }

    public void askWhichCardToFaceUp() {
        startPrompt();
        System.out.println("Quelle carte choisissez-vous de mettre face visible ?");

        Card cardA = current.get(0);
        Card cardB = current.get(1);

        System.out.print("1) " + cardA.toString() + "     ");
        System.out.println("2) " + cardB.toString());
        chooseFaceUpCard(Scanner.nextInt(2) - 1);
    }

    public Player askWhichPlayerToSteal(ArrayList<Player> players) {
        startPrompt();
        int i = 1;

        ArrayList<Player> otherPlayers = new ArrayList<>();
        System.out.println("Quel joueur voler ?");
        for (Player player : players) {
            // On n'affiche que les joueurs ayant 2 cartes et pas soit même
            if (player.getCurrentCardSize() == 2 && player != this) {
                System.out.print(i + ") " + player.getName() + "     ");
                otherPlayers.add(player);
                i++;
            }
        }

        Player stolenPlayer;

        if (otherPlayers.size() == 0) {
            System.out.println("En fait, ne choisissez pas, vous devez vous voler à vous même \uD83D\uDE01");
            stolenPlayer = this;
        } else {
            stolenPlayer = otherPlayers.get(Scanner.nextInt(otherPlayers.size()) - 1);
        }


        System.out.println("Quelle carte voler ?");
        System.out.println(stolenPlayer.displayCards(true));

        Card stolenCard = stolenPlayer.stealCard(Scanner.nextInt(stolenPlayer.getCurrentCardSize()) - 1);
        jest.add(stolenCard);

        return stolenPlayer;
    }

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

    public String displayCards(boolean withIndexes) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < current.size(); i++) {
            if (withIndexes) {
                builder.append(i + 1);
                builder.append(") ");
            }

            builder.append(current.get(i).toStringFromOutside());
            builder.append("     ");
        }

        return builder.toString();
    }

    public String displayCards() {
        return displayCards(false);
    }

    public Card stealCard(int index) {
        System.out.println("Vous avez volé un " + current.get(index).toString());
        return current.remove(index);
    }

    @Override
    public String toString() {
        return name;
    }

    // Dans le diagramme: put
    public void chooseFaceUpCard(int index) {
        current.get(index).setFaceUp();
    }

    public Card getVisibleCard() {
        Card cardA = current.get(0);
        Card cardB = current.get(1);

        return cardA.isFaceUp() ? cardA : cardB;
    }

    // todo: rajouter un deck intermediaire yohann
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
