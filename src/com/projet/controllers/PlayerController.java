package com.projet.controllers;

import com.projet.models.App;
import com.projet.models.Card;
import com.projet.models.Color;
import com.projet.models.Game;
import com.projet.models.players.Player;
import com.projet.views.Interface;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerController {

    private Player player;

    private JPanel panel;
    private JLabel label;

    private JLabel cardA;
    private JLabel cardB;

    private MouseListener cardAListener;
    private MouseListener cardBListener;

    private JLabel score;
    private JLabel tropheys;

    /**
     * Attribute le bon label au joueur et cache ses cartes
     *
     * @param player   joueur
     * @param panel    panneau
     * @param label    label
     * @param cardA    première carte
     * @param cardB    deuxième carte
     * @param score    label des scores
     * @param tropheys label des trophés
     */
    public PlayerController(Player player, JPanel panel, JLabel label, JLabel cardA, JLabel cardB, JLabel score, JLabel tropheys) {
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

    public static String loadCard(String imageName) {
        return "src/com/projet/views/images/" + imageName + ".png";
    }

    public static String loadHiddenCard() {
        return loadCard("blank");
    }

    /**
     * Renvoie le joueur
     *
     * @return joueur
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Affiche les 2 cartes du joueur
     * @return joueur
     */
    public PlayerController showCards() {

        Card cardAModel = player.getCard(0);
        Card cardBModel = player.getCard(1);

        if (cardAModel.getColor() != Color.Jocker) {
            ImageIcon icon1 = new ImageIcon(loadCard(cardAModel.getValue() + cardAModel.getColor().toString()));
            cardA.setIcon(icon1);
        } else {
            ImageIcon icon1 = new ImageIcon(loadCard(cardAModel.getColor().toString()));
            cardA.setIcon(icon1);
        }

        if (cardBModel.getColor() != Color.Jocker) {
            ImageIcon icon2 = new ImageIcon(loadCard(cardBModel.getValue() + player.getCard(1).getColor().toString()));
            cardB.setIcon(icon2);
        } else {
            ImageIcon icon2 = new ImageIcon(loadCard(cardBModel.getColor().toString()));
            cardB.setIcon(icon2);
        }

        return this;
    }

    /**
     *
     * @param index
     * @return
     */
    private MouseListener getChooseCardListener(int index) {
        return Interface.mouseListener(new Interface.MouseListener() {
            @Override
            public void onAction(MouseEvent e) {
                App.getInstance().getBus().put(index);
                removeActionListeners();
            }
        });
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

        cardA.addMouseListener(cardAListener);
        cardB.addMouseListener(cardBListener);

        return this;
    }

    public PlayerController playing() {
        label.setForeground(java.awt.Color.RED);

        return this;
    }

    public PlayerController notPlaying() {
        label.setForeground(java.awt.Color.BLACK);

        return this;
    }

    public PlayerController hideCards() {
        ImageIcon icon1 = new ImageIcon(loadHiddenCard());
        cardA.setIcon(icon1);
        ImageIcon icon2 = new ImageIcon(loadHiddenCard());
        cardB.setIcon(icon2);

        return this;
    }

    public PlayerController displayVisibleCard() {

        Card cardAModel = player.getCard(0);

        if (cardAModel.isFaceUp()) {
            if (player.getCard(0).getColor() != Color.Jocker) {
                ImageIcon icon1 = new ImageIcon(loadCard(cardAModel.getValue() + cardAModel.getColor().toString()));
                cardA.setIcon(icon1);
            } else {
                ImageIcon icon1 = new ImageIcon(loadCard(cardAModel.getColor().toString()));
                cardA.setIcon(icon1);
            }
        } else {
            ImageIcon icon1 = new ImageIcon(loadHiddenCard());
            cardA.setIcon(icon1);
        }

        if (player.getCurrentCardSize() == 2) {

            Card cardBModel = player.getCard(1);

            if (cardBModel.isFaceUp()) {
                if (cardBModel.getColor().getOrder() != 1) {
                    ImageIcon icon2 = new ImageIcon(loadCard(cardBModel.getValue() + player.getCard(1).getColor().toString()));
                    cardB.setIcon(icon2);
                } else {
                    ImageIcon icon2 = new ImageIcon(loadCard(cardBModel.getColor().toString()));
                    cardB.setIcon(icon2);
                }
            } else {
                ImageIcon icon2 = new ImageIcon(loadHiddenCard());
                cardB.setIcon(icon2);
            }
        } else {
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

    private MouseListener getStealListener(int playerIndex, int cardChoice) {
        return Interface.mouseListener(new Interface.MouseListener() {
            @Override
            public void onAction(MouseEvent e) {
                JLabel button = (JLabel) e.getSource();
                button.setText(" ");

                // Si le joueur courant est le même que le controller, on envoit pas quel joueur choisir
                if (player != Game.getInstance().getCurrentPlayer()) {
                    App.getInstance().getBus().put(playerIndex + 1);
                }
                App.getInstance().getBus().put(cardChoice);
                notPlaying();
            }
        });
    }

    public PlayerController removeActionListeners() {
        for (MouseListener listener : cardA.getMouseListeners()) {
            cardA.removeMouseListener(listener);
        }

        for (MouseListener listener : cardB.getMouseListeners()) {
            cardB.removeMouseListener(listener);
        }

        return this;
    }

    public PlayerController addStealListener(int playerIndex) {
        cardAListener = getStealListener(playerIndex, 1);
        cardBListener = getStealListener(playerIndex, 2);

        cardA.addMouseListener(cardAListener);
        cardB.addMouseListener(cardBListener);

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
