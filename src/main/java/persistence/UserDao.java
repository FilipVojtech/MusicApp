package persistence;

import business.User;
import lombok.NonNull;

/**
 * @author Filip Vojtěch
 */
public interface UserDao {
    /**
     * Retrieves a user by their ID
     * @param id ID to look up by
     * @return A new {@systemProperty User} object. Null if the user couldn't be found
     */
    User getUser(@NonNull String id);

    /**
     * Looks up user by their email
     * @param email Email to look up by
     * @return The found user. Null if none found
     */
    User getUserByEmail(@NonNull String email);

    /**
     * Creates a new user.
     * @param user The new user data
     * @return True if create succeeded. False otherwise.
     */
    boolean createUser(@NonNull User user);

    /**
     * Updates the user
     * @param newUserData Data to update the user
     * @return True if update succeeded. False otherwise.
     */
    boolean updateUser(@NonNull User newUserData);
}
