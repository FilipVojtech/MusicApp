package persistence;

import business.User;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Filip Vojtěch
 */
public class UserDaoImpl extends MySQLDao implements UserDao {
    public UserDaoImpl() {
        super();
    }

    public UserDaoImpl(Connection conn) {
        super(conn);
    }

    public UserDaoImpl(String propertiesFilename) {
        super(propertiesFilename);
    }

    @Override
    public boolean createUser(@NonNull User user) {
        final var sql = "INSERT INTO app_user (email, password, display_name) VALUE (?, ?, ?)";

        try (Connection con = super.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(0, user.getEmail());
            ps.setString(1, user.getPasswordHash());
            ps.setString(2, user.getDisplayName());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Couldn't create user.");
        }
        return false;
    }

    @Override
    public boolean updateUser(@NonNull User newUserData) {
        if (newUserData.getId() == 0) {
            System.out.println("Cannot update user. ID is not set.");
        }
        final var sql = "UPDATE users SET display_name = ?, email = ? WHERE id = ?;";

        try (Connection con = super.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, newUserData.getDisplayName());
            statement.setString(2, newUserData.getEmail());
            statement.setInt(3, newUserData.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Couldn't update user");
        }
        return false;
    }
}
