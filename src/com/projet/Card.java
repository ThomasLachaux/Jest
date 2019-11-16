package com.projet;

public class Card {

    // Todo: ajouter une enum.toString()
    private Color color;
    private int value;
    private boolean faceDown;

    public Card(Color color, int value) {
        this.color = color;
        this.value = value;
        this.faceDown = true;

        if(value < 1 || value > 4) {
            System.out.println("Erreur: Les valeurs doivent être comprises entre 1 et 4.");
            System.exit(1);
        }

        if(color == Color.Jocker)
            this.value = 0;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public Card setFaceDown() {
        faceDown = true;
        return this;
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public boolean isFaceUp() {
        return !faceDown;
    }

    public void setFaceUp() {
        faceDown = false;
    }

    public String toStringFromOutside() {
        return faceDown ? "⛶" : toString();
    }

    @Override
    public String toString() {
        return (value != 0 ? value : "") + String.valueOf(color);
    }

}
