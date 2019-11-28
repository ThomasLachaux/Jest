package com.projet.strategies;

import com.projet.Card;
import com.projet.players.Player;

public abstract class BotStrategy implements Strategy {

    protected String name;

    public BotStrategy(String name) {
        this.name = name;
    }

    public void hasChoseCard(Card card) {
        System.out.println(name + " a choisi la carte " + card.toString());
    }

    public void hasChoseStolenPlayer(Player player) {
        System.out.println(name + " a choisi de voler " + player.toString());
    }
}
