package persistence;

import business.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SongDaoImplTest {

    private MySQLDao connectionSource = new MySQLDao("resources/database_test.properties");
    private Connection conn;
    private SongDaoImpl songDao;

    @BeforeEach
    void setUp() throws SQLException {
        conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        songDao = new SongDaoImpl(conn);

        // Insert test data
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("INSERT INTO artists (id, name) VALUES (1, 'Artist One')");
            statement.executeUpdate("INSERT INTO albums (id, title, artist_id) VALUES (1, 'Album One', 1)");
            statement.executeUpdate("INSERT INTO songs (id, title, album_id) VALUES (1, 'Test Song 1', 1)");
            statement.executeUpdate("INSERT INTO songs (id, title, album_id) VALUES (2, 'Test Song 2', 1)");
        }
    }

    @Test
    void getSongsByAlbum() throws SQLException {
        int albumId = 1;
        List<Song> songs = songDao.getSongsByAlbum(albumId);

        assertNotNull(songs, "Songs list should not be Emp.");
        assertEquals(2, songs.size(), "Songs list should contain two songs");

        Song song1 = songs.getFirst();
        assertEquals(1, song1.getId(), "First song ID should be 1");
        assertEquals("Test Song 1", song1.getTitle(), "First song title should be" + song1.getTitle());
        assertEquals(albumId, song1.getAlbumId(), "Album ID should match the input album ID");

        Song song2 = songs.get(1);
        assertEquals(2, song2.getId(), "Second song ID should be 2");
        assertEquals("Test Song 2", song2.getTitle(), "Second song title should be " + song2.getTitle());
        assertEquals(albumId, song2.getAlbumId(), "Album ID should match the input album ID");
    }
}




