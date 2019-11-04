package com.projet;

public class Card {

    // Todo: ajouter une enum.toString()
    public enum Color {Jocker, Spade, Diamond, Heart, Club};

    private Color color;
    private int value;
    private boolean faceDown;

    public Card(Color color, int value) {
        this.color = color;
        this.value = value;
        this.faceDown = true;

        if(value < 1 || value > 5) {
            System.out.println("Erreur: Les valeurs doivent être comprises entre 1 et 5.");
            System.exit(1);
        }
    }

    public Card setFaceDown() {
        faceDown = true;
        return this;
    }

    public Card setFaceUp() {
        faceDown = false;
        return this;
    }

}
