package jhaturanga.test.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.user.User;
import jhaturanga.model.user.UserImpl;

class UserTest {

    private static final String NAME = "Mario";
    private User user;

    @BeforeEach
    void initUser() {
        user = new UserImpl(NAME, "encrypted", 0, 0, 0);
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
        assertEquals(NAME, user.getUserName());
        assertEquals(Optional.of("encrypted"), user.getHashedPassword());
    }

    @Test
    public void optionalPassword() {
        final User user = new UserImpl(NAME, null, 0, 0, 0);
        assertEquals(Optional.empty(), user.getHashedPassword());
    }

    @Test
    public void equalsTest() {
        final User other = new UserImpl(NAME, null, 3, 2, 1);
        assertEquals(other, user);
    }

}
