package console;

import session.Session;

/**
 * @author Filip Vojtěch
 * @author Dylan Habis
 * @author Alex Clinton
 */
public class GeneralInterface extends TextInterface {
    @Override
    public void listOptions() {
        System.out.println("Logged in as " + Session.getUser().getDisplayName());
        System.out.println("1. Playlist");
        System.out.println("2. Rating");
        System.out.println("0. Logout");

    }

    @Override
    public void handleCommand(String choice) {
        switch (choice) {
            case "1" -> setNextInterface(InterfaceType.PlaylistController);
            case "2" -> setNextInterface(InterfaceType.RatingController);
            case "0" -> logout();
            default -> {
                System.out.println("Invalid choice. Please try again.");
                setNextInterface(InterfaceType.General);
            }
        }
    }
}
