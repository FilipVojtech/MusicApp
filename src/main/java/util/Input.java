package util;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
    private static final Scanner sc = new Scanner(System.in);

    public static String command() {
        System.out.print("> ");
        return sc.nextLine();
    }

    public static char[] password() {
        System.out.print("Password: ");
        return sc.nextLine().toCharArray();
    }

    /**
     * Prompts the user for string input.
     *
     * @param prompt          Prompt displayed to the user. Input happens on the same line.
     * @param inputOnSameLine True - Input happens on the same line. False - Input happens on the next line
     * @return Input by the user.
     */
    public static String string(String prompt, boolean inputOnSameLine) {
        System.out.print(prompt);
        if (!inputOnSameLine) {
            System.out.print('\n');
        }
        return sc.nextLine();
    }

    /**
     * Prompts the user for string input.
     *
     * @param prompt Prompt displayed to the user. Input happens on the same line.
     * @return Input by the user.
     */
    public static String string(String prompt) {
        return string(prompt, true);
    }

    public static String email() {
        Pattern pattern = Pattern.compile("[.\\S]+@[.\\S]+\\.[\\w]{2,3}");

        while (true) {
            System.out.print("Email: ");
            String email = sc.nextLine();
            Matcher matcher = pattern.matcher(email);

            if (matcher.find()) {
                return email;
            } else {
                System.out.println("Please enter an email address.");
            }
        }
    }
}
