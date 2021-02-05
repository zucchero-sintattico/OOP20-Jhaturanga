package jhaturanga.model.user;

public interface User {

    /**
     * 
     * @return the Unique identifier of the user
     */
    int getUserID();

    /**
     * 
     * @return the name of the user
     */
    String getUserName();

    /**
     * 
     * @return the number of win match
     */
    int getWinCount();

    /**
     * 
     * @return the number of lost match
     */
    int getLostCount();

    /**
     * 
     * @return the number of draw match
     */
    int getDrawCount();

    /**
     * 
     * @return the number of match played
     */
    int getPlayedMatchCount();

    /**
     * Increase the win Count.
     */
    void increaseWinCount();

    /**
     * Increase the draw Count.
     */
    void increaseDrawCount();

    /**
     * Increase the lost Count.
     */
    void increaseLostCount();
}
