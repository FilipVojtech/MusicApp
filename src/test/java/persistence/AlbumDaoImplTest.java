package persistence;

import business.Album;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//Dylan
public class AlbumDaoImplTest {

    private MySQLDao connectionSource = new MySQLDao("resources/database_test.properties");

    private Connection conn;
    private AlbumDaoImpl albumDao;
    @BeforeEach
    void setUp() throws SQLException {
        conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        albumDao = new AlbumDaoImpl(conn);

        // Insert test data
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("INSERT INTO artists (id, name) VALUES (1, 'Test Artist')");
            statement.executeUpdate("INSERT INTO albums (id, title, artist_id) VALUES (1, 'Test Album 1', 1)");
            statement.executeUpdate("INSERT INTO albums (id, title, artist_id) VALUES (2, 'Test Album 2', 1)");
        }
    }

    @Test
    void getAlbumByArtist() throws SQLException {
        int artistId = 1;
        List<Album> albums = albumDao.getAlbumsByArtist(artistId);

        assertNotNull(albums, "Albums list should not be null.");
        assertEquals(2, albums.size(), "Albums list should contain two albums.");

        Album album1 = albums.getFirst();
        assertEquals(1, album1.getId(), "First album ID should be 1.");
        assertEquals("Test Album 1", album1.getTitle(), "First album title should be 'Test Album 1'.");
        assertEquals(artistId, album1.getArtist_Id(), "Artist ID should match the input artist ID.");

        Album album2 = albums.get(1);
        assertEquals(2, album2.getId(), "Second album ID should be 2.");
        assertEquals("Test Album 2", album2.getTitle(), "Second album title should be 'Test Album 2'.");
        assertEquals(artistId, album2.getArtist_Id(), "Artist ID should match the input artist ID.");
    }
}

