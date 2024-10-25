package persistence;

import business.Playlist;
import business.Song;
import business.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDaoImpl extends MySQLDao implements PlaylistDao {
    public PlaylistDaoImpl(Connection conn) {
        super(conn);
    }

    public PlaylistDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }

    @Override
    public boolean createPlaylist(Playlist playlist) {
        String sql = "INSERT INTO playlist (owner_id, name, visibility) VALUES (?, ?, ?)";
        int rowsAffected = 0;

        // Get a connection using the superclass
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))  {
            // RETURN_GENERATED_KEYS. This reutnrs auto generated keys within the database
            // Set the parameters
            ps.setInt(1, playlist.getUserId());
            ps.setString(2, playlist.getName());
            ps.setBoolean(3, playlist.isPublic());

            // Execute the update
            rowsAffected = ps.executeUpdate();

            // Retrieve the generated playlist ID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    playlist.setPlaylistId(generatedKeys.getInt(1));
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving generated keys: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("Error creating playlist: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected > 0;
    }

    @Override
    public boolean updatePlaylist(Playlist playlist) {
        String sql = "UPDATE playlist SET name = ?, visibility = ? WHERE id = ?";
        int rowsAffected = 0;

        // Get a connection
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameters
            ps.setString(1, playlist.getName());
            ps.setBoolean(2, playlist.isPublic());
            ps.setInt(3, playlist.getPlaylistId());

            // Execute the update
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating playlist: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected > 0;
    }

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {
        String sql = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";
        int rowsAffected = 0;

        // Get a connection using the superclass
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameters
            ps.setInt(1, playlistId);
            ps.setInt(2, songId);

            // Execute the update
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding song to playlist: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected > 0;
    }

    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        String sql = "DELETE FROM playlist_songs WHERE playlist_id = ? AND song_id = ?";
        int rowsAffected = 0;

        // Get a connection using the superclass
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameters
            ps.setInt(1, playlistId);
            ps.setInt(2, songId);

            // Execute the update
            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error removing song from playlist: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected > 0;
    }
//    @Override
//    public boolean renamePlaylist(int playlistId, String newName) {
//        return false;
//    }

    @Override
    public Playlist getPlaylistById(int playlistId) {
        String sql = "SELECT * FROM playlist WHERE id = ?";
        Playlist playlist = null;

        // Get a connection using the superclass
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameter
            ps.setInt(1, playlistId);

            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    playlist = mapPlaylist(rs);
                    // Optionally, load songs in the playlist
                    playlist.setSongs(getSongsInPlaylist(playlistId));
                }
            } catch (SQLException e) {
                System.err.println("Error executing query or processing results: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("Error preparing SQL for execution: " + e.getMessage());
            e.printStackTrace();
        }

        return playlist;
    }
    @Override
    public List<Playlist> getUserPlaylists(int userId) {
    }

    @Override
    public List<Playlist> getPublicPlaylists() {
    }

    @Override
    public List<Song> getSongsInPlaylist(int playlistId) {
        String sql = "SELECT s.* FROM song s JOIN playlist_songs ps ON s.id = ps.song_id WHERE ps.playlist_id = ?";
        List<Song> songs = new ArrayList<>();

        // Get a connection using the superclass
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the parameter
            ps.setInt(1, playlistId);

            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Song song = mapSong(rs);
                    songs.add(song);
                }
            } catch (SQLException e) {
                System.err.println("Error executing query or processing results: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("Error preparing SQL for execution: " + e.getMessage());
            e.printStackTrace();
        }

        return songs;
    }

    /**
     * Maps a ResultSet row to a Playlist object.
     *
     * @param rs the ResultSet containing playlist data
     * @return a Playlist object
     * @throws SQLException if a database access error occurs
     */
    private Playlist mapPlaylist(ResultSet rs) throws SQLException {
        return Playlist.builder()
                .playlistId(rs.getInt("id"))
                .name(rs.getString("name"))
                .isPublic(rs.getBoolean("visibility"))
                .userId(rs.getInt("owner_id"))
                .build();
    }

    /**
     * Maps a ResultSet row to a Song object.
     *
     * @param rs the ResultSet containing song data
     * @return a Song object
     * @throws SQLException if a database access error occurs
     */
    private Song mapSong(ResultSet rs) throws SQLException {
        // Adjust according to your Song class
        return Song.builder()
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .artist_id(rs.getInt("artist_id"))
                // Include other song fields as necessary
                .build();
    }

}
