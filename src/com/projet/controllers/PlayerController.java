package com.projet.controllers;

import com.projet.models.App;
import com.projet.models.Game;
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

    public ActionListener getChooseCardListener(int index) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.getInstance().getBus().put(index);
                removeActionListeners();
            }
        };
    }

    /**
     * Dès qu'un humain a choisi une carte, on cache ses cartes et on enlève le listener
     */
    public void onCardChoosen() {
        hideCards();
        notPlaying();

        removeActionListeners();
    }

    public void chooseCard() {
        playing();
        showCards();

        cardAListener = getChooseCardListener(1);
        cardBListener = getChooseCardListener(2);

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

    public ActionListener getStealListener(int playerIndex, int cardChoice) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                button.setText(" ");

                // Si le joueur courant est le même que le controller, on envoit pas quel joueur choisir
                if(player != Game.getInstance().getCurrentPlayer()) {
                    App.getInstance().getBus().put(playerIndex + 1);
                }
                App.getInstance().getBus().put(cardChoice);
                notPlaying();
            }
        };
    }

    public void removeActionListeners() {
        for(ActionListener listener : cardA.getActionListeners()) {
            cardA.removeActionListener(listener);
        }

        for(ActionListener listener : cardB.getActionListeners()) {
            cardB.removeActionListener(listener);
        }

    }

    public void addStealListener(int playerIndex) {
        cardAListener = getStealListener(playerIndex, 1);
        cardBListener = getStealListener(playerIndex, 2);

        cardA.addActionListener(cardAListener);
        cardB.addActionListener(cardBListener);
    }
}
