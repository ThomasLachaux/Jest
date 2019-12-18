package com.projet.models.utils;

public class Scanner {

    private static final String INVALID_ANSWER = "Entrée invalide ! Veuillez réessayer";
    public static java.util.Scanner scanner = new java.util.Scanner(System.in);

    // Disables instantiations
    private Scanner() {
    }

    // Le min et le max sont inclusifs
    public static int nextInt(int min, int max) {
        System.out.print(Console.BLUE + "-> " + Console.RESET);
        int result;
        boolean invalidAnswer;

        do {
            result = scanner.nextInt();

            invalidAnswer = result < min || result > max;

            if (invalidAnswer) {
                System.out.println(INVALID_ANSWER);
            }

        } while (invalidAnswer);

        System.out.println();
        return result;
    }

    // Sous entends que le min est 1
    public static int nextInt(int max) {
        return nextInt(1, max);
    }

}
