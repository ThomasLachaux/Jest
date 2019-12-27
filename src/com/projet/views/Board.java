/*
 * Created by JFormDesigner on Sat Dec 21 10:19:06 CET 2019
 */

package com.projet.views;

import com.projet.controllers.PlayerController;
import com.projet.models.Game;
import com.projet.models.players.Player;
import com.projet.models.trophies.Trophy;
import com.projet.models.utils.Entry;
import com.projet.models.utils.EventType;
import com.projet.models.utils.Observer;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

public class Board extends JPanel implements Observer {

    private ArrayList<PlayerController> playerControllers = new ArrayList<>();

    public Board(ArrayList<Player> players) {
        initComponents();
        Game.getInstance().addObserver(this);

        playerControllers.add(new PlayerController(players.get(0), player1Panel, player1Label, p1c1, p1c2, p1Score, p1Tropheys));
        playerControllers.add(new PlayerController(players.get(1), player2Panel, player2Label, p2c1, p2c2, p2Score, p1Tropheys));
        playerControllers.add(new PlayerController(players.get(2), player3Panel, player3Label, p3c1, p3c2, p3Score, p1Tropheys));

        Trophy trophyA = Game.getInstance().getTrophies().get(0);
        trophy1.setText(Game.getInstance().getTropheyMapping().findCard(trophyA).toString() + " " + trophyA.toString());

        if(players.size() == 4) {
            playerControllers.add(new PlayerController(players.get(3), player4Panel, player4Label, p4c1, p4c2, p4Score, p1Tropheys));
            trophy2.setText(null);
        }

        else {
            remove(player4Panel);
            Trophy trophyB = Game.getInstance().getTrophies().get(1);
            trophy2.setText(Game.getInstance().getTropheyMapping().findCard(trophyB).toString() + " " + trophyB.toString());
        }


    }

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
                int tropheyNumber = mapping.getKey();
                Player winner = mapping.getValue();

                Trophy trophy = Game.getInstance().getTrophies().get(tropheyNumber);

                if(tropheyNumber == 0) {
                    findController(winner).addTrophey(trophy.toString());
                    trophy1.setText(null);
                } else if(tropheyNumber == 1) {
                    findController(winner).addTrophey(trophy.toString());
                    trophy2.setText(null);
                }
                break;

            case SCORE_GIVEN:
                for(PlayerController controller : playerControllers) {
                    controller
                            .disableCards()
                            .displayScore();
                }
                Interface.getInstance().refresh();
                break;
        }
    }

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
        p1Tropheys = new JLabel();
        player2Panel = new JPanel();
        p2c1 = new JLabel();
        p2c2 = new JLabel();
        player2Label = new JLabel();
        p2Score = new JLabel();
        p2Tropheys = new JLabel();
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
        p3Tropheys = new JLabel();
        player4Panel = new JPanel();
        p4c1 = new JLabel();
        p4c2 = new JLabel();
        player4Label = new JLabel();
        p4Score = new JLabel();
        p4Tropheys = new JLabel();

        //======== this ========
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
        swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border
        . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog"
        ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) , getBorder
        ( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
        .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException
        ( ); }} );
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
            player1Panel.add(p1c1, "cell 0 0");
            player1Panel.add(p1c2, "cell 1 0");

            //---- player1Label ----
            player1Label.setText("Joueur 1");
            player1Label.setHorizontalAlignment(SwingConstants.CENTER);
            player1Panel.add(player1Label, "cell 0 1 2 1");

            //---- p1Score ----
            p1Score.setHorizontalAlignment(SwingConstants.CENTER);
            player1Panel.add(p1Score, "cell 0 2 2 1");

            //---- p1Tropheys ----
            p1Tropheys.setHorizontalAlignment(SwingConstants.CENTER);
            player1Panel.add(p1Tropheys, "cell 0 3 2 1");
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
            player2Panel.add(p2c1, "cell 0 0");
            player2Panel.add(p2c2, "cell 1 0");

            //---- player2Label ----
            player2Label.setText("Joueur 2");
            player2Label.setHorizontalAlignment(SwingConstants.CENTER);
            player2Panel.add(player2Label, "cell 0 1 2 1");

            //---- p2Score ----
            p2Score.setHorizontalAlignment(SwingConstants.CENTER);
            player2Panel.add(p2Score, "cell 0 2 2 1");

            //---- p2Tropheys ----
            p2Tropheys.setHorizontalAlignment(SwingConstants.CENTER);
            player2Panel.add(p2Tropheys, "cell 0 3 2 1");
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
            trophy1.setText("Troph\u00e9e 1");
            trophy1.setHorizontalAlignment(SwingConstants.CENTER);
            infos.add(trophy1, "cell 0 1");

            //---- trophy2 ----
            trophy2.setText("Troph\u00e9e 2");
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
            player3Panel.add(p3c1, "cell 0 0");
            player3Panel.add(p3c2, "cell 1 0");

            //---- player3Label ----
            player3Label.setText("Joueur 3");
            player3Label.setHorizontalAlignment(SwingConstants.CENTER);
            player3Panel.add(player3Label, "cell 0 1 2 1");

            //---- p3Score ----
            p3Score.setHorizontalAlignment(SwingConstants.CENTER);
            player3Panel.add(p3Score, "cell 0 2 2 1");

            //---- p3Tropheys ----
            p3Tropheys.setHorizontalAlignment(SwingConstants.CENTER);
            player3Panel.add(p3Tropheys, "cell 0 3 2 1");
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
            player4Panel.add(p4c1, "cell 0 0");
            player4Panel.add(p4c2, "cell 1 0");

            //---- player4Label ----
            player4Label.setText("Joueur 4");
            player4Label.setHorizontalAlignment(SwingConstants.CENTER);
            player4Panel.add(player4Label, "cell 0 1 2 1");

            //---- p4Score ----
            p4Score.setHorizontalAlignment(SwingConstants.CENTER);
            player4Panel.add(p4Score, "cell 0 2 2 1");

            //---- p4Tropheys ----
            p4Tropheys.setHorizontalAlignment(SwingConstants.CENTER);
            player4Panel.add(p4Tropheys, "cell 0 3 2 1");
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
    private JLabel p1Tropheys;
    private JPanel player2Panel;
    private JLabel p2c1;
    private JLabel p2c2;
    private JLabel player2Label;
    private JLabel p2Score;
    private JLabel p2Tropheys;
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
    private JLabel p3Tropheys;
    private JPanel player4Panel;
    private JLabel p4c1;
    private JLabel p4c2;
    private JLabel player4Label;
    private JLabel p4Score;
    private JLabel p4Tropheys;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
