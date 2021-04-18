package jhaturanga.model.match.online;

import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovement;

/**
 * The Handler for on online movement from the other player.
 */
@FunctionalInterface
public interface MovementHandler {

    /**
     * Handle the movement.
     * 
     * @param movement
     * @param result
     */
    void handleMovement(PieceMovement movement, MovementResult result);
}
