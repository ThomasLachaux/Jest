package com.projet.models;

import com.projet.views.Console;

public class Main {

    public static void main(String[] args) {

        Console console = Console.getInstance();

        Thread consoleThread = new Thread(console);
        consoleThread.start();

        Game.getInstance();
    }

}
