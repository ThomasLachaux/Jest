/*
 * Created by JFormDesigner on Sat Dec 21 10:19:06 CET 2019
 */

package com.projet.views;

import com.projet.controllers.PlayerController;
import com.projet.models.Card;
import com.projet.models.Game;
import com.projet.models.players.Player;
import com.projet.models.trophies.Trophy;
import com.projet.models.utils.Entry;
import com.projet.models.utils.EventType;
import com.projet.models.utils.Observer;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

import static com.projet.controllers.PlayerController.loadCard;

/**
 * la classe est un observer du modele
 */
public class Board extends JPanel implements Observer {
    /**
     * un constructeur qui permet d associer un joueur a un controleur
     */
    private ArrayList<PlayerController> playerControllers = new ArrayList<>();

    public Board(ArrayList<Player> players) {
        initComponents();
        Game.getInstance().addObserver(this);

        playerControllers.add(new PlayerController(players.get(0), player1Panel, player1Label, p1c1, p1c2, p1Score, p1Tropheys1,p1Tropheys2));
        playerControllers.add(new PlayerController(players.get(1), player2Panel, player2Label, p2c1, p2c2, p2Score, p2Tropheys1,p2Tropheys2));
        playerControllers.add(new PlayerController(players.get(2), player3Panel, player3Label, p3c1, p3c2, p3Score, p3Tropheys1,p3Tropheys2));

        Card cardA = Game.getInstance().getTrophyMapping().findCard(Game.getInstance().getTrophies().get(0));

        if (cardA.getColor().getOrder() != 1) {
            ImageIcon icon = new ImageIcon(loadCard(cardA.getValue() + cardA.getColor().toString()));
            trophy1.setIcon(icon);
        } else {
            ImageIcon icon = new ImageIcon(loadCard(cardA.getColor().toString()));
            trophy1.setIcon(icon);
        }

        if(players.size() == 4) {
            playerControllers.add(new PlayerController(players.get(3), player4Panel, player4Label, p4c1, p4c2, p4Score, p4Tropheys1,p4Tropheys2));
        }

        else {
            remove(player4Panel);
            Card cardB = Game.getInstance().getTrophyMapping().findCard(Game.getInstance().getTrophies().get(1));

            if (cardB.getColor().getOrder() != 1) {
                ImageIcon icon = new ImageIcon(loadCard(cardB.getValue() + cardA.getColor().toString()));
                trophy2.setIcon(icon);
            } else {
                ImageIcon icon = new ImageIcon(loadCard(cardB.getColor().toString()));
                trophy2.setIcon(icon);
            }
        }


    }

    /**
     * methode update du patron MVC
     * @param eventType type de l'évenement
     * @param payload paramètres
     */
    @Override
    public void update(EventType eventType, Object payload) {
        switch (eventType) {
            case TURN_START:
                turn.setText("Tour: " + payload);
                break;
            case CHOOSE_CARD:
                for(PlayerController controller : playerControllers) {
                    controller.disableCards();
                }
                findController(Game.getInstance().getCurrentPlayer())
                        .chooseCard()
                        .enableCards();
                break;
            case CHOOSED_CARD:
                findController(Game.getInstance().getCurrentPlayer()).onCardChoosen();
                for(PlayerController controller : playerControllers) {
                    controller.enableCards();
                }
                break;

            case STEAL_PLAYER:
                findController(Game.getInstance().getCurrentPlayer()).playing();
                for(PlayerController controller : playerControllers) {
                    controller
                            .displayVisibleCard()
                            .disableCards();
                }

                ArrayList<Player> otherPlayers = (ArrayList<Player>) payload;

                // Si on doit se voler à soit même, s'auto active
                if(otherPlayers.size() == 0) {
                    findController(Game.getInstance().getCurrentPlayer())
                            .enableCards()
                            .addStealListener(-1);
                }

                for(int i = 0; i < otherPlayers.size(); i++) {
                    Player player = otherPlayers.get(i);
                    findController(player)
                            .enableCards()
                            .addStealListener(i);
                }
                break;

            case STOLE_CARD:
                findController(Game.getInstance().getCurrentPlayer()).notPlaying();

                for(PlayerController controller : playerControllers) {
                    controller
                            .removeActionListeners()
                            .enableCards();
                }
                break;

            case TROPHEY_GIVEN:
                Entry<Integer, Player> mapping = (Entry<Integer, Player>) payload;
                int trophyNumber = mapping.getKey();
                Player winner = mapping.getValue();

                Trophy trophy = Game.getInstance().getTrophies().get(trophyNumber);
                findController(winner).addTrophy(trophy);

                if(mapping.getKey() == 0) {
                    trophy1.setIcon(null);
                }

                else {
                    trophy2.setIcon(null);
                }
                break;

            case SCORE_GIVEN:
                Player winnerPlayer = playerControllers.get(0).getPlayer();
                for(PlayerController controller : playerControllers) {
                    controller
                            .disableCards()
                            .displayScore();

                    int currentPlayerPoints = controller.getPlayer().getScore().getPoints();
                    int currentWinnerPoints = winnerPlayer.getScore().getPoints();

                    winnerPlayer = currentPlayerPoints > currentWinnerPoints ?  controller.getPlayer() : winnerPlayer;
                }



                trophyLabel.setText("Le vainqueur est : " + winnerPlayer);
                turn.setText(null);
                Interface.getInstance().refresh();
                break;
        }
    }

    /**
     * permet de trouver le controleur associé a un joueur
     * @param player
     * @return
     */
    private PlayerController findController(Object player) {
        for(PlayerController controller : playerControllers) {
            if(controller.getPlayer() == player) {
                return controller;
            }
        }

        return null;
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        player1Panel = new JPanel();
        p1c1 = new JLabel();
        p1c2 = new JLabel();
        player1Label = new JLabel();
        p1Score = new JLabel();
        p1Tropheys1 = new JLabel();
        p1Tropheys2 = new JLabel();
        player2Panel = new JPanel();
        p2c1 = new JLabel();
        p2c2 = new JLabel();
        player2Label = new JLabel();
        p2Score = new JLabel();
        p2Tropheys1 = new JLabel();
        p2Tropheys2 = new JLabel();
        infos = new JPanel();
        trophyLabel = new JLabel();
        trophy1 = new JLabel();
        trophy2 = new JLabel();
        turn = new JLabel();
        player3Panel = new JPanel();
        p3c1 = new JLabel();
        p3c2 = new JLabel();
        player3Label = new JLabel();
        p3Score = new JLabel();
        p3Tropheys1 = new JLabel();
        p3Tropheys2 = new JLabel();
        player4Panel = new JPanel();
        p4c1 = new JLabel();
        p4c2 = new JLabel();
        player4Label = new JLabel();
        p4Score = new JLabel();
        p4Tropheys1 = new JLabel();
        p4Tropheys2 = new JLabel();

        //======== this ========
        setLayout(new MigLayout(
            "fill,hidemode 3,align center center",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]"));

        //======== player1Panel ========
        {
            player1Panel.setLayout(new MigLayout(
                "fill,hidemode 3,align center center",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]"));
            player1Panel.add(p1c1, "cell 0 0,alignx right,growx 0");
            player1Panel.add(p1c2, "cell 1 0,alignx left,growx 0");

            //---- player1Label ----
            player1Label.setText("Joueur 1");
            player1Label.setHorizontalAlignment(SwingConstants.CENTER);
            player1Panel.add(player1Label, "cell 0 1 2 1");

            //---- p1Score ----
            p1Score.setHorizontalAlignment(SwingConstants.CENTER);
            player1Panel.add(p1Score, "cell 0 2 2 1");

            //---- p1Tropheys1 ----
            p1Tropheys1.setHorizontalAlignment(SwingConstants.CENTER);
            player1Panel.add(p1Tropheys1, "cell 0 3");
            player1Panel.add(p1Tropheys2, "cell 1 3");
        }
        add(player1Panel, "cell 0 1");

        //======== player2Panel ========
        {
            player2Panel.setLayout(new MigLayout(
                "fill,hidemode 3,align center center",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]"));
            player2Panel.add(p2c1, "cell 0 0,alignx right,growx 0");
            player2Panel.add(p2c2, "cell 1 0,alignx left,growx 0");

            //---- player2Label ----
            player2Label.setText("Joueur 2");
            player2Label.setHorizontalAlignment(SwingConstants.CENTER);
            player2Panel.add(player2Label, "cell 0 1 2 1");

            //---- p2Score ----
            p2Score.setHorizontalAlignment(SwingConstants.CENTER);
            player2Panel.add(p2Score, "cell 0 2 2 1");

            //---- p2Tropheys1 ----
            p2Tropheys1.setHorizontalAlignment(SwingConstants.CENTER);
            player2Panel.add(p2Tropheys1, "cell 0 3");
            player2Panel.add(p2Tropheys2, "cell 1 3");
        }
        add(player2Panel, "cell 1 0");

        //======== infos ========
        {
            infos.setLayout(new MigLayout(
                "fill,hidemode 3,align center center",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));

            //---- trophyLabel ----
            trophyLabel.setText("Troph\u00e9es");
            trophyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            infos.add(trophyLabel, "cell 0 0 2 1");

            //---- trophy1 ----
            trophy1.setHorizontalAlignment(SwingConstants.CENTER);
            infos.add(trophy1, "cell 0 1");

            //---- trophy2 ----
            trophy2.setHorizontalAlignment(SwingConstants.CENTER);
            infos.add(trophy2, "cell 1 1");

            //---- turn ----
            turn.setText("Tour");
            turn.setHorizontalAlignment(SwingConstants.CENTER);
            infos.add(turn, "cell 0 2 2 1");
        }
        add(infos, "cell 1 1");

        //======== player3Panel ========
        {
            player3Panel.setLayout(new MigLayout(
                "fill,hidemode 3,align center center",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]"));
            player3Panel.add(p3c1, "cell 0 0,alignx right,growx 0");
            player3Panel.add(p3c2, "cell 1 0,alignx left,growx 0");

            //---- player3Label ----
            player3Label.setText("Joueur 3");
            player3Label.setHorizontalAlignment(SwingConstants.CENTER);
            player3Panel.add(player3Label, "cell 0 1 2 1");

            //---- p3Score ----
            p3Score.setHorizontalAlignment(SwingConstants.CENTER);
            player3Panel.add(p3Score, "cell 0 2 2 1");

            //---- p3Tropheys1 ----
            p3Tropheys1.setHorizontalAlignment(SwingConstants.CENTER);
            player3Panel.add(p3Tropheys1, "cell 0 3");
            player3Panel.add(p3Tropheys2, "cell 1 3");
        }
        add(player3Panel, "cell 2 1");

        //======== player4Panel ========
        {
            player4Panel.setLayout(new MigLayout(
                "fill,hidemode 3,align center center",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]"));
            player4Panel.add(p4c1, "cell 0 0,alignx right,growx 0");
            player4Panel.add(p4c2, "cell 1 0,alignx left,growx 0");

            //---- player4Label ----
            player4Label.setText("Joueur 4");
            player4Label.setHorizontalAlignment(SwingConstants.CENTER);
            player4Panel.add(player4Label, "cell 0 1 2 1");

            //---- p4Score ----
            p4Score.setHorizontalAlignment(SwingConstants.CENTER);
            player4Panel.add(p4Score, "cell 0 2 2 1");

            //---- p4Tropheys1 ----
            p4Tropheys1.setHorizontalAlignment(SwingConstants.CENTER);
            player4Panel.add(p4Tropheys1, "cell 0 3");
            player4Panel.add(p4Tropheys2, "cell 1 3");
        }
        add(player4Panel, "cell 1 2");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel player1Panel;
    private JLabel p1c1;
    private JLabel p1c2;
    private JLabel player1Label;
    private JLabel p1Score;
    private JLabel p1Tropheys1;
    private JLabel p1Tropheys2;
    private JPanel player2Panel;
    private JLabel p2c1;
    private JLabel p2c2;
    private JLabel player2Label;
    private JLabel p2Score;
    private JLabel p2Tropheys1;
    private JLabel p2Tropheys2;
    private JPanel infos;
    private JLabel trophyLabel;
    private JLabel trophy1;
    private JLabel trophy2;
    private JLabel turn;
    private JPanel player3Panel;
    private JLabel p3c1;
    private JLabel p3c2;
    private JLabel player3Label;
    private JLabel p3Score;
    private JLabel p3Tropheys1;
    private JLabel p3Tropheys2;
    private JPanel player4Panel;
    private JLabel p4c1;
    private JLabel p4c2;
    private JLabel player4Label;
    private JLabel p4Score;
    private JLabel p4Tropheys1;
    private JLabel p4Tropheys2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
