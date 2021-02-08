package jhaturanga.model.user;

/**
 * An interface that represent a Builder for Users.
 *
 */
public interface UserBuilder {

    /**
     * 
     * @param username of the User that is unique.
     * @return the builder
     */
    UserBuilder username(String username);

    /**
     * 
     * @param hashedPassword of the User
     * @return the builder
     */
    UserBuilder hashedPassword(String hashedPassword);

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
