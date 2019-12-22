/*
 * Created by JFormDesigner on Sat Dec 21 10:19:06 CET 2019
 */

package com.projet.views;

import javax.swing.*;

import com.projet.controllers.PlayerController;
import com.projet.models.App;
import com.projet.models.Game;
import com.projet.models.players.Player;
import com.projet.models.utils.EventType;
import com.projet.models.utils.Observer;
import net.miginfocom.swing.*;

import java.util.ArrayList;

/**
 * @author unknown
 */
public class Board extends JPanel implements Observer {

    private ArrayList<PlayerController> playerControllers = new ArrayList<>();

    public Board(ArrayList<Player> players) {
        initComponents();

        playerControllers.add(new PlayerController(players.get(0), player1Panel, player1Label, p1c1, p1c2));
        playerControllers.add(new PlayerController(players.get(1), player2Panel, player2Label, p2c1, p2c2));
        playerControllers.add(new PlayerController(players.get(2), player3Panel, player3Label, p3c1, p3c2));

        if(players.size() == 4) {
            playerControllers.add(new PlayerController(players.get(3), player4Panel, player4Label, p4c1, p4c2));
        }

        else {
            remove(player4Panel);
        }
    }

    @Override
    public void update(EventType eventType, Object payload) {
        switch (eventType) {
            case CHOOSE_CARD:
                findController(Game.getInstance().getCurrentPlayer()).chooseCard();
                break;
            case CHOOSED_CARD:
                findController(Game.getInstance().getCurrentPlayer()).onCardChoosen();
                break;

            case STEAL_PLAYER:
                findController(Game.getInstance().getCurrentPlayer()).playing();
                for(PlayerController controller : playerControllers) {
                    controller.displayVisibleCard();
                    controller.disableCards();
                }

                ArrayList<Player> otherPlayers = (ArrayList<Player>) payload;

                // Si on doit se voler à soit même, s'auto active
                if(otherPlayers.size() == 0) {
                    findController(Game.getInstance().getCurrentPlayer()).enableCards();
                }

                for(int i = 0; i < otherPlayers.size(); i++) {
                    Player player = otherPlayers.get(i);
                    findController(player).enableCards();
                    findController(player).addStealListener(i);
                }
                break;

            case STOLE_CARD:
                findController(Game.getInstance().getCurrentPlayer()).notPlaying();

            /*case STEAL_PLAYER:
                findController(Game.getInstance().getCurrentPlayer()).playing();
                ArrayList<Player> otherPlayers = (ArrayList<Player>) payload;

                for(int i = 0; i < otherPlayers.size(); i++) {
                    findController(otherPlayers.get(i)).addStealButton(new PlayerController.StealListener() {
                        @Override
                        public void onSteal(Player player, int i) {
                            App.getInstance().getBus().put(i + 1);
                        }
                    }, i);
                }
                break;

            case STOLE_PLAYER:
                otherPlayers = (ArrayList<Player>) payload;

                for(Player player : otherPlayers) {
                    findController(player).removeStealButton();
                }
                break;

            case STEAL_CARD:
                Player stolenPlayer = (Player) payload;
                findController(stolenPlayer).displayVisibleCard();
                break;

            case STOLE_CARD:
                stolenPlayer = (Player) payload;
                findController(stolenPlayer).onCardChoosen();
                findController(Game.getInstance().getCurrentPlayer()).notPlaying();
                break;*/
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        player1Panel = new JPanel();
        p1c1 = new JButton();
        p1c2 = new JButton();
        player1Label = new JLabel();
        player2Panel = new JPanel();
        p2c1 = new JButton();
        p2c2 = new JButton();
        player2Label = new JLabel();
        player3Panel = new JPanel();
        p3c1 = new JButton();
        p3c2 = new JButton();
        player3Label = new JLabel();
        player4Panel = new JPanel();
        p4c1 = new JButton();
        p4c2 = new JButton();
        player4Label = new JLabel();

        //======== this ========
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new
        javax. swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e", javax
        . swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java
        .awt .Font ("Dialo\u0067" ,java .awt .Font .BOLD ,12 ), java. awt
        . Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans.
        PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("borde\u0072" .
        equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
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
                "[]"));

            //---- p1c1 ----
            p1c1.setText("Carte 1");
            p1c1.setFocusPainted(false);
            player1Panel.add(p1c1, "cell 0 0");

            //---- p1c2 ----
            p1c2.setText("Carte 2");
            p1c2.setFocusPainted(false);
            player1Panel.add(p1c2, "cell 1 0");

            //---- player1Label ----
            player1Label.setText("Joueur 1");
            player1Label.setHorizontalAlignment(SwingConstants.CENTER);
            player1Panel.add(player1Label, "cell 0 1 2 1");
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
                "[]"));

            //---- p2c1 ----
            p2c1.setText("Carte 1");
            p2c1.setFocusPainted(false);
            player2Panel.add(p2c1, "cell 0 0");

            //---- p2c2 ----
            p2c2.setText("Carte 2");
            p2c2.setFocusPainted(false);
            player2Panel.add(p2c2, "cell 1 0");

            //---- player2Label ----
            player2Label.setText("Joueur 2");
            player2Label.setHorizontalAlignment(SwingConstants.CENTER);
            player2Panel.add(player2Label, "cell 0 1 2 1");
        }
        add(player2Panel, "cell 1 0");

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
                "[]"));

            //---- p3c1 ----
            p3c1.setText("Carte 1");
            p3c1.setFocusPainted(false);
            player3Panel.add(p3c1, "cell 0 0");

            //---- p3c2 ----
            p3c2.setText("Carte 2");
            p3c2.setFocusPainted(false);
            player3Panel.add(p3c2, "cell 1 0");

            //---- player3Label ----
            player3Label.setText("Joueur 3");
            player3Label.setHorizontalAlignment(SwingConstants.CENTER);
            player3Panel.add(player3Label, "cell 0 1 2 1");
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
                "[]"));

            //---- p4c1 ----
            p4c1.setText("Carte 1");
            p4c1.setFocusPainted(false);
            player4Panel.add(p4c1, "cell 0 0");

            //---- p4c2 ----
            p4c2.setText("Carte 2");
            p4c2.setFocusPainted(false);
            player4Panel.add(p4c2, "cell 1 0");

            //---- player4Label ----
            player4Label.setText("Joueur 4");
            player4Label.setHorizontalAlignment(SwingConstants.CENTER);
            player4Panel.add(player4Label, "cell 0 1 2 1");
        }
        add(player4Panel, "cell 1 2");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel player1Panel;
    private JButton p1c1;
    private JButton p1c2;
    private JLabel player1Label;
    private JPanel player2Panel;
    private JButton p2c1;
    private JButton p2c2;
    private JLabel player2Label;
    private JPanel player3Panel;
    private JButton p3c1;
    private JButton p3c2;
    private JLabel player3Label;
    private JPanel player4Panel;
    private JButton p4c1;
    private JButton p4c2;
    private JLabel player4Label;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
