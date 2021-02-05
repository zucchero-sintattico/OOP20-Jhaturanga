package jhaturanga.model.user;

public interface UserBuilder {

    /**
     * 
     * @param count of win match. Otherwise is 0
     * @return the builder
     */
    UserBuilder winCount(int count);

    /**
     * 
     * @param count of draw match. Otherwise is 0
     * @return the builder
     */
    UserBuilder drawCount(int count);

    /**
     * 
     * @param count of lost match. Otherwise is 0
     * @return the builder
     */
    UserBuilder lostCount(int count);

    /**
     * 
     * @return the built user only one time.
     */
    User build();
}
