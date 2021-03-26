package jhaturanga.controllers.match;

import java.util.function.Consumer;

import jhaturanga.model.movement.MovementResult;

public interface OnlineMatchController extends MatchController {

    String create();

    void join(String gameID);

    void setOnMovementHandler(Consumer<MovementResult> onMovementHandler);

    boolean isWhitePlayer();

}
