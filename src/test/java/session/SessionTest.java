package session;

import business.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {
    private final static User testUser = new User(1, "test@test.com", "Password", "Test User");

    @BeforeEach
    void setUp() {
        Session.clearSession();
    }

    @Test
    void isLoggedIn_NoUser() {
        boolean result = Session.IsLoggedIn();
        assertFalse(result, "isLoggedIn returned true, expected was false");
    }

    @Test
    void isLoggedIn_WithUser() {
        Session.setUser(testUser);

        boolean result = Session.IsLoggedIn();
        assertTrue(result, "isLoggedIn returned false, expected was true");
    }

    @Test
    void clearSession_NoPriorUser() {
        assertNull(Session.getUser(), "Unexpected user in Session");
        Session.clearSession();
        assertNull(Session.getUser(), "Session was not cleared");
    }

    @Test
    void clearSession_WithUser() {
        assertNull(Session.getUser(), "Unexpected user in Session");
        Session.setUser(testUser);
        Session.clearSession();
        assertNull(Session.getUser(), "Session was not cleared");
    }

    @Test
    void getUser_WithUser() {
        Session.setUser(testUser);
        User expected = new User(1, "test@test.com", "Password", "Test User");

        assertEquals(expected, Session.getUser(), "Expected user was different from actual");
    }

    @Test
    void getUser_NoUser() {
        assertNull(Session.getUser(), "Expected user was not null");
    }

    @Test
    void setUser_ValidUser() {
        User expectedUser = new User(1, "test@test.com", "Password", "Test User");
        Session.setUser(testUser);
        assertEquals(Session.getUser(), expectedUser, "User was not set successfully");
    }

    @Test
    void setUser_ChangeUser() {
        Session.setUser(testUser);

        User newUser = new User(2, "test2@test.com", "Password", "Test User 2");
        User expectedUser =  new User(2, "test2@test.com", "Password", "Test User 2");
        Session.setUser(newUser);
        assertEquals(Session.getUser(), expectedUser, "User was not set successfully");
    }

    @Test
    void setUser_NullUser() {
        assertThrows(NullPointerException.class, () -> Session.setUser(null), "Setting null user did not throw");
    }
}
