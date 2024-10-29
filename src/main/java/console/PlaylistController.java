package console;

import business.Playlist;
import business.Song;
import persistence.PlaylistDao;
import persistence.PlaylistDaoImpl;
import session.Session;
import util.Input;

import java.util.List;

public class PlaylistController extends TextInterface {
    private PlaylistDao playlistDao;

    public PlaylistController() {
        super();
    }

    @Override
    public void listOptions() {
        System.out.println("Logged in as " + Session.getUser().getDisplayName());
        System.out.println("0. Exit");
        System.out.println("1. Create Playlist");
        System.out.println("2. Add Song to Playlist");
        System.out.println("3. Remove Song from Playlist");
        System.out.println("4. Get Playlist by ID");
        System.out.println("5. Get User Playlists");
        System.out.println("6. Get Public Playlists");
        System.out.println("7. Get Songs in Playlist");
    }

    @Override
    public void handleCommand(String choice) {
        switch (choice) {
            case "1":
                createPlaylist();
                break;
            case "2":
                addSongToPlaylist();
                break;
            case "3":
                removeSongFromPlaylist();
                break;
            case "4":
                getPlaylistById();
                break;
            case "5":
                getUserPlaylists();
                break;
            case "6":
                getPublicPlaylists();
                break;
            case "7":
                getSongsInPlaylist();
                break;
            case "0":
                exitProgram();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void createPlaylist() {
        System.out.print("Enter playlist name: ");
        String name = Input.command();
        System.out.print("Is the playlist public? (true/false): ");
        String isPublicInput = Input.command();
        boolean isPublic = Boolean.parseBoolean(isPublicInput);

        Playlist playlist = Playlist.builder()
                .name(name)
                .isPublic(isPublic)
                .userId(Session.getUser().getId())
                .build();

        if (playlistDao.createPlaylist(playlist)) {
            System.out.println("Playlist created successfully: " + playlist.getName());
        } else {
            System.out.println("Failed to create playlist.");
        }
    }

    private void addSongToPlaylist() {
        try {
            System.out.print("Enter playlist ID: ");
            int playlistId = Integer.parseInt(Input.command());
            System.out.print("Enter song ID to add: ");
            int songId = Integer.parseInt(Input.command());

            if (playlistDao.addSongToPlaylist(playlistId, songId)) {
                System.out.println("Song added successfully to playlist.");
            } else {
                System.out.println("Failed to add song to playlist.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numerical IDs.");
        }
    }

    private void removeSongFromPlaylist() {
        try {
            System.out.print("Enter playlist ID: ");
            int playlistId = Integer.parseInt(Input.command());
            System.out.print("Enter song ID to remove: ");
            int songId = Integer.parseInt(Input.command());

            if (playlistDao.removeSongFromPlaylist(playlistId, songId)) {
                System.out.println("Song removed successfully from playlist.");
            } else {
                System.out.println("Failed to remove song from playlist.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numerical IDs.");
        }
    }

    private void getPlaylistById() {
        try {
            System.out.print("Enter playlist ID: ");
            int playlistId = Integer.parseInt(Input.command());
            Playlist playlist = playlistDao.getPlaylistById(playlistId);

            if (playlist != null) {
                System.out.println("Playlist found: " + playlist.getName());
                displayPlaylistDetails(playlist);
            } else {
                System.out.println("No playlist found with that ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid numerical ID.");
        }
    }

    private void getUserPlaylists() {
        List<Playlist> playlists = playlistDao.getUserPlaylists(Session.getUser().getId());
        if (playlists.isEmpty()) {
            System.out.println("No playlists found for this user.");
        } else {
            System.out.println("User Playlists:");
            for (Playlist playlist : playlists) {
                System.out.println("- ID: " + playlist.getPlaylistId() + ", Name: " + playlist.getName());
            }
        }
    }

    private void getPublicPlaylists() {
        List<Playlist> playlists = playlistDao.getPublicPlaylists();
        if (playlists.isEmpty()) {
            System.out.println("No public playlists available.");
        } else {
            System.out.println("Public Playlists:");
            for (Playlist playlist : playlists) {
                System.out.println("- ID: " + playlist.getPlaylistId() + ", Name: " + playlist.getName());
            }
        }
    }

    private void getSongsInPlaylist() {
        try {
            System.out.print("Enter playlist ID: ");
            int playlistId = Integer.parseInt(Input.command());
            List<Song> songs = playlistDao.getSongsInPlaylist(playlistId);

            if (songs.isEmpty()) {
                System.out.println("No songs found in this playlist.");
            } else {
                System.out.println("Songs in Playlist:");
                for (Song song : songs) {
                    System.out.println("- ID: " + song.getId() + ", Title: " + song.getTitle());
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid numerical ID.");
        }
    }

    /**
     * Displays detailed information about a playlist.
     *
     * @param playlist The playlist to display.
     */
    private void displayPlaylistDetails(Playlist playlist) {
        System.out.println("Playlist ID: " + playlist.getPlaylistId());
        System.out.println("Name: " + playlist.getName());
        System.out.println("Public: " + playlist.isPublic());
        System.out.println("Owner ID: " + playlist.getUserId());
    }
}
