package jhaturanga.commons.network;

import jhaturanga.model.movement.Movement;

public interface NetworkManager {

    /**
     * Create a new Match.
     * 
     * @param data - the data of the match
     * @return the ID of the match
     */
    String createMatch(NetworkMatchData data);

    /**
     * Join a match.
     * 
     * @param matchId - the id of the match
     */
    void joinMatch(String matchId);

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
    void sendMove(Movement move);

    /**
     * Get the username of the user who joined.
     * 
     * @return the username
     */
    String getJoinedUserName();

}
