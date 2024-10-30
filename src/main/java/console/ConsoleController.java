package console;

import session.Session;
import util.Input;

/**
 * @author Filip Vojtěch
 * @author Dylan Habis
 * @author Alex Clinton
 */
public class ConsoleController {
    public static void doInterface(boolean isDebugging) {
        TextInterface tInterface = new GeneralInterface();

        //noinspection InfiniteLoopStatement
        while (true) {
            // Choose interface to display
            tInterface = switch (tInterface.getNextInterface()) {
                case General ->
                    //noinspection DuplicateBranchesInSwitch
                        new GeneralInterface();
                case PasswordAuth -> new PasswordAuthInterface();
                case PlaylistController -> new PlaylistController();
                case RatingController -> new RatingController();
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
