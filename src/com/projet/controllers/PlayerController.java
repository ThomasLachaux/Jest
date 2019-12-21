package com.projet.controllers;

import com.projet.models.players.Player;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class PlayerController {

    private Player player;
    private JLabel label;
    private JButton cardA;
    private JButton cardB;

    public PlayerController(Player player, JLabel label, JButton cardA, JButton cardB) {
        this.player = player;
        this.label = label;
        this.cardA = cardA;
        this.cardB = cardB;

        label.setText(player.getName());
        hideCards();
    }

    public Player getPlayer() {
        return player;
    }

    public void showCards() {
        cardA.setText(player.getCard(0).toString());
        cardB.setText(player.getCard(1).toString());
    }

    public void chooseCard() {
        showCards();
    }

    public void hideCards() {
        cardA.setText(null);
        cardB.setText(null);
    }
}
