package console;

import session.Session;
import util.Input;

public class Console {
    public static void doInterface(boolean isDebugging) {
        TextInterface tInterface;

        while (true) {
            if (Session.IsLoggedIn()) {
                tInterface = new GeneralInterface();
            } else {
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
