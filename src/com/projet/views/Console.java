package com.projet.views;

import com.projet.models.App;
import com.projet.models.Game;
import com.projet.models.utils.EventType;
import com.projet.models.utils.Observer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
@SuppressWarnings("unused")
public class Console implements Observer, Runnable {

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    private static Console instance;
    private boolean canUseConsole = false;

    private Console() {
        Game.getInstance().addObserver(this);
    }

    public static Console getInstance() {
        if(instance == null) {
            instance = new Console();
        }

        return instance;
    }

    /**
     * Démarage de la partie en mode console
     */
    public static void startGame() {
        System.out.println(" __| |____________________________________________| |__");
        System.out.println("(__   ____________________________________________   __)");
        System.out.println("   | |                                            | |");
        System.out.println("   | |           ___ _____ _____ _____            | |");
        System.out.println("   | |          |_  |  ___/  ___|_   _|           | |");
        System.out.println("   | |            | | |__ \\ `--.  | |             | |");
        System.out.println("   | |            | |  __| `--. \\ | |             | |");
        System.out.println("   | |        /\\__/ / |___/\\__/ / | |             | |");
        System.out.println("   | |        \\____/\\____/\\____/  \\_/             | |");
        System.out.println("   | |                                            | |");
        System.out.println(" __| |____________________________________________| |__");
        System.out.println("(__   ____________________________________________   __)");
        System.out.println("   | |                                            | |");
    }

    /**
     * Méthode update qui indique à l'utilisateur ou configurer l'interface
     * @param eventType type de l'évenement
     * @param payload paramètres
     */
    @Override
    public void update(EventType eventType, Object payload) {
        switch(eventType) {
            case GAME_START:
                canUseConsole = false;
                startGame();
                System.out.println("Veillez utiliser l'interface graphique pour configurer le Jest");
                break;

            case GAME_SET_UP:
                canUseConsole = true;
        }
    }

    /**
     * la console est un thread donc on a une methode run
     */
    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            String input = readLine();

            if (input != null && canUseConsole) {
                App.getInstance().getBus().put(input);
            }
        }
    }

    private String readLine() {
        BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
        String resultat = null;
        try {
            resultat = br.readLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return resultat;
    }
}
