package com.projet.models;

import com.projet.views.Console;
import com.projet.views.Interface;

import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

    private static App instance;
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(50);

    public static void main(String[] args) {
        new App();
    }

    public static App getInstance() {
        return instance;
    }

    private App() {
        instance = this;
        Game game = Game.getInstance(queue);
        Console console = Console.getInstance(queue);

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

    public BlockingQueue<String> getQueue() {
        return queue;
    }
}
