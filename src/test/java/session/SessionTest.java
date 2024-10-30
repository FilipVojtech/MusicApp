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
}
