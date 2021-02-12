package jhaturanga.model.timer;

import jhaturanga.model.player.Player;

public interface Timer {
    /**
     * 
     * @param player of which you want to know the remaining play time
     * @return the remaining time of a player
     */
    int getRemaningTime(Player player);

    /**
     * 
     * @param player which you want starting time
     */
    void start(Player player);

    void switchPlayer();

    boolean isMoficicable();

    boolean updatePlayerTieme(Player player, int second);

    void addTimeToPlayer(Player player, int seconds);
}
