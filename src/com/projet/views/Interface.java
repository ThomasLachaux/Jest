package com.projet.views;

import com.projet.models.Game;
import com.projet.models.players.Player;
import com.projet.models.utils.EventType;
import com.projet.models.utils.Observable;
import com.projet.models.utils.Observer;

import javax.swing.*;
import java.util.ArrayList;

public class Interface extends Observable implements Observer {

    private JFrame frame;
    private static Interface instance;

    public static Interface getInstance() {
        if(instance == null) {
            instance = new Interface();
        }

        return instance;
    }

    /**
     * Create the application.
     */
    private Interface() {
        initialize();
        Game.getInstance().addObserver(this);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("Jest");
        frame.setResizable(false);
        frame.setBounds(100, 100, 720, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        switchPanel(new Menu());
    }

    public JFrame getFrame() {
        return frame;
    }

    public void switchPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        refresh();
    }

    @Override
    public void update(EventType eventType, Object payload) {
        notifyObservers(eventType, payload);
        switch (eventType) {
            case GAME_SET_UP:
                Board board = new Board((ArrayList<Player>) payload);
                addObserver(board);
                switchPanel(board);
                break;
        }
    }

    public void refresh() {
        getFrame().revalidate();
        getFrame().repaint();
    }
}
