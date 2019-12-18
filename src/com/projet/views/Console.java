package com.projet.views;

import com.projet.models.Game;
import com.projet.models.utils.EventType;
import com.projet.models.utils.Observer;

public class Console implements Observer, Runnable {

    private static Console instance;

    private Console() {
        Game.getInstance().addObserver(this);
    }

    public static Console getInstance() {
        if(instance == null) {
            instance = new Console();
        }

        return instance;
    }

    @Override
    public void update(EventType eventType, Object payload) {
        switch(eventType) {
            case START_GAME:
                com.projet.models.utils.Console.startGame();
                break;
        }
    }

    @Override
    public void run() {
        System.out.println("Sang de tes morts");
    }
}
