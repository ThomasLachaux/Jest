package com.projet.models.utils;

import com.projet.models.App;
import com.projet.views.Console;

public class Scanner {
    private static final String INVALID_ANSWER = "Entrée invalide ! Veuillez réessayer";

    // Disables instantiations
    private Scanner() {
    }

    // Le min et le max sont inclusifs
    public static int nextInt(int min, int max) {
        int result = -1;
        try {
            boolean invalidAnswer;

            do {
                result = Integer.parseInt(App.getInstance().getQueue().take());

                invalidAnswer = result < min || result > max;

                if (invalidAnswer) {
                    System.out.println(INVALID_ANSWER);
                }

            } while (invalidAnswer);

            System.out.println();
        } catch (NumberFormatException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Sous entends que le min est 1
    public static int nextInt(int max) {
        return nextInt(1, max);
    }

}
