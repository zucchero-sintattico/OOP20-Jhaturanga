package jhaturanga.model.user;

/**
 * 
 * Implementation of {@link UserBuilder} that can be built just one time.
 *
 */
public final class UserBuilderImpl implements UserBuilder {

    private String username;
    private String hashedPassword;
    private int winCount;
    private int drawCount;
    private int lostCount;
    private boolean build;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBuilder username(final String username) {
        this.username = username;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBuilder hashedPassword(final String hashedPassword) {
        this.hashedPassword = hashedPassword;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBuilder winCount(final int count) {
        this.winCount = count;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBuilder drawCount(final int count) {
        this.drawCount = count;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserBuilder lostCount(final int count) {
        this.lostCount = count;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User build() {
        if (this.build) {
            return null;
        }
        if (this.username == null) {
            return null;
        }
        this.build = true;

        return new UserImpl(this.username, this.hashedPassword, this.winCount, this.drawCount, this.lostCount);
    }
}
