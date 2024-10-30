package persistence;

import business.Rating;
import business.Song;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Alex Clinton
 */

class RatingDaoImplTest {
    private MySQLDao connectionSource = new MySQLDao("resources/database_test.properties");


    @Test
    void testRateSong_Insert() throws SQLException {

        Connection conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        RatingDaoImpl ratingDao = new RatingDaoImpl(conn);
        Rating newRating = Rating.builder()
                .userId(1)     // Assuming user with ID 1 exists
                .songId(3)     // Assuming song with ID 3 exists
                .ratingValue(4)
                .build();

        boolean result = ratingDao.rateSong(newRating);

        assertTrue(result, "Inserting a new rating should return true");

        String sql = "SELECT rating_value FROM song_ratings WHERE user_id = ? AND song_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newRating.getUserId());
            ps.setInt(2, newRating.getSongId());
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next(), "Rating should exist in the database");
                assertEquals(4, rs.getInt("rating_value"), "Rating value should be 4");
            }
        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    /**
     * Tests updating an existing rating in the database.
     */
    @Test
    void testRateSong_Update() throws SQLException {

        Connection conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        RatingDaoImpl ratingDao = new RatingDaoImpl(conn);
        Rating updatedRating = Rating.builder()
                .userId(1)
                .songId(1)
                .ratingValue(3) // Changing rating from 5 to 3
                .build();

        // Act: Update the rating
        boolean result = ratingDao.rateSong(updatedRating);

        // Assert: The update should be successful
        assertTrue(result, "Updating an existing rating should return true");

        // Additionally, verify that the rating was updated in the database
        String sql = "SELECT rating_value FROM song_ratings WHERE user_id = ? AND song_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, updatedRating.getUserId());
            ps.setInt(2, updatedRating.getSongId());
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next(), "Rating should exist in the database");
                assertEquals(3, rs.getInt("rating_value"), "Rating value should be updated to 3");
            }
        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    /**
     * Tests retrieving all ratings submitted by a specific user.
     */
    @Test
    void testGetUserRatings() throws SQLException {

        Connection conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        RatingDaoImpl ratingDao = new RatingDaoImpl(conn);
        int userId = 1;

        List<Rating> ratings = ratingDao.getUserRatings(userId);

        assertNotNull(ratings, "Ratings list should not be null");
        assertEquals(2, ratings.size(), "User 1 should have 2 ratings");

        boolean hasSong1Rating = false;
        int song1RatingValue = 0;

        for (Rating rating : ratings) {
            if (rating.getSongId() == 1) {
                hasSong1Rating = true;
                song1RatingValue = rating.getRatingValue();
            }

            assertTrue(hasSong1Rating, "User 1 should have rated song 1");
            assertEquals(5, song1RatingValue, "Rating value for song 1 should be 5");
        }
    }

    /**
     * Tests retrieving the top-rated song based on average ratings.
     */
    @Test
    void testGetTopRatedSong() throws SQLException {

        Connection conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        RatingDaoImpl ratingDao = new RatingDaoImpl(conn);
        Song topRatedSong = ratingDao.getTopRatedSong();

        assertNotNull(topRatedSong, "Top rated song should not be null");
        assertEquals("Stairway to Heaven", topRatedSong.getTitle(), "Top rated song should be 'Stairway to Heaven'");
    }

    /**
     * Tests retrieving the most popular song based on the number of playlists it appears in.
     */
    @Test
    void testGetMostPopularSong() throws SQLException {

        Connection conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        RatingDaoImpl ratingDao = new RatingDaoImpl(conn);
        Song mostPopularSong = ratingDao.getMostPopularSong();

        assertNotNull(mostPopularSong, "Most popular song should not be null");
        assertEquals("Come Together", mostPopularSong.getTitle(), "Most popular song should be 'Come Together'");
    }

    /**
     * Tests calculating the average rating of a specific song.
     */
    @Test
    void testGetAverageRating() throws SQLException {

        Connection conn = connectionSource.getConnection();
        conn.setAutoCommit(false);
        RatingDaoImpl ratingDao = new RatingDaoImpl(conn);
        int songId1 = 1; // 'Come Together' rated by user 1 with 5
        int songId2 = 3; // 'Stairway to Heaven' rated by user 2 with 5
        int songId3 = 5; // 'Money' rated by user 3 with 3

        double avgRating1 = ratingDao.getAverageRating(songId1);
        assertEquals(5.0, avgRating1, 0.001, "Average rating for 'Come Together' should be 5.0");

        double avgRating2 = ratingDao.getAverageRating(songId2);
        assertEquals(5.0, avgRating2, 0.001, "Average rating for 'Stairway to Heaven' should be 5.0");

        double avgRating3 = ratingDao.getAverageRating(songId3);
        assertEquals(3.0, avgRating3, 0.001, "Average rating for 'Money' should be 3.0");
    }
}