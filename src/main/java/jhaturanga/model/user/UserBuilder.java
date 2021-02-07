package jhaturanga.model.user;

/**
 * An interface that represent a Builder for Users.
 *
 */
public interface UserBuilder {

    /**
     * 
     * @param id of the User
     * @return the builder
     */
    UserBuilder id(int id);

    /**
     * 
     * @param username of the User.
     * @return the builder
     */
    UserBuilder username(String username);

    /**
     * 
     * @param count of win match
     * @return the builder
     */
    UserBuilder winCount(int count);

    /**
     * 
     * @param count of draw match
     * @return the builder
     */
    UserBuilder drawCount(int count);

    /**
     * 
     * @param count of lost match
     * @return the builder
     */
    UserBuilder lostCount(int count);

    /**
     * 
     * @return the built user only one time.
     */
    User build();
}
