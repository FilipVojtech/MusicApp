package persistence;
import business.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 * @author Dylan Habis
 */
public class ArtistDaoImplTest {

    private MySQLDao connectionSource = new MySQLDao("resources/database_test.properties");
    private Connection conn;
    private ArtistDaoImpl artistDao;

    @BeforeEach
    void setUp() throws SQLException {
        conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        artistDao = new ArtistDaoImpl(conn);

        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("INSERT INTO artist (id, name) VALUES (1, 'Artist One')");
            statement.executeUpdate("INSERT INTO artist (id, name) VALUES (2, 'Artist Two')");
        }
    }

    @Test
    void getAllArtists_ReturnsListOfArtists() throws SQLException {
        List<Artist> artists = artistDao.getAllArtists();

        assertNotNull(artists, "Artists list should not be null.");
        assertEquals(2, artists.size(), "Artists list should contain two artists.");

        Artist artist1 = artists.get(0);
        assertEquals(1, artist1.getId(), "First artist ID should be 1.");
        assertEquals("Artist One", artist1.getName(), "First artist name should be 'Artist One'.");

        Artist artist2 = artists.get(1);
        assertEquals(2, artist2.getId(), "Second artist ID should be 2.");
        assertEquals("Artist Two", artist2.getName(), "Second artist name should be 'Artist Two'.");
    }
}


