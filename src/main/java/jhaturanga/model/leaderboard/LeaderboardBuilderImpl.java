package jhaturanga.model.leaderboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import jhaturanga.model.user.User;

public class LeaderboardBuilderImpl implements LeaderboardBuilder {

    private final List<User> users = new ArrayList<>();
    private Predicate<User> tests = u -> true;
    private Comparator<User> comparator;

    @Override
    public LeaderboardBuilder addUsers(final Collection<User> users) {
        this.users.addAll(users);
        return this;
    }

    @Override
    public LeaderboardBuilder addFilter(final Predicate<User> predicate) {
        this.tests = tests.and(predicate);
        return this;
    }

    @Override
    public LeaderboardBuilder layComparator(final Comparator<User> comparator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> build() {
        // TODO Auto-generated method stub
        return null;
    }


}
