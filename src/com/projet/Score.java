package com.projet;

import java.util.ArrayList;

public class Score {
    private int points;
    private Player player;

    public Score(Player player) {
        points = 0;
        this.player = player;
    }

    public void calculateAll() {
        calculateBlack();
        calculateDiamond();
        calculateHeart();
    }

    public void calculateBlack() {
        ArrayList<Integer> pair = new ArrayList<Integer>();
        int occurence = 0;
        boolean asSpade = false;//booleen qui permet de savoir si on a seulement un seul as d'une couleur
        boolean asClub = false;
        int nbrSpade = 0;
        int nbrClub = 0;
        for (Card card : player.getJest()) {//boucle qui permet d avoir la condition un seul pique et cest l'as pour le transformer en 5
            if (card.getColor() == Color.Spade) {
                nbrSpade += 1;
                if (card.getValue() == 1) {
                    asSpade = true;
                }
            }
        }
        if (nbrSpade == 1 && asSpade == true) {// changement de la valeur de l'as si la condition est respectée
            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Spade && card.getValue() == 1) {
                    card.setValue(5);
                }
            }
        }

        for (Card card : player.getJest()) { //meme chose pour les trefles
            if (card.getColor() == Color.Club) {
                nbrClub += 1;
                if (card.getValue() == 1) {
                    asClub = true;
                }

            }
        }
        if (nbrClub == 1 && asClub == true) {
            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Club && card.getValue() == 1) {
                    card.setValue(5);
                }
            }
        }
        for (Card card : player.getJest()) {// calcul des pairs on rentre toutes les valeurs de couleur noir dans un tableau d entier
            if (card.getColor() == Color.Spade || card.getColor() == Color.Club) {
                pair.add(card.getValue());
                this.points += card.getValue();
            }
        }
        for (int j = 0; j < pair.size(); j += 1) { // si on retrouve une occurence dans le tableau des valeurs en excluant l'égalité des indices de parcours on incrément occurence
            for (int i = 0; i < pair.size(); i += 1) {
                if (pair.get(j) == pair.get(i) && i != j) {
                    occurence += 1;
                }
            }
        }
        this.points += occurence * 2; //les points sont incrémenté du nombre de pairs(occurences) fois 2 car une pair vaut 2 points
    }

    public void calculateDiamond() {
        boolean asDiamond = false;
        int nbrDiamond = 0;
        for (Card card : player.getJest()) {//similaire pour l'as
            if (card.getColor() == Color.Diamond) {
                nbrDiamond += 1;
                if (card.getValue() == 1) {
                    asDiamond = true;
                }

            }
        }
        if (nbrDiamond == 1 && asDiamond == true) {
            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Diamond && card.getValue() == 1) {
                    card.setValue(5);
                }
            }
        }
        for (Card card : player.getJest()) {
            if (card.getColor() == Color.Diamond) {

                this.points -= card.getValue();//le carreau fait perdre des points
            }
        }
    }

    public void calculateHeart() {
        boolean hasJoker = false;
        int nbrHeart = 0;
        boolean asHeart = false;
        for (Card card : player.getJest()) {//similaire pour l 'as
            if (card.getColor() == Color.Heart) {
                nbrHeart += 1;
                if (card.getValue() == 1) {
                    asHeart = true;
                }

            }
        }
        if (nbrHeart == 1 && asHeart == true) {
            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Heart && card.getValue() == 1) {
                    card.setValue(5);
                }
            }
        }
        for (Card card : player.getJest()) { //est ce que le joueur a la joker?
            if (card.getColor() == Color.Jocker) {
                hasJoker = true;
            }
        }
        if (hasJoker == true && nbrHeart == 0) {// le joker et aucun coeur = +4 points
            this.points += 4;
        } else if (hasJoker == true && nbrHeart < 4 && nbrHeart > 0) { // le joker et 1 a 3 coeur cest les points en negatif
            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Heart) {
                    this.points -= card.getValue();
                }
            }
        } else if (hasJoker == true && nbrHeart == 4) {// le joker et tous les coeurs cest les points en positif
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
