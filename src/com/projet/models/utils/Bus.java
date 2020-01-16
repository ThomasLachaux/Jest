package com.projet.models.utils;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Permet de communiquer entre les threads
 * Attrape les exceptions dans cette classe pour éviter d'avoir un code trop long dans les autres
 */
/**
 * Permet de communiquer entre les threads
 * Attrape les exceptions dans cette classe pour éviter d'avoir un code trop long dans les autres
 * @author Thomas de Lachaux
 * @author Yohann Valo
 * @version 1.0
 */
public class Bus extends LinkedBlockingQueue<String> {


    public Bus(int capacity) {
        super(capacity);
    }

    /**
     * Récupère le premier element de la pile
     * Si il n'y a aucun élément, se met en pause
     * @return element
     */
    @Override
    public String take() {
        try {
            return super.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Ajoute un element à la file
     * @param s element
     */
    @Override
    public void put(String s) {
        try {
            super.put(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajoute un element à la pile
     * @param i element
     */
    public void put(int i) {
        put(String.valueOf(i));
    }
}
