package com.projet.views;

import com.projet.models.Game;
import com.projet.models.utils.EventType;
import com.projet.models.utils.Observer;

import javax.swing.*;

public class Interface implements Observer {

    private JFrame frame;

    /**
     * Create the application.
     */
    public Interface() {
        initialize();
        Game.getInstance().addObserver(this);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("Jest");
        frame.setResizable(false);
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Menu menu = new Menu();
        frame.getContentPane().removeAll();
        frame.getContentPane().add(menu);
    }

    public JFrame getFrame() {
        return frame;
    }

    @Override
    public void update(EventType eventType, Object payload) {

    }
}
