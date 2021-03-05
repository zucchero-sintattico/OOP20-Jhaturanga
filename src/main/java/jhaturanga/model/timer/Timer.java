package jhaturanga.model.timer;

import java.util.Optional;

import jhaturanga.model.player.Player;

public interface Timer{
    /**
     * 
     * @param player of which you want to know the remaining play time (second)
     * @return the remaining time of a player
     */
    int getRemaningTime(Player player);

    /**
     * 
     * @param player which you want starting time
     */
    void start(Player player);

    /**
     * @param player change the turn on player timer
     */
    void switchPlayer(Player player);

    /**
     * 
     * @return true if is modifiable, else false
     */
    boolean isModifiable();

    /**
     * Sets the Timer to be or not modifiable.
     * 
     * @param modifiable
     */
    void setModifiable(boolean modifiable);

    /**
     * Used to set the increment of a modifiable Timer.
     * 
     * @param increment
     */
    void setIncrement(Optional<Integer> increment);

    /**
     * @return Optional<Integer> containing the optional increment
     */
    Optional<Integer> getIncrement();

    /**
     * 
     * @param player of which you want add time
     * @param second which you want add
     * @return true if the operation is possible, else false
     */
    boolean updatePlayerTime(Player player, int second);

    /**
     * 
     * @param player  of which you want give time
     * @param seconds which you want give
     * @return true if the operation is possible, else false
     */
    boolean addTimeToPlayer(Player player, int seconds);

    /**
     * 
     * @return players with have finish time;
     */
    Optional<Player> getPlayerWithoutTime();
}
