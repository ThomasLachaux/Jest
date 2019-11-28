package com.projet.utils;

public class Random {

    /**
     * Retourne un nombre aléatoire dans l'interval donné. (Min et Max inclus)
     * @param min nombre minimum
     * @param max nombre maximum
     * @return nombre aléatoire
     */
    public static int ranInt(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

}
