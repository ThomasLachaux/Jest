package com.projet.models;

/**
 * Enumeration des couleur de cartes
 */
public enum Color {
    Spade("♠", 5),
    Club("♣", 4),
    Diamond("♦", 3),
    Heart("♥", 2),
    Jocker("\uD83D\uDE08", 1);

    private String symbol;

    // L'ordre le plus grand est le plus prioritaire
    private int order;

    Color(String symbol, int order) {
        this.symbol = symbol;
        this.order = order;
    }

    /**
     * Permet de recuperer "order" qui est la puissance des cartes cela sert par exemple pour savoir qui joue en premier
     * @return
     */
    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
