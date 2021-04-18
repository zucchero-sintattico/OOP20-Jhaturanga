package jhaturanga.model.game;

/**
 * The status of the Game.
 */
public enum GameStatus {

    /**
     * Is returned when the game finished for checkmate.
     */
    CHECKMATE,

    /**
     * Is returned when the game finished by draw.
     */
    DRAW,

    /**
     * Is returned when there is a check.
     */
    CHECK,

    /**
     * Is returned when the game did not finish.
     */
    ACTIVE;

}
