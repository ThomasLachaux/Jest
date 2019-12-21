package com.projet.models;

import com.projet.models.utils.Bus;
import com.projet.views.Console;
import com.projet.views.Interface;

import java.awt.*;

public class App {

    private static App instance;
    private Bus bus = new Bus(50);

    public static void main(String[] args) {
        new App();
    }

    public static App getInstance() {
        return instance;
    }

    private App() {
        instance = this;
        Game game = Game.getInstance();
        Console console = Console.getInstance();

        Thread gameThread = new Thread(game);
        gameThread.start();

        Thread consoleThread = new Thread(console);
        consoleThread.start();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Interface window = new Interface();
                    window.getFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Bus getBus() {
        return bus;
    }
}
