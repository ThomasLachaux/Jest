package com.projet;

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

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
