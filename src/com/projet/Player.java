package com.projet;

import java.util.ArrayList;

public class Player {

    // Equivalent de posée sur le diagramme mais on ne fait pas de français
    private ArrayList<Card> current;

    // Après verification des règle, le talon posée dans le diagramme s'appelle jest
    private ArrayList<Card> jest;

    public Player() {
        current = new ArrayList<>();
        jest = new ArrayList<>();
    }

    // On a bien 2 cartes par joueurs tout le temps ????
    // todo: faire des validations/exceptions
    public void addCardFaceDown(Card cardA, Card cardB) {
        current.add(cardA.setFaceDown());
        current.add(cardB.setFaceDown());
    }

    // Dans le diagramme: put
    public void chooseFaceUpCard(int index) {
        current.get(index).setFaceUp();
    }

    // Pour l'instant, dans le MVP, on ne passe pas play en abstract
    public void play() {

    }
}
