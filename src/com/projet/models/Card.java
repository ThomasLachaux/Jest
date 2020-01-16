package com.projet.models;

public class Card {

    protected Color color;
    protected int value;
    private boolean faceDown;

    public Card(Color color, int value) {
        this.color = color;
        this.value = value;
        this.faceDown = true;

        if (value < 0 || value > 4) {
            System.out.println("Erreur: Les valeurs doivent être comprises entre 0 et 4.");
            System.exit(1);
        }

        if (color == Color.Jocker)
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

    public int setValue(int i) {
        return this.value = i;
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


    /**
     * Affiche la carte face caché ""⛶"" comme ca
     * @return
     */
    public String toStringFromOutside() {
        return faceDown ? "⛶" : toString();
    }

    @Override
    public String toString() {
        return (color != Color.Jocker ? value : "") + String.valueOf(color);
    }
    /**
     * Permet de savoir si il y a une égalité entre les cartes
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Card) {
            Card card = (Card) obj;

            return value == card.getValue() && color == card.getColor();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return value * 31 + color.getOrder();
    }

}
