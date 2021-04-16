package jhaturanga.test.model.leaderboard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.leaderboard.Leaderboard;
import jhaturanga.model.leaderboard.builder.LeaderboardBuilder;
import jhaturanga.model.leaderboard.builder.LeaderboardBuilderImpl;
import jhaturanga.model.user.UserImpl;
import jhaturanga.test.model.commons.Constants;

class LeaderboardTest {

    private LeaderboardBuilder builder;

    @BeforeEach
    void setup() {
        this.builder = new LeaderboardBuilderImpl((u1, u2) -> u1.getUsername().compareTo(u2.getUsername()), u1 -> Constants.ZERO);
        this.builder.addUsers(List.of(
                new UserImpl("user1", "", Constants.THREE, Constants.TWO, Constants.ONE),
                new UserImpl("user2", "", Constants.ONE, Constants.TWO, Constants.THREE),
                new UserImpl("user3", "", Constants.ONE, Constants.ONE, Constants.ONE),
                new UserImpl("user4", "", Constants.ZERO, Constants.ZERO, Constants.ZERO),
                new UserImpl("user5", "", Constants.TEN, Constants.ZERO, Constants.ZERO)
                        ));
    }

    @Test
    void normalUseTest() {
        assertDoesNotThrow(() -> this.builder.build());
        assertThrows(IllegalStateException.class, () -> this.builder.build());
    }

    @Test
    void comparatorTest() {
        final Leaderboard l = this.builder
            .comparator((u1, u2) -> u2.getWinCount() - u1.getWinCount())
            .build();
        assertEquals("user5", l.getUsers().get(0).getUsername());
    }

    @Test
    void filterTest() {
        final Leaderboard l = this.builder
            .addFilter(u -> u.getWinCount() > Constants.ZERO)
            .build();
        assertEquals(Constants.FOUR, l.getUsers().size());
    }

    @Test
    void moreFilterTest() {
        final Leaderboard l = this.builder
            .addFilter(u -> u.getWinCount() > Constants.ZERO)
            .addFilter(u -> u.getWinCount() < Constants.FIVE)
            .build();
        assertEquals(Constants.THREE, l.getUsers().size());
    }

    @Test
    void scoreTest() {
        final Leaderboard l = this.builder
            .addFilter(u -> u.getWinCount() == Constants.THREE)
            .strategy(u -> u.getWinCount() * 2)
            .build();
        assertEquals(Constants.SIX, l.getUsers().get(0).getScore());
    }

}
