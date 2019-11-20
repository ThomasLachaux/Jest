package com.projet;

public class Scanner {

    private static java.util.Scanner scanner = new java.util.Scanner(System.in);

    // Disables instantiations
    private Scanner() {}

    // Le min et le max sont inclusifs
    public static int nextInt(int min, int max) {
        int result;
        boolean invalidAnswer;

        System.out.println("DEBUG: min " + min + " max: " + max);

        do {
            result = scanner.nextInt();

            invalidAnswer = result < min || result > max;

            if(invalidAnswer) {
                System.out.println("Entrée invalide ! Veuillez réessayer");
            }

        } while (invalidAnswer);

        return result;
    }

    // Sous entends que le min est 1
    public static int nextInt(int max) {
        return nextInt(1, max);
    }

}
