package console;

import session.Session;
import util.Input;

public class Console {
    public static void doInterface(boolean isDebugging) {
        TextInterface tInterface = new GeneralInterface();

        while (true) {
            // Choose interface to display
            tInterface = switch (tInterface.getNextInterface()) {
                case PasswordAuth -> new PasswordAuthInterface();
                default -> new GeneralInterface();
            };

            // If not logged in, force login interface
            if (!Session.IsLoggedIn()) {
                tInterface = new PasswordAuthInterface(isDebugging);
            }

            String input;

            tInterface.listOptions();
            input = Input.command();
            tInterface.handleCommand(input);
        }
    }

    public static void doInterface() {
        doInterface(false);
    }
}
