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
}
