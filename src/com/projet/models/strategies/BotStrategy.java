package com.projet.models.strategies;

import com.projet.models.Card;
import com.projet.models.players.Player;

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
