package jhaturanga.controllers.match;

import java.util.function.Consumer;

public interface OnlineMatchController extends MatchController {

    String create();

    void join(String gameID);

    void setOnMovementHandler(Consumer<MovementResult> onMovementHandler);

    boolean isWhitePlayer();

}
