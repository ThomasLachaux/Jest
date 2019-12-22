package com.projet.controllers;

import com.projet.models.App;
import com.projet.models.players.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerController {

    private Player player;

    private JPanel panel;
    private JLabel label;

    private JButton cardA;
    private JButton cardB;

    private ActionListener cardAListener;
    private ActionListener cardBListener;

    public interface StealListener {
        void onSteal(Player player, int i);
    }

    public PlayerController(Player player, JPanel panel, JLabel label, JButton cardA, JButton cardB) {
        this.player = player;
        this.panel = panel;
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

    public ActionListener addChooseCardListener(int index) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.getInstance().getBus().put(index);
            }
        };
    }

    /**
     * Dès qu'un humain a choisi une carte, on cache ses cartes et on enlève le listener
     */
    public void onCardChoosen() {
        hideCards();
        notPlaying();

        // Enlève le listener des 2 cartes
        cardA.removeActionListener(cardAListener);
        cardB.removeActionListener(cardBListener);
    }

    public void chooseCard() {
        playing();
        showCards();

        cardAListener = addChooseCardListener(1);
        cardBListener = addChooseCardListener(2);

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
        cardA.setText("⛶");
        cardB.setText("⛶");
    }

    public void displayVisibleCard() {
        cardA.setText(player.getCard(0).toStringFromOutside());

        if(player.getCurrentCardSize() == 2) {
            cardB.setText(player.getCard(1).toStringFromOutside());
        }
        else {
            cardB.setEnabled(false);
        }
    }

    public void disableCards() {
        cardA.setEnabled(false);
        cardB.setEnabled(false);
    }

    public void enableCards() {
        cardA.setEnabled(true);
        cardB.setEnabled(true);
    }

    public void addStealListener(int i) {
        cardA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.getInstance().getBus().put(i + 1);
                App.getInstance().getBus().put(1);
                cardA.setText(" ");
                notPlaying();
            }
        });

        cardB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.getInstance().getBus().put(i +1);
                App.getInstance().getBus().put(2);
                cardB.setText(" ");
            }
        });
    }
}
