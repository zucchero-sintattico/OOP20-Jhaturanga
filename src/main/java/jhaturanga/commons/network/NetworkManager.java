package jhaturanga.commons.network;

import jhaturanga.model.movement.Movement;

public interface NetworkManager {

    /**
     * Create a new Match.
     * 
     * @return the ID of the match
     */
    String createGame();

    /**
     * Join a match.
     * 
     * @param matchId - the id of the match
     * @return true if the connection to the match was succeded, false otherwise
     */
    boolean joinMatch(String matchId);

    /**
     * Send a move.
     * 
     * @param move - the movement
     */
    void sendMove(Movement move);

}
