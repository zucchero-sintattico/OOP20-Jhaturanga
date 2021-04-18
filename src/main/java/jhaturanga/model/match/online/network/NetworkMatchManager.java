package jhaturanga.model.match.online.network;

import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.player.Player;

/**
 * A Manager for online match that handle the creation of a match, the join of a
 * match and the movement sending.
 */
public interface NetworkMatchManager {

    /**
     * Create a new Match.
     * 
     * @param data    - the data of the match
     * @param onReady - the callback when a user join and the game start
     * @return the ID of the match
     */
    String createMatch(NetworkMatchData data, Runnable onReady);

    /**
     * Join a match.
     * 
     * @param matchId - the id of the match
     * @param player  - the local player
     * @param onReady - the callback when the game start
     */
    void joinMatch(String matchId, Player player, Runnable onReady);

    /**
     * Return the match data.
     * 
     * @return the match data
     */
    NetworkMatchData getMatchData();

    /**
     * Send a move.
     * 
     * @param move - the movement
     */
    void sendMove(PieceMovement move);

    /**
     * Send a resign command.
     */
    void sendResign();

    /**
     * Get the username of the user who joined.
     * 
     * @return the username
     */
    Player getJoinedPlayer();

    /**
     * Disconnect from the server.
     */
    void disconnect();

}
