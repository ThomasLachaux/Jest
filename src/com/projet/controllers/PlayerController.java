package com.projet.controllers;

import com.projet.models.App;
import com.projet.models.Card;
import com.projet.models.Color;
import com.projet.models.Game;
import com.projet.models.players.Player;
import com.projet.models.trophies.Trophy;
import com.projet.views.Interface;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Contrôleur du joueur. Fait le lien entre l'interface et le moteur
 *
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public class PlayerController {

    private Player player;

    private JPanel panel;
    private JLabel label;

    private JLabel cardA;
    private JLabel cardB;

    private MouseListener cardAListener;
    private MouseListener cardBListener;

    private JLabel score;
    private JLabel trophyA;
    private JLabel trophyB;

    /**
     * Attribute le bon label au joueur et cache ses cartes
     *
     * @param player   joueur
     * @param panel    panneau
     * @param label    label
     * @param cardA    première carte
     * @param cardB    deuxième carte
     * @param score    label des scores
     * @param trophyA label du trophé A
     * @param trophyB label du trophé B
     */
    public PlayerController(Player player, JPanel panel, JLabel label, JLabel cardA, JLabel cardB, JLabel score, JLabel trophyA, JLabel trophyB) {
        this.player = player;
        this.panel = panel;
        this.label = label;
        this.cardA = cardA;
        this.cardB = cardB;
        this.score = score;
        this.trophyA = trophyA;
        this.trophyB = trophyB;

        label.setText(player.getName());
        hideCards();
    }

    /**
     * Renvoie le chemin relatif où sont stoké les images
     * @param imageName
     * @return chemin relatif
     */
    public static String loadCard(String imageName) {
        return "src/com/projet/views/images/" + imageName + ".png";
    }

    /**
     * Retourne le chemin relatif vers une image blanche
     * @return chemin relatif
     */
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
     *
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
     * Quand un utilisateur a choisi l'une de ses cartes, envoie le numéro dans le bus
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

    /**
     * Appelé quand c'est au moment du joueur de choisir une carte
     * Ajoute des listeners aux cartes
     * @return
     */
    public PlayerController chooseCard() {
        playing();
        showCards();

        cardAListener = getChooseCardListener(1);
        cardBListener = getChooseCardListener(2);

        cardA.addMouseListener(cardAListener);
        cardB.addMouseListener(cardBListener);

        return this;
    }

    /**
     * Modifie la couleur du label au moment du tour de jeu du joueur
     * @return
     */
    public PlayerController playing() {
        label.setForeground(java.awt.Color.RED);

        return this;
    }

    /**
     * Reset la couleur du label du joueur
     * @return
     */
    public PlayerController notPlaying() {
        label.setForeground(java.awt.Color.BLACK);

        return this;
    }

    /**
     * Cache les cartes du joueur
     * @return
     */
    public PlayerController hideCards() {
        ImageIcon icon1 = new ImageIcon(loadHiddenCard());
        cardA.setIcon(icon1);
        ImageIcon icon2 = new ImageIcon(loadHiddenCard());
        cardB.setIcon(icon2);

        return this;
    }

    /**
     * Affiche de façon visible seulement la carte choisi
     * @return
     */
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

    /**
     * Désactive les cartes du joueur
     * @return
     */
    public PlayerController disableCards() {
        cardA.setEnabled(false);
        cardB.setEnabled(false);

        return this;
    }

    /**
     * Active les cartes du joueur
     * @return
     */
    public PlayerController enableCards() {
        cardA.setEnabled(true);
        cardB.setEnabled(true);

        return this;
    }

    /**
     * Renvoie un listener contenant la réference du joueur si le joueur à voler est le meme que le joueur en cours on envoie juste la carte
     * @param playerIndex index du joueur de 0 a n
     * @param cardChoice va de 1 a 2 c'est la carte qu'on vole
     * @return listener
     */
    private MouseListener getStealListener(int playerIndex, int cardChoice) {
        return Interface.mouseListener(new Interface.MouseListener() {
            @Override
            public void onAction(MouseEvent e) {
                // Si le joueur courant est le même que le controller, on envoit pas quel joueur choisir
                if (player != Game.getInstance().getCurrentPlayer()) {
                    App.getInstance().getBus().put(playerIndex + 1);
                }
                App.getInstance().getBus().put(cardChoice);
                notPlaying();
            }
        });
    }

    /**
     * Enleve les listeners sur les cartes d'un joueur
     * @return
     */
    public PlayerController removeActionListeners() {
        for (MouseListener listener : cardA.getMouseListeners()) {
            cardA.removeMouseListener(listener);
        }

        for (MouseListener listener : cardB.getMouseListeners()) {
            cardB.removeMouseListener(listener);
        }

        return this;
    }

    /**
     * Ajoute des listener sur les cartes du joueur
     * @param playerIndex
     * @return
     */
    public PlayerController addStealListener(int playerIndex) {
        cardAListener = getStealListener(playerIndex, 1);
        cardBListener = getStealListener(playerIndex, 2);

        cardA.addMouseListener(cardAListener);
        cardB.addMouseListener(cardBListener);

        return this;
    }

    /**
     * Affiche les scores
     * @return
     */
    public PlayerController displayScore() {
        int points = player.getScore().getPoints();
        score.setText("Score: " + points);
        return this;
    }

    /**
     * Affiche les trophées en dessous du joueur 
     * @param trophy
     * @return
     */
    public PlayerController addTrophy(Trophy trophy) {
        Card card = Game.getInstance().getTrophyMapping().findCard(trophy);
        JLabel trophyLabel = trophyA.getIcon() == null ? trophyA : trophyB;

        if (card.getColor().getOrder() != 1) {
            ImageIcon icon2 = new ImageIcon(loadCard(card.getValue() + card.getColor().toString()));
            trophyLabel.setIcon(icon2);
        } else {
            ImageIcon icon2 = new ImageIcon(loadCard(card.getColor().toString()));
            trophyLabel.setIcon(icon2);
        }

        return this;
    }
}
