package com.projet.models.utils;

import java.util.ArrayList;

/**
 * Désigne l'observable dans le design pattern Observer
 */
public abstract class Observable {

    private final ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Ajoute un observateur
     * @param observer observateur
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Notifie tous les observateur d'un évenement.
     * @param eventType type de l'évenement
     * @param payload paramètre éventuel
     */
    public void notifyObservers(EventType eventType, Object payload) {
        // Ne pas remplacer un foreach, car cause un ConcurrentModificationException
        // noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = observers.get(i);
            observer.update(eventType, payload);
        }
    }

    /**
     * Notifie tous les observateur d'un évenement.
     * @param eventType type de l'évenement
     */
    public void notifyObservers(EventType eventType) {
        notifyObservers(eventType, null);
    }
}
