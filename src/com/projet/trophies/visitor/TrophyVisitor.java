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

    @Override
    public Player visit(Highest highest) {
        Player winner = players.get(0);
        int maxCount = 0;

        for(Player player : players) {

            int count = 0;
            for(Card card : player.getJest()) {
                if (card.getColor() == highest.getTrophyColor()) {
                    count++;
                }
            }

            if(count > maxCount)
                winner = player;
        }

        return winner;
    }

    @Override
    public Player visit(Lowest lowest) {
        Player winner = players.get(0);
        int maxCount = Integer.MAX_VALUE;

        for(Player player : players) {

            int count = 0;
            for(Card card : player.getJest()) {
                if (card.getColor() == lowest.getTrophyColor()) {
                    count++;
                }
            }

            if(count < maxCount)
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
                if (card.getValue() == majority.getTrophyColor()) {
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
        Player winner = players.get(0);

        return winner;
    }

    @Override
    public Player visit(NoJoke noJoke) {
        Player winner = players.get(0);

        return winner;
    }
}
