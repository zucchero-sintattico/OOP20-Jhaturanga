package jhaturanga.model.match.online.network;

import java.io.Serializable;

/**
 * Type of network messages.
 */
public enum NetworkMessageType implements Serializable {

    /**
     * Join a game.
     */
    JOIN,

    /**
     * Send configuration data.
     * 
     * - GameType - Timer - Player ?
     */
    DATA,

    /**
     * Resing a game.
     */
    RESIGN,

    /**
     * Send a move.
     */
    MOVE;

}
