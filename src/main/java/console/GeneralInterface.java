package console;

import session.Session;

public class GeneralInterface extends TextInterface {
    @Override
    public void listOptions() {
        System.out.println("Logged in as " + Session.getUser().getDisplayName());
        System.out.println("0. Logout");
    }

    @Override
    public void handleCommand(String choice) {
        switch (choice) {
            case "0" -> logout();
            default -> {
                System.out.println("Invalid choice. Please try again.");
                setNextInterface(InterfaceType.General);
            }
        }
    }
}
