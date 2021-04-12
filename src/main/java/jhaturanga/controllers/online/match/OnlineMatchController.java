package jhaturanga.controllers.online.match;

import java.util.function.BiConsumer;

import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.player.Player;

/**
 * The controller for the online match page view.
 *
 */
public interface OnlineMatchController extends MatchController {

    /**
     * Set the callback to trigger when the other player resigned the game.
     * 
     * @param onResign - the on resign callback
     */
    void setOnResignHandler(Runnable onResign);

    /**
     * Set the callback to trigger when the other player make a movement.
     * 
     * @param onMovementHandler - the on movement callback
     */
    void setOnMovementHandler(BiConsumer<PieceMovement, MovementResult> onMovementHandler);

    /**
     * Get the local player.
     * 
     * @return the local player.
     */
    Player getLocalPlayer();

    /**
     * Check if the local player is the white one.
     * 
     * @return true if the local player is white, false otherwise.
     */
    boolean isWhitePlayer();

}
