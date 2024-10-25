package persistence;

import business.Playlist;
import business.Song;

import java.sql.*;
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
        String sql = "INSERT INTO playlist (owner_id, visibility, rating) VALUES (?, ?, ?)";
        int rowsAffected = 0;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Statement.RETURN_GENERATED_KEYS IS USED TO RETURN AUTO GENERATED KEYS IN THE DATABASE
            ps.setInt(1, playlist."getOwnerId"()); // TEMP UNTIL OTHER WORK IS DONE
            ps.setBoolean(2, playlist."isVisibility"());// TEMP UNTIL OTHER WORK IS DONE
            ps.setInt(3, playlist."getRating"());// TEMP UNTIL OTHER WORK IS DONE

            rowsAffected = ps.executeUpdate();

            // Retrieve the generated playlist ID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    playlist."setId"(generatedKeys.getInt(1)); // TEMP UNTIL OTHER WORK IS DONE
                }
            }

        } catch (SQLException e) {
            System.err.println("Error creating playlist: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected > 0;
    }

    @Override
    public boolean updatePlaylist(Playlist playlist) {
        String sql = "UPDATE playlist SET visibility = ?, rating = ? WHERE id = ?";
        int rowsAffected = 0;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, playlist."isVisibility"()); // TEMP UNTIL OTHER WORK IS DONE
            ps.setInt(2, playlist."getRating"()); // TEMP UNTIL OTHER WORK IS DONE
            ps.setInt(3, playlist."getId"()); // TEMP UNTIL OTHER WORK IS DONE

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating playlist: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected > 0;
    }

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {
        String sql = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";
        int rowsAffected = 0;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playlistId);
            ps.setInt(2, songId);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding song to playlist: " + e.getMessage());
            e.printStackTrace();
        }

        return rowsAffected > 0;
    }

    @Override
    public boolean removeSongFromPlaylist(int playlistId, int songId) {
        return false;
    }

    @Override
    public boolean renamePlaylist(int playlistId, String newName) {
        return false;
    }

    @Override
    public Playlist getPlaylistById(int playlistId) {
        return null;
    }

    @Override
    public List<Playlist> getUserPlaylists(int userId) {
    }

    @Override
    public List<Playlist> getPublicPlaylists() {
    }

    @Override
    public List<Song> getSongsInPlaylist(int playlistId) {
    }

}
