package jhaturanga.model.game;

public enum MatchStatusEnum {
    /**
     * Is returned when the game finished for checkmate.
     */
    CHECKMATE,
    /**
     * Is returned when the game finished by draw.
     */
    DRAW,
    /**
     * Is returned when the game finished for end of time.
     */
    ENDED_FOR_TIME,

    /**
     * Is returned when the game did not finish.
     */
    ACTIVE;

}
