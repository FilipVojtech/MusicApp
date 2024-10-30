package console;

import session.Session;

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
            case "0" -> logout();
            case "1" -> {
                TextInterface playlistController = new PlaylistController();
                playlistController.listOptions();
            }
            case "2" -> {
                TextInterface ratingController = new RatingController();
                ratingController.listOptions();
            }
            default -> {
                System.out.println("Invalid choice. Please try again.");
                setNextInterface(InterfaceType.General);
            }
        }
    }
}
