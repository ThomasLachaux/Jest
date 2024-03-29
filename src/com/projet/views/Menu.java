package com.projet.views;

import com.projet.models.App;
import com.projet.models.utils.Bus;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import mdlaf.themes.MaterialOceanicTheme;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public class Menu extends JPanel {

    private boolean easyDifficulty = true;

    /*
    0: pas d'extension
    1: remise en jeu des cartes dans le stack
    2: jouer carte face caché
     */
    private int extension = 0;

    private boolean darkTheme = false;

    public Menu() {
        initComponents();
    }

    /**
     * Lorsque le joueur lance la partie les paramètres sont envoyés dasn le bus
     * @param event
     */
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

    /**
     * Met à jour le nombre de bot possible maximum en fonction du nombre de jooueurs choisi
     * @param e
     */
    private void playerStateChanged(ChangeEvent e) {
        int players = (int) playerInput.getModel().getValue();
        System.out.println(players);

        SpinnerModel botModel = new SpinnerNumberModel(0, 0, players, 1);
        botInput.setModel(botModel);
    }

    /**
     * change le text label de facile a difficile lors d'un clique
     * @param e
     */
    private void difficultyActionPerformed(ActionEvent e) {
        easyDifficulty = !easyDifficulty;

        difficulty.setText(easyDifficulty ? "Facile" : "Difficile");
    }

    /**
     * change le text label lors d'un clique
     * @param e
     */
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

    /**
     * Renvoie l'utilisateur sur les règles
     * @param e
     */
    private void rulesActionPerformed(ActionEvent e) {
        try {
            URI uri = new URI("https://bit.ly/2Ss0pOP");
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * change le label text lors d'un clique et change le thème
     * @param e
     */
    private void themeButtonActionPerformed(ActionEvent e) {
        darkTheme = !darkTheme;

        if(darkTheme) {
            themeButton.setText("Sombre");
            MaterialLookAndFeel.changeTheme(new MaterialOceanicTheme());
        }

        else {
            themeButton.setText("Clair");
            MaterialLookAndFeel.changeTheme(new MaterialLiteTheme());
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
        themeLabel = new JLabel();
        themeButton = new JButton();
        play = new JButton();
        rules = new JButton();

        //======== this ========
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
        botInput.setModel(new SpinnerNumberModel(0, 0, 3, 1));
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

        //---- themeLabel ----
        themeLabel.setText("Th\u00e8me du jeu");
        themeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(themeLabel, "cell 0 5");

        //---- themeButton ----
        themeButton.setText("Clair");
        themeButton.addActionListener(e -> themeButtonActionPerformed(e));
        add(themeButton, "cell 1 5");

        //---- play ----
        play.setText("Jouer");
        play.addActionListener(e -> playActionPerformed(e));
        add(play, "cell 0 6 2 1");

        //---- rules ----
        rules.setText("R\u00e8gles");
        rules.addActionListener(e -> rulesActionPerformed(e));
        add(rules, "cell 0 7 2 1");
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
    private JLabel themeLabel;
    private JButton themeButton;
    private JButton play;
    private JButton rules;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
