package persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

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
    void tearDown() {
        mysql.close();
    }

    @Test
    void getUser_ExistingId() {
        try (UserDao userDao = new UserDaoImpl(conn)) {
        } catch (Exception e) {
            fail("Could not init UserDao");
        }
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
