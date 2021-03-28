package jhaturanga.controllers.online.match;

import java.util.function.BiConsumer;

import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementResult;

public interface OnlineMatchController extends MatchController {

    String create();

    void join(String gameID);

    void setOnMovementHandler(BiConsumer<Movement, MovementResult> onMovementHandler);

    boolean isWhitePlayer();

}
