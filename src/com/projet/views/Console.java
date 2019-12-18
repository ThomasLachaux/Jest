package com.projet.views;

import com.projet.models.utils.EventType;
import com.projet.models.utils.Observer;

public class Console implements Observer, Runnable {

    private static Console instance;

    private Console() {}

    public static Console getInstance() {
        if(instance == null) {
            instance = new Console();
        }

        return instance;
    }

    @Override
    public void update(EventType eventType, Object payload) {

    }

    @Override
    public void run() {
        System.out.println("Sang de tes morts");
    }
}
