package com.projet.models.utils;

import java.util.ArrayList;

public abstract class Observable {

    private ArrayList<Observer> observers;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(EventType eventType, Object payload) {
        for(Observer observer : observers) {
            observer.update(eventType, payload);
        }
    }
}
