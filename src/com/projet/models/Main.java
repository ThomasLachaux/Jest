package com.projet.models;

import com.projet.views.Console;

public class Main {

    public static void main(String[] args) {

        Game.getInstance();
        Console console = Console.getInstance();
        Thread consoleThread = new Thread(console);
        Game.getInstance().partie();
        consoleThread.start();

    }

}
