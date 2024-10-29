package persistence;

import business.Rating;
import business.Song;

import java.util.List;

/**
 * @author Alex Clinton
 */

/**
 * Interface defining the data access methods for Rating operations.
 */
public interface RatingDao {

    /**
     * Rates a song by a user.
     *
     * @param rating the Rating object containing rating details
     * @return true if the rating was submitted successfully, false otherwise
     */
    boolean rateSong(Rating rating);

//    /**
//     * Updates an existing rating.
//     *
//     * @param rating the Rating object containing updated rating details
//     * @return true if the rating was updated successfully, false otherwise
//     */
//    boolean updateRating(Rating rating);

    /**
     * Retrieves all ratings submitted by a user.
     *
     * @param userId the ID of the user
     * @return a list of Ratings submitted by the user
     */
    List<Rating> getUserRatings(int userId);

    /**
     * Retrieves the top-rated song based on average ratings.
     *
     * @return the Song object representing the top-rated song
     */
    Song getTopRatedSong();

    /**
     * Retrieves the most popular song based on the number of ratings.
     *
     * @return the Song object representing the most popular song
     */
    Song getMostPopularSong();

    /**
     * Retrieves the average rating of a song.
     *
     * @param songId the ID of the song
     * @return the average rating value
     */
    double getAverageRating(int songId);
}