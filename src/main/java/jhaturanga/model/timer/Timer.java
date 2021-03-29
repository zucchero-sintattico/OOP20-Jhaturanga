package jhaturanga.model.timer;

import java.util.Optional;

import jhaturanga.model.player.Player;

public interface Timer {

    /**
     * get remaining time of the selected player (seconds).
     * 
     * @param player of which you want to know the remaining play time (second)
     * @return the remaining time of a player
     */
    double getRemaningTime(Player player);

    /**
     * starts the timer for the selected player.
     * 
     * @param player which you want starting time
     */
    void start(Player player);

    /**
     * 
     * stop all timer's players.
     */
    void stop();

    /**
     * it returns false only if no timer of any player is active, else true.
     * 
     * @return timer status
     */
    boolean isRunning();

    /**
     * stop the timer of the current player, and start the timer of the selected
     * player.
     * 
     * @param player change the turn on player timer
     */
    void switchPlayer(Player player);

    /**
     * true if the timer can be changed after it has been created, else false.
     * 
     * @return true if is modifiable, else false
     */
    boolean isModifiable();

    /**
     * Set Timer modifiable. If true, the timer can be changed after it has been
     * created.
     * 
     * @param modifiable
     */
    void setModifiable(boolean modifiable);

    /**
     * Used to set the increment of a modifiable Timer.
     * 
     * @param increment
     */
    void setIncrement(int increment);

    /**
     * @return Optional<Integer> containing the optional increment
     */
    int getIncrement();

    /**
     * 
     * @param player of which you want add time
     * @param second which you want add
     * @return true if the operation is possible, else false
     */
    boolean updatePlayerTime(Player player, double second);

    /**
     * 
     * @param player  of which you want give time
     * @param seconds which you want give
     * @return true if the operation is possible, else false
     */
    boolean addTimeToPlayer(Player player, double seconds);

    /**
     * 
     * @return players with have finish time;
     */
    Optional<Player> getPlayerWithoutTime();

}
