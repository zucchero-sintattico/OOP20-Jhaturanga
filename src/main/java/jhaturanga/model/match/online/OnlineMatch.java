package jhaturanga.model.match.online;

import jhaturanga.model.game.type.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.player.Player;

/**
 * The Online Match Interface.
 */
public interface OnlineMatch extends Match {

    /**
     * Join to an existing online match with the specified ID.
     * 
     * @param matchID
     */
    void join(String matchID);

    /**
     * Create an online match.
     * 
     * @param type - the game type
     * @return the match id
     */
    String create(GameType type);

    /**
     * 
     * @param onResign
     */
    void setOnResign(Runnable onResign);

    /**
     * Set the handler for when a movement is made.
     * 
     * @param onMovementHandler - the callback
     */
    void setOnMovementHandler(MovementHandler onMovementHandler);

    /**
     * Exit the match.
     */
    void exit();

    /**
     * 
     * @return the local player
     */
    Player getLocalPlayer();

    /**
     * 
     * @return true if the local player is the white one, false if it's black
     */
    boolean isWhitePlayer();

}
