package com.projet.models.utils;

import com.projet.models.App;

/**
 * Permet les entrée de l'application
 * Peut aussi faire de la validation pour les nombres
 */
public class Scanner {
    private static final String INVALID_ANSWER = "Entrée invalide ! Veuillez réessayer";

    // Disables instantiations
    private Scanner() {
    }

    /**
     * Attend dans le bus l'arrivée d'un entier que l'on parse
     * @param min minimum inclusif
     * @param max maximum inclusif
     * @return première entrée du bus validée
     */
    public static int nextInt(int min, int max) {
        int result = -1;
        try {
            boolean invalidAnswer;

            do {
                result = Integer.parseInt(App.getInstance().getBus().take());

                invalidAnswer = result < min || result > max;

                if (invalidAnswer) {
                    System.out.println(INVALID_ANSWER);
                }

            } while (invalidAnswer);

            System.out.println();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Attend dans le bus l'arrivée d'un entier que l'on parse
     * Sous entend que le minimum est 1
     * @param max maximum inclusif
     * @return première entrée du bus validée
     */
    public static int nextInt(int max) {
        return nextInt(1, max);
    }

}
