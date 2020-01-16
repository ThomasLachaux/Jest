package com.projet.views;

import com.projet.models.Game;
import com.projet.models.players.Player;
import com.projet.models.utils.EventType;
import com.projet.models.utils.Observable;
import com.projet.models.utils.Observer;
import mdlaf.MaterialLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
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
        try {
            UIManager.setLookAndFeel (new MaterialLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace ();
        }
        frame = new JFrame("Jest");
        frame.setBounds(100, 100, 1000, 1000);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
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
        if (eventType == EventType.GAME_SET_UP) {
            Board board = new Board((ArrayList<Player>) payload);
            switchPanel(board);
        }
    }

    public void refresh() {
        getFrame().revalidate();
        getFrame().repaint();
    }

    public interface MouseListener {
        void onAction(MouseEvent e);
    }

    public static java.awt.event.MouseListener mouseListener(MouseListener listener) {
        return new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                listener.onAction(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
                Interface.getInstance().getFrame().setCursor(cursor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
                Interface.getInstance().getFrame().setCursor(cursor);
            }
        };
    }
}
