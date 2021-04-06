package jhaturanga.controllers.online.match;

import java.util.function.BiConsumer;

import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.player.Player;

public interface OnlineMatchController extends MatchController {

    void setOnResignHandler(Runnable onResign);

    void setOnMovementHandler(BiConsumer<PieceMovement, MovementResult> onMovementHandler);

    Player getLocalPlayer();

    boolean isWhitePlayer();

}
