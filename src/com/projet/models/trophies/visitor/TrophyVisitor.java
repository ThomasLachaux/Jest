package com.projet.models.trophies.visitor;

import com.projet.models.Card;
import com.projet.models.Color;
import com.projet.models.players.Player;
import com.projet.models.trophies.*;

import java.util.ArrayList;

/**
 * Classe représentant le concrete visitor des trophées
 */
public class TrophyVisitor implements Visitor {

    private ArrayList<Player> players;

    public TrophyVisitor(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Calcule le score de chaque joueur et determine un gagnant
     * @param players
     * @return
     */
    private Player calculateBestJest(ArrayList<Player> players) {
        Player winner = players.get(0);

        for (Player player : players) {
            if (player.getScore().getPoints() > winner.getScore().getPoints()) {
                winner = player;
            }
        }

        return winner;
    }

    /**
     * Implémente le pattern visitor
     * @param highest
     * @return
     */
    @Override
    public Player visit(Highest highest) {
        Player winner = players.get(0);
        int maxWinnerValue = 0;

        for (Player player : players) {

            // Pour chaque joueur, regarde la carte la plus elevée dans la couleur
            int maxPlayerValue = 0;
            for (Card card : player.getJest()) {
                if (card.getColor() == highest.getTrophyColor() && card.getValue() > maxPlayerValue) {
                    maxPlayerValue = card.getValue();
                }
            }

            // Si elle est plus elevée que le joueur, on change le winner
            if (maxPlayerValue > maxWinnerValue)
                winner = player;
        }

        return winner;
    }
    /**
     * Implémente le pattern visitor
     */
    @Override
    public Player visit(Lowest lowest) {
        Player winner = players.get(0);
        int maxWinnerValue = Integer.MAX_VALUE;

        for (Player player : players) {

            int maxPlayerValue = Integer.MAX_VALUE;
            for (Card card : player.getJest()) {
                if (card.getColor() == lowest.getTrophyColor() && card.getValue() < maxPlayerValue) {
                    maxPlayerValue = card.getValue();
                }
            }

            if (maxPlayerValue < maxWinnerValue)
                winner = player;
        }

        return winner;
    }
    /**
     * Implémente le pattern visitor
     */
    @Override
    public Player visit(Majority majority) {
        Player winner = players.get(0);
        int maxCount = 0;

        for (Player player : players) {

            int count = 0;
            for (Card card : player.getJest()) {
                if (card.getValue() == majority.getMajorityValue()) {
                    count++;
                }
            }

            if (count > maxCount)
                winner = player;
        }

        return winner;
    }
    /**
     * Implémente le pattern visitor
     */
    @Override
    public Player visit(Joker joker) {
        Player winner = null;

        for (Player player : players) {
            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Jocker) {
                    winner = player;
                    break;
                }
            }
        }

        return winner;
    }
    /**
     * Implémente le pattern visitor
     */
    @Override
    public Player visit(BestJest bestJest) {
        return calculateBestJest(players);
    }

    /**
     * Implémente le pattern visitor
     */
    @Override
    public Player visit(NoJoke noJoke) {
        ArrayList<Player> playersWithoutJocker = new ArrayList<>();

        for (Player player : players) {
            boolean hasJocker = false;

            for (Card card : player.getJest()) {
                if (card.getColor() == Color.Jocker) {
                    hasJocker = true;
                    break;
                }
            }

            if (!hasJocker) {
                playersWithoutJocker.add(player);
            }
        }

        return calculateBestJest(playersWithoutJocker);
    }
}
