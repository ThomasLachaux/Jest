package com.projet.trophies.visitor;

import com.projet.Card;
import com.projet.Color;
import com.projet.Player;
import com.projet.trophies.*;

import java.util.ArrayList;

public class TrophyVisitor implements Visitor {

    private ArrayList<Player> players;

    public TrophyVisitor(ArrayList<Player> players) {
        this.players = players;
    }

    private Player calculateBestJest(ArrayList<Player> players) {
        Player winner = players.get(0);

        for(Player player : players) {
            if(player.getScore().getPoints() > winner.getScore().getPoints()) {
                winner = player;
            }
        }

        return winner;
    }

    @Override
    public Player visit(Highest highest) {
        Player winner = players.get(0);
        int maxWinnerValue = 0;

        for(Player player : players) {

            // Pour chaque joueur, regarde la carte la plus elevée dans la couleur
            int maxPlayerValue = 0;
            for(Card card : player.getJest()) {
                if (card.getColor() == highest.getTrophyColor() && card.getValue() > maxPlayerValue ) {
                    maxPlayerValue = card.getValue();
                }
            }

            // Si elle est plus elevée que le joueur, on change le winner
            if(maxPlayerValue > maxWinnerValue)
                winner = player;
        }

        return winner;
    }

    @Override
    public Player visit(Lowest lowest) {
        Player winner = players.get(0);
        int maxWinnerValue = Integer.MAX_VALUE;

        for(Player player : players) {

            int maxPlayerValue = Integer.MAX_VALUE;
            for(Card card : player.getJest()) {
                if (card.getColor() == lowest.getTrophyColor() && card.getValue() < maxPlayerValue ) {
                    maxPlayerValue = card.getValue();
                }
            }

            if(maxPlayerValue < maxWinnerValue)
                winner = player;
        }

        return winner;
    }

    @Override
    public Player visit(Majority majority) {
        Player winner = players.get(0);
        int maxCount = 0;

        for(Player player : players) {

            int count = 0;
            for(Card card : player.getJest()) {
                if (card.getValue() == majority.getMajorityValue()) {
                    count++;
                }
            }

            if(count > maxCount)
                winner = player;
        }

        return winner;
    }

    @Override
    public Player visit(Joker joker) {
        Player winner = null;

        for(Player player : players) {
           for(Card card : player.getJest()) {
               if (card.getColor() == Color.Jocker) {
                   winner = player;
                   break;
               }
           }
        }

        return winner;
    }

    @Override
    public Player visit(BestJest bestJest) {
        return calculateBestJest(players);
    }

    @Override
    public Player visit(NoJoke noJoke) {
        ArrayList<Player> playersWithoutJocker = new ArrayList<>();

        for(Player player : players) {
            boolean hasJocker = false;

            for(Card card : player.getJest()) {
                if (card.getColor() == Color.Jocker) {
                    hasJocker = true;
                    break;
                }
            }

            if(!hasJocker) {
                playersWithoutJocker.add(player);
            }
        }

        return calculateBestJest(playersWithoutJocker);
    }
}
