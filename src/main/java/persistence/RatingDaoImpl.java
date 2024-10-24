package persistence;

import business.Rating;
import business.Song;

import java.sql.Connection;
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
        return false;
    }

    @Override
    public boolean updateRating(Rating rating) {
        return false;
    }

    @Override
    public List<Rating> getUserRatings(int userId) {
        return List.of();
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
