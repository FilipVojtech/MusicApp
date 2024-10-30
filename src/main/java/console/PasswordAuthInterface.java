package console;

import business.CreditCard;
import business.InvalidCardNumberException;
import business.UnsupportedCardIssuerException;
import business.User;
import org.mindrot.jbcrypt.BCrypt;
import persistence.RecordNotFound;
import persistence.UserDao;
import persistence.UserDaoImpl;
import session.Session;
import util.Input;

import java.io.Console;


public class PasswordAuthInterface extends TextInterface {
    public PasswordAuthInterface() {
        super();
    }

    public PasswordAuthInterface(boolean isDebugging) {
        super(isDebugging);
    }

    @Override
    public void listOptions() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");
    }

    @Override
    public void handleCommand(String choice) {
        switch (choice) {
            case "1" -> {
                User user = doAuth();
                if (user != null) Session.setUser(user);
            }
            case "2" -> {
                User user = register();
                if (user != null) Session.setUser(user);
            }
            case "0" -> exitProgram();
            default -> {
                System.out.println("Invalid choice. Please try again.");
                setNextInterface(InterfaceType.PasswordAuth);
            }
        }
    }

    private User doAuth() {
        String email = Input.email();
        char[] passwordArray = getPassword();

        if (passwordArray == null) {
            System.out.println("Something went wrong while logging in.");
            return null;
        }

        try {
            UserDao userDao = new UserDaoImpl();
            User user = userDao.getUserByEmail(email);
            if (BCrypt.checkpw(new String(passwordArray), user.getPassword())) {
                return user;
            } else {
                System.out.println("Couldn't log in.");
                return null;
            }
        } catch (RecordNotFound e) {
            System.out.println("Couldn't log in.");
            return null;
        }
    }

    private User register() {
        // Part 1 card
        getCreditCard();
        System.out.println("Credit card valid.");
        System.out.println("Please continue by creating an account.");

        // Part 2 user
        User user;
        {
            String email = getUniqueEmail();
            char[] password = getPassword();
            String displayName = Input.string("Display name: ");

            if (password == null) {
                System.out.println("Something went wrong while registering.");
                return null;
            }

            String hashedPw = BCrypt.hashpw(new String(password), BCrypt.gensalt(14));
            user = User.builder()
                    .email(email)
                    .password(hashedPw)
                    .displayName(displayName)
                    .build();
        }
        System.gc();

        UserDao userDao = new UserDaoImpl();

        if (userDao.createUser(user)) {
            System.out.println("Your account has been created successfully.");
            return user;
        } else {
            System.out.println("Couldn't finish registration. Please try again.");
            return null;
        }
    }

    private char[] getPassword() {
        Console console = System.console();

        if (!super.isDebugging && console == null) {
            System.out.println("Couldn't get Console instance");
            System.out.println("You have to run the program in the system console.");
            System.out.println("Copy the long string on first line from the IntelliJ Run window and paste it in Windows/Linux/MacOS terminal");
            return null;
        }

        char[] passwordArray;

        if (!super.isDebugging) {
            passwordArray = console.readPassword("Password: ");
        } else {
            passwordArray = Input.password();
        }

        return passwordArray;
    }

    private String getUniqueEmail() {
        while (true) {
            String email = Input.email();

            try {
                UserDao userDao = new UserDaoImpl();
                userDao.getUserByEmail(email);
                System.out.println("Email already taken.");
                continue;
            } catch (RecordNotFound ignore) {
                return email;
            }
        }
    }

    private CreditCard getCreditCard() {
        while (true) {
            CreditCard card;
            final var number = Input.integer("Card number: ");
            final var expirationDate = Input.cardExpirationDate("Expiration date: ");
            final var cvv = Input.cvv("CVV: ");
            final var name = Input.string("Name on card: ");

            try {
                card = new CreditCard(number, expirationDate, cvv, name);
            } catch (InvalidCardNumberException e) {
                System.out.println("Invalid card number.");
                continue;
            } catch (UnsupportedCardIssuerException e) {
                System.out.println("Unsupported card issuer. Please use one of these cards:");
                System.out.println("  - Visa");
                System.out.println("  - Master Card");
                System.out.println("  - American Express");
                continue;
            }
            return card;
        }
    }
}
