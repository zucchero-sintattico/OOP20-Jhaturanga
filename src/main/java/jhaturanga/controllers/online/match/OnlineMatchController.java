package jhaturanga.controllers.online.match;

import java.util.function.BiConsumer;

import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.movement.MovementResult;

public interface OnlineMatchController extends MatchController {

    void setOnMovementHandler(BiConsumer<PieceMovement, MovementResult> onMovementHandler);

    boolean isWhitePlayer();

}
