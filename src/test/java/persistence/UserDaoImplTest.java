package persistence;

import business.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Filip Vojtěch
 */
class UserDaoImplTest {
    private MySQLDao mysql = new MySQLDao("database-test.properties");
    private Connection conn;

    @BeforeEach
    void setUp() {
        conn = mysql.getConnection();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            fail("Could not establish connection.");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        conn.rollback();
        mysql.close();
    }

    @Test
    void getUser_ExistingId() {
        int userId = 1;
        UserDao userDao = new UserDaoImpl(conn);
        User expectedUser = new User(
                1,
                "user1@example.com",
                "$2a$14$Sci/QKCO4xEAqh2O3/PSk.XGWdEF.Jxy/AB0cDCkGWu19Gz1d3gPq",
                "User One"
        );
        User user = userDao.getUser(userId);

        assertNotNull(user);
        assertEquals(expectedUser, user);
    }

    @Test
    void getUser_InvalidId() {
        try (UserDao userDao = new UserDaoImpl(conn)) {
        } catch (Exception e) {
            fail("Could not init UserDao");
        }
    }

    @Test
    void getUserByEmail_ExistingEmail() {
        try (UserDao userDao = new UserDaoImpl(conn)) {
        } catch (Exception e) {
            fail("Could not init UserDao");
        }
    }

    @Test
    void getUserByEmail_InvalidEmail() {
        try (UserDao userDao = new UserDaoImpl(conn)) {
        } catch (Exception e) {
            fail("Could not init UserDao");
        }
    }

    @Test
    void createUser_1() {
        try (UserDao userDao = new UserDaoImpl(conn)) {
        } catch (Exception e) {
            fail("Could not init UserDao");
        }
    }

    @Test
    void createUser_2() {
        try (UserDao userDao = new UserDaoImpl(conn)) {
        } catch (Exception e) {
            fail("Could not init UserDao");
        }
    }
}
