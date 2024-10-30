package persistence;

import business.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.exceptions.RecordNotFound;

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
        int userId = 4;
        UserDao userDao = new UserDaoImpl(conn);

        assertThrows(RecordNotFound.class, () -> userDao.getUser(userId));
    }

    @Test
    void getUser_ZeroId() {
        int userId = 0;
        UserDao userDao = new UserDaoImpl(conn);

        assertThrows(RecordNotFound.class, () -> userDao.getUser(userId));
    }

    @Test
    void getUserByEmail_ExistingEmail() {
        String userEmail = "user1@example.com";
        UserDao userDao = new UserDaoImpl(conn);
        User expectedUser = new User(
                1,
                "user1@example.com",
                "$2a$14$Sci/QKCO4xEAqh2O3/PSk.XGWdEF.Jxy/AB0cDCkGWu19Gz1d3gPq",
                "User One"
        );
        User user = userDao.getUserByEmail(userEmail);

        assertNotNull(user);
        assertEquals(expectedUser, user);
    }

    @Test
    void getUserByEmail_NonExistentEmail() {
        String email = "nonexisting@testemail.com";
        UserDao userDao = new UserDaoImpl(conn);

        assertThrows(RecordNotFound.class, () -> userDao.getUserByEmail(email));
    }

    @Test
    void getUserByEmail_InvalidEmail() {
        String email = "oh@no";
        UserDao userDao = new UserDaoImpl(conn);

        assertThrows(RecordNotFound.class, () -> userDao.getUserByEmail(email));
    }

    @Test
    void createUser() throws SQLException {
        MySQLDao db = new MySQLDao("database-test.properties");
        Connection con = db.getConnection();

        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            fail("Could not establish connection.");
        }

        UserDao userDao = new UserDaoImpl(con);
        String newEmail = "test-code@test.com";
        User newUser = new User(newEmail, "Testing Password", "Test Name");
        boolean result = userDao.createUser(newUser);
        User resultUser = userDao.getUserByEmail("test-code@test.com");

        assertTrue(result);

        User expectedUser = new User(4, newEmail, "Testing Password", "Test Name");
        assertEquals(resultUser, expectedUser);

        con.rollback();
    }

    @Test
    void createUser_2() {
        try (UserDao userDao = new UserDaoImpl(conn)) {
        } catch (Exception e) {
            fail("Could not init UserDao");
        }
    }
}
