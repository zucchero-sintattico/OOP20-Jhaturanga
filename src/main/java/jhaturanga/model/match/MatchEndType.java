package jhaturanga.model.match;

/**
 * The type of end of a Match.
 */
public enum MatchEndType {

    /**
     * A player lost for timeout.
     */
    TIMEOUT,

    /**
     * A player won for checkmate.
     */
    CHECKMATE,

    /**
     * The match ended with a draw.
     */
    DRAW,

    /**
     * A player resigned.
     */
    RESIGN

}
