package com.projet.models;

import com.projet.views.Console;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

    private static App instance;
    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(50);

    public static void main(String[] args) {
        instance = new App();
    }

    public static App getInstance() {
        return instance;
    }

    private App() {
        Game game = Game.getInstance(queue);
        Console console = Console.getInstance(queue);

        Thread gameThread = new Thread(game);
        gameThread.start();

        Thread consoleThread = new Thread(console);
        consoleThread.start();
    }

    public BlockingQueue<String> getQueue() {
        return queue;
    }
}
