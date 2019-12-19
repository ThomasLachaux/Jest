package com.projet.views;

import com.projet.controllers.ButtonController;
import com.projet.models.App;
import com.projet.models.Game;
import com.projet.models.utils.EventType;
import com.projet.models.utils.Observer;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

public class Interface implements Observer {

    private JFrame frame;

    private JLabel title;

    private JButton button0;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;

    /**
     * Create the application.
     */
    public Interface() {
        initialize();
        Game.getInstance().addObserver(this);

        new ButtonController(button0, 0);
        new ButtonController(button1, 1);
        new ButtonController(button2, 2);
        new ButtonController(button3, 3);
        new ButtonController(button4, 4);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("Jest");
        frame.setResizable(false);
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        title = new JLabel("Combien de joueurs ?");
        title.setBounds(125, 61, 160, 14);
        frame.getContentPane().add(title);

        button0 = new JButton("0");
        button0.setBounds(77, 87, 89, 23);
        frame.getContentPane().add(button0);

        button1 = new JButton("1");
        button1.setBounds(196, 86, 89, 23);
        frame.getContentPane().add(button1);

        button2 = new JButton("2");
        button2.setBounds(77, 121, 89, 23);
        frame.getContentPane().add(button2);

        button3 = new JButton("3");
        button3.setBounds(196, 120, 89, 23);
        frame.getContentPane().add(button3);

        button4 = new JButton("4");
        button4.setBounds(135, 161, 89, 23);
        frame.getContentPane().add(button4);
    }

    public JFrame getFrame() {
        return frame;
    }

    @Override
    public void update(EventType eventType, Object payload) {
        switch (eventType) {
            case CHOOSE_PLAYER_COUNT:
            case CHOOSE_BOT_COUNT:
                title.setText((String) payload);
        }
    }
}
