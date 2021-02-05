package jhaturanga.test.user;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.user.User;
import jhaturanga.model.user.UserImpl;

class UserTest {

    private User user;

    @BeforeEach
    void initUser() {
        user = new UserImpl(1, "Mario", 0, 0, 0);
    }

    @Test
    public void userIncrease() {
        user.increaseWinCount();
        user.increaseWinCount();

        user.increaseLostCount();

        user.increaseDrawCount();
        user.increaseDrawCount();
        user.increaseDrawCount();

        assertEquals(2, user.getWinCount());
        assertEquals(1, user.getLostCount());
        assertEquals(3, user.getDrawCount());
        assertEquals(2 + 1 + 3, user.getPlayedMatchCount());
    }

    @Test
    public void userData() {
        assertEquals(1, user.getUserID());
        assertEquals("Mario", user.getUserName());
    }

    @Test
    public void nullUser() {
        final User nullUser = new UserImpl(0, null, 0, 0, 0);
        assertNull(nullUser.getUserName());
    }

    @Test
    public void equalsTest() {
        final User other = new UserImpl(1, "Luigi", 3, 2, 1);
        assertEquals(other, user);
    }

}
