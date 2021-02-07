package jhaturanga.test.user;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.user.UserBuilderImpl;
import jhaturanga.model.user.UserImpl;
import jhaturanga.model.user.User;
import jhaturanga.model.user.UserBuilder;

class UserBuilderTest {

    private UserBuilder builder;
    private static final String NAME = "Mario";

    @BeforeEach
    public void initBuilder() {
        builder = new UserBuilderImpl();
    }

    @Test
    void normalBuild() {
        final var user = builder.id(1)
                .username(NAME)
                .winCount(3)
                .drawCount(2)
                .lostCount(1)
                .build();
        final User sameUser = new UserImpl(1, NAME, 3, 2, 1);
        assertEquals(sameUser, user);
    }

    @Test
    void emptyBuilder() {
        final var user = builder.build();
        final User sameUser = new UserImpl(0, "", 0, 0, 0);
        assertEquals(sameUser, user);
    }

    @Test
    void moreBuild() {
        var user = builder.build();
        assertNotNull(user);

        user = builder.build();
        assertNull(user);
    }

}
