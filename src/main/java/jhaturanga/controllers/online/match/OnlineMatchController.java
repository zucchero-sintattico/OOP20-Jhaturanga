package jhaturanga.controllers.online.match;

import java.util.function.Consumer;

import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.movement.MovementResult;

public interface OnlineMatchController extends MatchController {

    String create();

    void join(String gameID);

    void setOnMovementHandler(Consumer<MovementResult> onMovementHandler);

    boolean isWhitePlayer();

}
