package persistence;

import business.Rating;
import business.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingDaoImpl extends MySQLDao implements RatingDao{
    public RatingDaoImpl(Connection conn) {
        super(conn);
    }

    public RatingDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }

    @Override
    public boolean rateSong(Rating rating) {
        String sql = "INSERT INTO song_ratings (user_id, song_id, rating_value) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE rating_value = ?";
        int rowsAffected = 0;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, rating.getUserId());
            ps.setInt(2, rating.getSongId());
            ps.setInt(3, rating.getRatingValue());
            ps.setInt(4, rating.getRatingValue()); // For the UPDATE part

            rowsAffected = ps.executeUpdate();

            // Retrieve the generated rating ID (if a new rating was inserted)
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    rating.setRatingId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting/updating rating: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return rowsAffected > 0;
    }

//    @Override
//    public boolean updateRating(Rating rating) {
//        return false;
//    }

    @Override
    public List<Rating> getUserRatings(int userId) {
        String sql = "SELECT sr.rating_id, sr.user_id, sr.song_id, sr.rating_value, s.title " +
                "FROM song_ratings sr JOIN song s ON sr.song_id = s.id " +
                "WHERE sr.user_id = ?";
        List<Rating> ratings = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Rating rating = Rating.builder()
                            .ratingId(rs.getInt("rating_id"))
                            .userId(rs.getInt("user_id"))
                            .songId(rs.getInt("song_id"))
                            .ratingValue(rs.getInt("rating_value"))
                            .build();

                    ratings.add(rating);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user ratings: " + e.getMessage());
            e.printStackTrace();
        }

        return ratings;
    }

    @Override
    public Song getTopRatedSong() {
        return null;
    }

    @Override
    public Song getMostPopularSong() {
        return null;
    }

    @Override
    public double getAverageRating(int songId) {
        return 0;
    }
}
