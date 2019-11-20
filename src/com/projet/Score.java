package com.projet;

public class Score {
    private int points;
    private Player player;
    public Score(Player player) {
        points = 0;
        this.player = player;
    }

    public void calculateSpade() {
        for (Card card : player.getJest()) {
            if (card.getColor() == Color.Spade) {

                this.points += card.getValue();
            }
        }
    }

    public int getPoints() {
        return points;
    }
}
