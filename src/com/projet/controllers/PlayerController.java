package com.projet.controllers;

import com.projet.models.App;
import com.projet.models.players.Player;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerController {

    private Player player;
    private JLabel label;

    private JButton cardA;
    private JButton cardB;

    private ActionListener cardAListener;
    private ActionListener cardBListener;

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

    public ActionListener chooseCardListener(int index) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.getInstance().getBus().put(String.valueOf(index));
            }
        };
    }

    /**
     * Dès qu'un humain a choisi une carte, on cache ses cartes et on enlève le listener
     */
    public void onHumanCardChosen() {
        hideCards();
        notPlaying();

        // Enlève le listener des 2 cartes
        cardA.removeActionListener(cardAListener);
        cardB.removeActionListener(cardBListener);
    }

    public void chooseCard() {
        playing();
        showCards();

        cardAListener = chooseCardListener(1);
        cardBListener = chooseCardListener(2);

        cardA.addActionListener(cardAListener);
        cardB.addActionListener(cardBListener);
    }

    public void playing() {
        label.setForeground(Color.RED);
    }

    public void notPlaying() {
        label.setForeground(Color.BLACK);
    }

    public void hideCards() {
        cardA.setText(null);
        cardB.setText(null);
    }
}
