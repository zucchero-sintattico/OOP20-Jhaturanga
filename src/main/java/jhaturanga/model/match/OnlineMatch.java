package jhaturanga.model.match;

import java.util.function.BiConsumer;

import jhaturanga.model.game.type.GameType;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovement;

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
     * Set the handler for when a movement is made.
     * 
     * @param onMovementHandler - the callback
     */
    void setOnMovementHandler(BiConsumer<PieceMovement, MovementResult> onMovementHandler);

    /**
     * Exit the match.
     */
    void exit();

}
