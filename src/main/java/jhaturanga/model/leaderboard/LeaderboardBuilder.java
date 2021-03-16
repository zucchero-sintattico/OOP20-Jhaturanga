package jhaturanga.model.leaderboard;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import jhaturanga.model.user.User;

/**
 *
 * Builder pattern for construct a Leaderboard list.
 */
public interface LeaderboardBuilder {

    /**
     * 
     * @param users for creating a Leaderboard
     * @return LeaderboardBuilder for Builder Pattern
     */
    LeaderboardBuilder addUsers(Collection<User> users);

    /**
     * 
     * @param predicate for filter in the Leaderboard
     * @return LeaderboardBuilder for Builder Pattern
     */
    LeaderboardBuilder addFilter(Predicate<User> predicate);

    /**
     * 
     * @param comparator for ordering the Leaderboard
     * @return LeaderboardBuilder for Builder Pattern
     */
    LeaderboardBuilder comparator(Comparator<User> comparator);

    /**
     * 
     * @return the Leaderboard list built
     */
    List<User> build();
}
