package persistence;

import business.Playlist;
import business.Song;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Alex Clinton
 */

class PlaylistDaoImplTest {
    private MySQLDao connectionSource = new MySQLDao("resources/database_test.properties");


    /*
    at current time of making tests, connection to dummy db isnt working. Unsure if tests are correct or wrong
     */

    /**
     * Tests the successful creation of a playlist.
     */
    @Test
    void createPlaylist() throws SQLException {
        Connection conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        PlaylistDaoImpl playlistDao = new PlaylistDaoImpl(conn);

        Playlist newPlaylist = Playlist.builder()
                .userId(1) //assumes user id is 1
                .name("My Playlist")
                .isPublic(true)
                .songs(null)
                .build();

        boolean result = playlistDao.createPlaylist(newPlaylist);
        int generatedId = newPlaylist.getPlaylistId();

        assertTrue(result, "Playlist should be created successfully.");
        assertTrue(generatedId > 0, "Generated Playlist ID should be greater than 0.");

        Playlist insertedPlaylist = playlistDao.getPlaylistById(generatedId);
        assertNotNull(insertedPlaylist, "Inserted Playlist should not be null.");
        assertEquals(newPlaylist.getUserId(), insertedPlaylist.getUserId(), "User IDs should match.");
        assertEquals(newPlaylist.getName(), insertedPlaylist.getName(), "Playlist names should match.");
        assertEquals(newPlaylist.isPublic(), insertedPlaylist.isPublic(), "Visibility should match.");
        assertNull(insertedPlaylist.getSongs(), "Songs list should be null initially.");

        // use this to test equals() if just looking at playlistid
        assertEquals(newPlaylist.getPlaylistId(), insertedPlaylist.getPlaylistId(), "Playlists should have the same ID.");

        conn.commit();
    }

    /**
     * Tests updating an existing playlist.
     */
    @Test
    void updatePlaylist() throws SQLException {
        Connection conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        PlaylistDaoImpl playlistDao = new PlaylistDaoImpl(conn);

        // create new playlist to update
        Playlist originalPlaylist = Playlist.builder()
                .userId(2) // assumes id 2 exists
                .name("Original Playlist")
                .isPublic(false)
                .songs(null)
                .build();

        boolean createResult = playlistDao.createPlaylist(originalPlaylist);
        assertTrue(createResult, "Playlist should be created successfully.");
        int playlistId = originalPlaylist.getPlaylistId();
        assertTrue(playlistId > 0, "Generated Playlist ID should be greater than 0.");

        Playlist updatedPlaylist = Playlist.builder()
                .playlistId(playlistId)
                .userId(2)
                .name("Updated Playlist")
                .isPublic(true) // if updated visibility
                .songs(null) // no change in songs
                .build();

        boolean updateResult = playlistDao.updatePlaylist(updatedPlaylist);

        assertTrue(updateResult, "Playlist should be updated successfully.");
        Playlist fetchedPlaylist = playlistDao.getPlaylistById(playlistId);
        assertNotNull(fetchedPlaylist, "Updated playlist should not be null.");
        assertEquals(updatedPlaylist.getName(), fetchedPlaylist.getName(), "Playlist name should be updated.");
        assertEquals(updatedPlaylist.isPublic(), fetchedPlaylist.isPublic(), "Visibility should be updated.");
        assertNull(fetchedPlaylist.getSongs(), "Songs list should remain unchanged.");

        conn.commit();
        conn.close();
    }
//

    /**
     * Tests adding songs to a playlist.
     */
    @Test
    void addSongToPlaylist() throws SQLException {
        Connection conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        PlaylistDaoImpl playlistDao = new PlaylistDaoImpl(conn);

        Playlist newPlaylist = Playlist.builder()
                .userId(3) // assumes id 3 exists
                .name("Rock Classics")
                .isPublic(true)
                .songs(null)
                .build();

        boolean createResult = playlistDao.createPlaylist(newPlaylist);
        assertTrue(createResult, "Playlist should be created successfully.");
        int playlistId = newPlaylist.getPlaylistId();
        assertTrue(playlistId > 0, "Generated Playlist ID should be greater than 0.");

        // this assumes the following song ids actually exists in db
        int songId1 = 1;
        int songId2 = 2;

        boolean addResult1 = playlistDao.addSongToPlaylist(playlistId, songId1);
        boolean addResult2 = playlistDao.addSongToPlaylist(playlistId, songId2);

        assertTrue(addResult1, "Song 1 should be added successfully.");
        assertTrue(addResult2, "Song 2 should be added successfully.");

        List<Song> songsInPlaylist = playlistDao.getSongsInPlaylist(playlistId);
        assertNotNull(songsInPlaylist, "Songs list should not be null.");
        assertEquals(2, songsInPlaylist.size(), "Playlist should contain two songs.");


        conn.commit();
        conn.close();
    }
//
//    @Test
//    void removeSongFromPlaylist() {
//    }
//

    /**
     * Tests retrieving a playlist by its ID.
     */
    @Test
    void getPlaylistById() throws SQLException {
        // Arrange
        Connection conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        PlaylistDaoImpl playlistDao = new PlaylistDaoImpl(conn);

        Playlist newPlaylist = Playlist.builder()
                .userId(2) // assume id 2 in db
                .name("Workout Mix")
                .isPublic(true)
                .songs(null)
                .build();

        boolean createResult = playlistDao.createPlaylist(newPlaylist);
        assertTrue(createResult, "Playlist should be created successfully.");
        int playlistId = newPlaylist.getPlaylistId();
        assertTrue(playlistId > 0, "Generated Playlist ID should be greater than 0.");

        Playlist fetchedPlaylist = playlistDao.getPlaylistById(playlistId);

        assertNotNull(fetchedPlaylist, "Fetched Playlist should not be null.");
        assertEquals(newPlaylist.getUserId(), fetchedPlaylist.getUserId(), "User IDs should match.");
        assertEquals(newPlaylist.getName(), fetchedPlaylist.getName(), "Playlist names should match.");
        assertEquals(newPlaylist.isPublic(), fetchedPlaylist.isPublic(), "Visibility should match.");
        assertNull(fetchedPlaylist.getSongs(), "Songs list should be null initially.");

        conn.commit();
        conn.close();
    }
//

    /**
     * Tests retrieving all playlists for a specific user.
     */
    @Test
    void getUserPlaylists() throws SQLException {
        Connection conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        PlaylistDaoImpl playlistDao = new PlaylistDaoImpl(conn);

        // make 2 playlists for user
        Playlist playlist1 = Playlist.builder()
                .userId(4) // assume id 4 exist in db
                .name("Party Hits")
                .isPublic(true)
                .songs(null)
                .build();

        Playlist playlist2 = Playlist.builder()
                .userId(4) // same id as before
                .name("Relaxing Tunes")
                .isPublic(false)
                .songs(null)
                .build();

        boolean createResult1 = playlistDao.createPlaylist(playlist1);
        boolean createResult2 = playlistDao.createPlaylist(playlist2);
        assertTrue(createResult1, "First Playlist should be created successfully.");
        assertTrue(createResult2, "Second Playlist should be created successfully.");
        int playlistId1 = playlist1.getPlaylistId();
        int playlistId2 = playlist2.getPlaylistId();
        assertTrue(playlistId1 > 0, "First Playlist ID should be greater than 0.");
        assertTrue(playlistId2 > 0, "Second Playlist ID should be greater than 0.");

        conn.commit();
        conn.close();
    }
//

    /**
     * Tests retrieving all public playlists.
     */
    @Test
    void getPublicPlaylists() throws SQLException {
        // Arrange
        Connection conn = connectionSource.getConnection();
        conn.setAutoCommit(false); // Start transaction
        PlaylistDaoImpl playlistDao = new PlaylistDaoImpl(conn);

        Playlist publicPlaylist = Playlist.builder()
                .userId(1) // assume id 1 exists
                .name("Public Playlist")
                .isPublic(true)
                .songs(null)
                .build();

        boolean createResult = playlistDao.createPlaylist(publicPlaylist);
        assertTrue(createResult, "Public Playlist should be created successfully.");
        int publicId = publicPlaylist.getPlaylistId();
        assertTrue(publicId > 0, "Public Playlist ID should be greater than 0.");

        List<Playlist> publicPlaylists = playlistDao.getPublicPlaylists();

        assertNotNull(publicPlaylists, "Public Playlists should not be null.");
        assertFalse(publicPlaylists.isEmpty(), "Public Playlists should contain at least one playlist.");

        conn.commit();
        conn.close();
    }


}
