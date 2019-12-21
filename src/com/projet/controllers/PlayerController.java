package com.projet.controllers;

import com.projet.models.App;
import com.projet.models.players.Player;
import com.projet.views.Interface;

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

    private JButton steal;

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
        cardA.setText(" ");
        cardB.setText(" ");
    }

    public void addStealButton(StealListener listener, int i) {
        steal = new JButton();
        steal.setText("Voler");

        steal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.onSteal(PlayerController.this.getPlayer(), i);
            }
        });

        panel.add(steal, "cell 0 2 2 1");

        Interface.getInstance().getFrame().revalidate();
        Interface.getInstance().getFrame().repaint();
    }

    public void removeStealButton() {
        panel.remove(steal);
        Interface.getInstance().getFrame().revalidate();
        Interface.getInstance().getFrame().repaint();
    }

    public void displayVisibleCard() {
        int visibleCardIndex = player.getVisibleCard() == player.getCard(0) ? 0 : 1;

        if (visibleCardIndex == 0) {
            cardA.setText(player.getVisibleCard().toString());
        } else {
            cardB.setText(player.getVisibleCard().toString());
        }

        cardAListener = addChooseCardListener(1);
        cardBListener = addChooseCardListener(2);

        cardA.addActionListener(cardAListener);
        cardB.addActionListener(cardBListener);
    }
}
