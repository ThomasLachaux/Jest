package com.projet;

public enum Color {
    Jocker("\uD83D\uDE08"),
    Spade("♠"),
    Diamond("♦"),
    Heart("♥"),
    Club("♣");

    private String symbol;

    Color(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
