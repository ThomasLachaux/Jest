package com.projet.controllers;

import com.projet.models.App;
import com.projet.models.Game;
import com.projet.models.players.Player;

import javax.swing.*;
import java.awt.*;
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

    public Player getPlayer() {
        return player;
    }

    public PlayerController showCards() {

        

        ImageIcon icon1 = new ImageIcon(player.getCard(0).getValue()+"" + ""+ player.getCard(0).getColor() + ".png");
        Image img = icon1.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon icon2 = new ImageIcon(player.getCard(1).getValue()+"" + ""+ player.getCard(1).getColor() + ".png");
        cardB.setIcon(icon2);

        return this;
    }

    private MouseListener getChooseCardListener(int index) {
        return new MouseListener() {
            @Override
            public void mouseExited(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseReleased(MouseEvent e){}
            public void mousePressed(MouseEvent e){}
            public void mouseClicked(MouseEvent e) {
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

        cardAListener =  getChooseCardListener(1);
        cardBListener =  getChooseCardListener(2);

        cardA.addMouseListener(cardAListener);
        cardB.addMouseListener(cardBListener);

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

    private MouseListener getStealListener(int playerIndex, int cardChoice) {
        return new MouseListener() {
            @Override
            public void mouseExited(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseReleased(MouseEvent e){}
            public void mousePressed(MouseEvent e){}
            public void mouseClicked(MouseEvent e) {
                JLabel button = (JLabel) e.getSource();
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
        for(MouseListener listener : cardA.getMouseListeners()) {
            cardA.removeMouseListener(listener);
        }

        for(MouseListener listener : cardB.getMouseListeners()) {
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
