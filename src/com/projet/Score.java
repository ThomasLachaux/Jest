package com.projet;

public class Score {
    private int points;
    private Player player;
    public Score(Player player) {
        points = 0;
        this.player = player;
    }
    public void calculateAll(){
        calculateSpade();
        calculateClub();
        calculateDiamond();
        calculateHeart();
    }
    public void calculateSpade() {
        for (Card card : player.getJest()) {
            if (card.getColor() == Color.Spade) {

                this.points += card.getValue();
            }
        }
    }
    public void calculateClub() {
        for (Card card : player.getJest()) {
            if (card.getColor() == Color.Club) {

                this.points += card.getValue();
            }
        }
    }
    public void calculateDiamond() {
        for (Card card : player.getJest()) {
            if (card.getColor() == Color.Diamond) {

                this.points -= card.getValue();
            }
        }
    }
    public void calculateHeart() {
        boolean hasJoker = false;
        int nbrHeart = 0;
        for (Card card : player.getJest()) {
            if (card.getColor() == Color.Jocker) {
                hasJoker = true;
            }
            if (card.getColor() == Color.Heart) {
                nbrHeart += 1;
            }

        }
        if(hasJoker== true && nbrHeart == 0){
            this.points += 4;
        }
        else if(hasJoker == true && nbrHeart <4 && nbrHeart >0) {
            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Heart) {
                    this.points -= card.getValue();
                }
            }
        }
        else if(hasJoker == true && nbrHeart ==4) {
            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Heart) {
                    this.points += card.getValue();
                }
            }
        }

    }

    public int getPoints() {
        return points;
    }
}
