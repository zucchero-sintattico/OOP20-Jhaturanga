package jhaturanga.controllers.match;

public interface OnlineMatchController extends MatchController {

    String create();

    void join(String gameID);

    void setOnMovementHandler(Runnable onMovementHandler);

    boolean isWhitePlayer();

}
