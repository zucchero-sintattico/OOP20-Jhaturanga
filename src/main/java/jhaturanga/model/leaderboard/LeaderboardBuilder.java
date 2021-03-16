package jhaturanga.model.leaderboard;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import jhaturanga.model.user.User;

/**
 *
 * Builder pattern for construct a Leaderboard.
 */
public interface LeaderboardBuilder {

    LeaderboardBuilder addUsers(Collection<User> users);

    LeaderboardBuilder addFilter(Predicate<User> predicate);

    LeaderboardBuilder layComparator(Comparator<User> comparator);

    List<User> build();
}
