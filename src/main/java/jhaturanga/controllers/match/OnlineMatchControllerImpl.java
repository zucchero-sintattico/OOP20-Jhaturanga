package jhaturanga.controllers.match;

import java.util.function.Consumer;

import jhaturanga.model.match.NetworkMatch;

public final class OnlineMatchControllerImpl extends MatchControllerImpl implements OnlineMatchController {

    @Override
    public String create() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void join(final String gameID) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setOnMovementHandler(final Consumer<MovementResult> onMovementHandler) {
        final NetworkMatch netMatch = (NetworkMatch) this.getModel().getActualMatch().get();
        netMatch.setOnMovementHandler(onMovementHandler);

    }

    @Override
    public boolean isWhitePlayer() {
        final NetworkMatch netMatch = (NetworkMatch) this.getModel().getActualMatch().get();
        return netMatch.isWhitePlayer();
    }

}
