/*
 * Created by JFormDesigner on Fri Dec 20 23:07:15 CET 2019
 */

package com.projet.views;

import com.projet.models.App;
import com.projet.models.utils.Bus;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author unknown
 */
public class Menu extends JPanel {

    private boolean easyDifficulty = true;

    /*
    0: pas d'extension
    1: remise en jeu des cartes dans le stack
    2: jouer carte face caché
     */
    private int extension = 0;

    public Menu() {
        initComponents();
    }

    private void playActionPerformed(ActionEvent event) {
        Bus bus = App.getInstance().getBus();

        bus.put(String.valueOf(playerInput.getModel().getValue()));
        bus.put(String.valueOf(botInput.getModel().getValue()));
        bus.put(easyDifficulty ? 1 : 2);

        // Voulez vous jouer à une extension
        bus.put(extension == 0 ? 2 : 1);

        if(extension != 0) {
            bus.put(extension);
        }
    }

    private void playerStateChanged(ChangeEvent e) {
        int players = (int) playerInput.getModel().getValue();
        System.out.println(players);

        SpinnerModel botModel = new SpinnerNumberModel(0, 0, players, 1);
        botInput.setModel(botModel);
    }

    private void difficultyActionPerformed(ActionEvent e) {
        easyDifficulty = !easyDifficulty;

        difficulty.setText(easyDifficulty ? "Facile" : "Difficile");
    }

    private void extensionActionPerformed(ActionEvent e) {
        extension++;
        extension %= 3;
        String text = null;
        switch (extension) {
            case 0:
                text = "Pas d'extension";
                break;

            case 1:
                text = "Remise en jeu";
                break;

            case 2:
                text = "Face caché";
                break;
        }

        extensionButton.setText(text);
    }

    private void rulesActionPerformed(ActionEvent e) {
        try {
            URI uri = new URI("https://bit.ly/2Ss0pOP");
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        title = new JLabel();
        players = new JLabel();
        playerInput = new JSpinner();
        bots = new JLabel();
        botInput = new JSpinner();
        difficultyLabel = new JLabel();
        difficulty = new JButton();
        extensionLabel = new JLabel();
        extensionButton = new JButton();
        play = new JButton();
        rules = new JButton();

        //======== this ========
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border
        . EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing. border. TitledBorder. CENTER, javax
        . swing. border. TitledBorder. BOTTOM, new java .awt .Font ("D\u0069alog" ,java .awt .Font .BOLD ,
        12 ), java. awt. Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans
        . PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062order" .equals (e .
        getPropertyName () )) throw new RuntimeException( ); }} );
        setLayout(new MigLayout(
            "fill,hidemode 3,align center center",
            // columns
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- title ----
        title.setText("Jest");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        add(title, "cell 0 0 2 1");

        //---- players ----
        players.setText("Nombre de joueurs");
        players.setHorizontalAlignment(SwingConstants.CENTER);
        add(players, "cell 0 1");

        //---- playerInput ----
        playerInput.setModel(new SpinnerNumberModel(3, 3, 4, 1));
        playerInput.addChangeListener(e -> playerStateChanged(e));
        add(playerInput, "cell 1 1");

        //---- bots ----
        bots.setText("Nombre de bots");
        bots.setHorizontalAlignment(SwingConstants.CENTER);
        add(bots, "cell 0 2");

        //---- botInput ----
        botInput.setModel(new SpinnerNumberModel(0, 0, 4, 1));
        add(botInput, "cell 1 2");

        //---- difficultyLabel ----
        difficultyLabel.setText("Difficult\u00e9");
        difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(difficultyLabel, "cell 0 3");

        //---- difficulty ----
        difficulty.setText("Facile");
        difficulty.addActionListener(e -> difficultyActionPerformed(e));
        add(difficulty, "cell 1 3");

        //---- extensionLabel ----
        extensionLabel.setText("Extension");
        extensionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(extensionLabel, "cell 0 4");

        //---- extensionButton ----
        extensionButton.setText("Pas d'extension");
        extensionButton.addActionListener(e -> extensionActionPerformed(e));
        add(extensionButton, "cell 1 4");

        //---- play ----
        play.setText("Jouer");
        play.addActionListener(e -> playActionPerformed(e));
        add(play, "cell 0 5 2 1");

        //---- rules ----
        rules.setText("R\u00e8gles");
        rules.addActionListener(e -> rulesActionPerformed(e));
        add(rules, "cell 0 6 2 1");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel title;
    private JLabel players;
    private JSpinner playerInput;
    private JLabel bots;
    private JSpinner botInput;
    private JLabel difficultyLabel;
    private JButton difficulty;
    private JLabel extensionLabel;
    private JButton extensionButton;
    private JButton play;
    private JButton rules;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
