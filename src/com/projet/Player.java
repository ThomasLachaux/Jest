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

        System.out.println("--- " + this.name + " ---");
        return scanner;
    }

    public void askWhichCardToFaceUp() {
        Scanner scanner = startPrompt();
        System.out.println("Quelle carte choisissez-vous de mettre face visible ?");

        Card cardA = current.get(0);
        Card cardB = current.get(1);

        System.out.print("1) " + cardA.toString() + "     ");
        System.out.println("2) " + cardB.toString());
        // todo: faire de la validation
        chooseFaceUpCard(scanner.nextInt() - 1);
    }

    public void askWhichPlayerToSteal(ArrayList<Player> players) {
        Scanner scanner = startPrompt();
        int i = 1;
        ArrayList<Player> otherPlayers = new ArrayList<>();
        System.out.println("Quel joueur voler ?");
        for(Player player: players) {
            // On n'affiche pas sois même ou un joueur qui n'a plus de cartes
            if(this != player && player.getCurrentCardSize() != 0) {
                System.out.print(i + ") " + player.getName() + "     ");
                otherPlayers.add(player);
                i++;
            }
        }

        // todo: validation !
        Player stolenPlayer = otherPlayers.get(scanner.nextInt() - 1);


        System.out.println("Quelle carte voler ?");
        System.out.println(stolenPlayer.displayCards(true));

        // todo: validation si 2 cartes
        // todo: validation scanner !
        Card stolenCard = stolenPlayer.stealCard(scanner.nextInt() - 1);
        jest.add(stolenCard);
    }

    public int getCurrentCardSize() {
        return current.size();
    }

    public String getName() {
        return name;
    }

    public String displayCards(boolean withIndexes) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < current.size(); i++) {
            if(withIndexes) {
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

    public void giveCardsToStack(Game game) {
        for(int i = 0 ; i < current.size(); i++) {
            game.addToStack(current.remove(i));
        }
    }

    // todo: jocker = 0
    // todo: egalité pas prise en compte
    @Override
    public int compareTo(Player o) {
        return  o.getVisibleCard().getValue() - getVisibleCard().getValue();
    }
}
