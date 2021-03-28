package jhaturanga.commons.network;

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
     * Send a move.
     */
    MOVE;

}