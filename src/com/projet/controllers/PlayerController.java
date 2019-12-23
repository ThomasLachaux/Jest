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

    private JLabel score;
    private JLabel tropheys;

    public PlayerController(Player player, JPanel panel, JLabel label, JButton cardA, JButton cardB, JLabel score, JLabel tropheys) {
        this.player = player;
        this.panel = panel;
        this.label = label;
        this.cardA = cardA;
        this.cardB = cardB;
        this.score = score;
        this.tropheys = tropheys;

        label.setText(player.getName());
        hideCards();
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerController showCards() {
        cardA.setText(player.getCard(0).toString());
        cardB.setText(player.getCard(1).toString());

        return this;
    }

    private ActionListener getChooseCardListener(int index) {
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
    public PlayerController onCardChoosen() {
        hideCards();
        notPlaying();

        removeActionListeners();

        return this;
    }

    public PlayerController chooseCard() {
        playing();
        showCards();

        cardAListener = getChooseCardListener(1);
        cardBListener = getChooseCardListener(2);

        cardA.addActionListener(cardAListener);
        cardB.addActionListener(cardBListener);

        return this;
    }

    public PlayerController playing() {
        label.setForeground(Color.RED);

        return this;
    }

    public PlayerController notPlaying() {
        label.setForeground(Color.BLACK);

        return this;
    }

    public PlayerController hideCards() {
        cardA.setText("⛶");
        cardB.setText("⛶");

        return this;
    }

    public PlayerController displayVisibleCard() {
        cardA.setText(player.getCard(0).toStringFromOutside());

        if(player.getCurrentCardSize() == 2) {
            cardB.setText(player.getCard(1).toStringFromOutside());
        }
        else {
            cardB.setEnabled(false);
        }

        return this;
    }

    public PlayerController disableCards() {
        cardA.setEnabled(false);
        cardB.setEnabled(false);

        return this;
    }

    public PlayerController enableCards() {
        cardA.setEnabled(true);
        cardB.setEnabled(true);

        return this;
    }

    private ActionListener getStealListener(int playerIndex, int cardChoice) {
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

    public PlayerController removeActionListeners() {
        for(ActionListener listener : cardA.getActionListeners()) {
            cardA.removeActionListener(listener);
        }

        for(ActionListener listener : cardB.getActionListeners()) {
            cardB.removeActionListener(listener);
        }

        return this;
    }

    public PlayerController addStealListener(int playerIndex) {
        cardAListener = getStealListener(playerIndex, 1);
        cardBListener = getStealListener(playerIndex, 2);

        cardA.addActionListener(cardAListener);
        cardB.addActionListener(cardBListener);

        return this;
    }

    public PlayerController displayScore() {
        int points = player.getScore().getPoints();
        score.setText("Score: " + points);
        return this;
    }

    public PlayerController addTrophey(String trophey) {
        tropheys.setText(tropheys.getText() + "     " + trophey);

        return this;
    }
}
