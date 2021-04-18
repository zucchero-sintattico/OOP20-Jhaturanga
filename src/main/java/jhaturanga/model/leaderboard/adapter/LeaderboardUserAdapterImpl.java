package jhaturanga.model.leaderboard.adapter;

import jhaturanga.model.user.User;

public final class LeaderboardUserAdapterImpl implements LeaderboardUserAdapter {

    private final User user;
    private final int score;

    public LeaderboardUserAdapterImpl(final User user, final int score) {
        this.user = user;
        this.score = score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWinCount() {
        return this.user.getWinCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLostCount() {
        return this.user.getLostCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDrawCount() {
        return this.user.getDrawCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayedMatchCount() {
        return this.user.getPlayedMatchCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "LeaderboardUserAdapterImpl [score=" + score + ", username=" + getUsername() + ", win=" + getWinCount()
                + ", lost=" + getLostCount() + ", draw=" + getDrawCount() + ", MatchCount=" + getPlayedMatchCount()
                + "]";
    }

}
