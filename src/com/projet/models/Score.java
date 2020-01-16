package com.projet.models;

import com.projet.models.players.Player;

import java.util.ArrayList;

/**
 * Classe des scores
 */
public class Score {
    private int points;
    private Player player;

    public Score(Player player) {
        points = 0;
        this.player = player;
    }

    /**
     * Permet de calculer les scores totaux
     */
    public void calculateAll() {
        calculateBlack();
        calculateDiamond();
        calculateHeart();
    }

    /**
     * Permet de calculer le score pique et trefle
     */
    public void calculateBlack() {
        ArrayList<Integer> pair = new ArrayList<>();
        int occurence = 0;
        // Permet de savoir si on a seulement un seul as d'une couleur
        boolean asSpade = false;
        boolean asClub = false;
        int spaceCount = 0;
        int clubCount = 0;

        // Boucle qui permet d'avoir la condition un seul pique qui détermine la valeur de l'as (1 ou 5)
        for (Card card : player.getJest()) {
            if (card.getColor() == Color.Spade) {
                spaceCount += 1;
                if (card.getValue() == 1) {
                    asSpade = true;
                }
            }
        }

        // Changement de la valeur de l'as si la condition est respectée
        if (spaceCount == 1 && asSpade) {
            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Spade && card.getValue() == 1) {
                    card.setValue(5);
                }
            }
        }

        // Même chose pour les trefles
        for (Card card : player.getJest()) {
            if (card.getColor() == Color.Club) {
                clubCount += 1;
                if (card.getValue() == 1) {
                    asClub = true;
                }

            }
        }

        if (clubCount == 1 && asClub) {
            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Club && card.getValue() == 1) {
                    card.setValue(5);
                }
            }
        }

        // Calcul des pairs on rentre toutes les valeurs de couleur noir dans un tableau d'entier
        for (Card card : player.getJest()) {
            if (card.getColor() == Color.Spade || card.getColor() == Color.Club) {
                pair.add(card.getValue());
                this.points += card.getValue();
            }
        }

        // Si on retrouve une occurence dans le tableau des valeurs en excluant l'égalité des indices de parcours, on incrément occurence
        for (int j = 0; j < pair.size(); j += 1) {
            for (int i = 0; i < pair.size(); i += 1) {
                if (pair.get(j).equals(pair.get(i)) && i != j) {
                    occurence += 1;
                }
            }
        }

        // Les points sont incrémenté du nombre de pairs (occurences) fois 2 car une pair vaut 2 points
        this.points += occurence * 2;
    }
    /**
     * Permet de calculer le score des carreaux
     */
    public void calculateDiamond() {
        boolean asDiamond = false;
        int diamondCount = 0;
        for (Card card : player.getJest()) {//similaire pour l'as
            if (card.getColor() == Color.Diamond) {
                diamondCount += 1;
                if (card.getValue() == 1) {
                    asDiamond = true;
                }

            }
        }
        if (diamondCount == 1 && asDiamond) {
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
    /**
     * Permet de calculer le score des coeurs
     */
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
        if (nbrHeart == 1 && asHeart) {
            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Heart && card.getValue() == 1) {
                    card.setValue(5);
                }
            }
        }
        for (Card card : player.getJest()) { //est ce que le joueur a la joker?
            if (card.getColor() == Color.Jocker) {
                hasJoker = true;
                break;
            }
        }
        if (hasJoker && nbrHeart == 0) {// le joker et aucun coeur = +4 points
            this.points += 4;
        } else if (hasJoker && nbrHeart < 4 && nbrHeart > 0) { // le joker et 1 a 3 coeur cest les points en negatif
            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Heart) {
                    this.points -= card.getValue();
                }
            }
        } else if (hasJoker && nbrHeart == 4) {// le joker et tous les coeurs cest les points en positif
            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Heart) {
                    this.points += card.getValue();
                }
            }
        }

    }
    /**
     * getter des points d un joueur
     */
    public int getPoints() {
        return points;
    }
}
