package jhaturanga.test.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.user.User;
import jhaturanga.model.user.UserBuilder;
import jhaturanga.model.user.UserBuilderImpl;
import jhaturanga.model.user.UserImpl;

class UserBuilderTest {

    private static final String ENCRYPTED = "encrypted";
    private UserBuilder builder;
    private static final String NAME = "Mario";

    @BeforeEach
    public void init() {
        builder = new UserBuilderImpl();
    }

    @Test
    void normalBuild() {
        final var user = builder.username(NAME).hashedPassword(ENCRYPTED).winCount(3).drawCount(2).lostCount(1).build();
        final User sameUser = new UserImpl(NAME, ENCRYPTED, 3, 2, 1);
        assertEquals(sameUser, user);
    }

    @Test
    void emptyBuilder() {
        assertNull(builder.build());
    }

    @Test
    void moreBuild() {
        var user = builder.username(NAME).build();
        assertNotNull(user);

        user = builder.build();
        assertNull(user);
    }

}
