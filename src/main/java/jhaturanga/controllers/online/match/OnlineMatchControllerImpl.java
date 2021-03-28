package jhaturanga.controllers.online.match;

import java.util.function.BiConsumer;

import jhaturanga.controllers.match.MatchControllerImpl;
import jhaturanga.model.match.NetworkMatch;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementResult;

public final class OnlineMatchControllerImpl extends MatchControllerImpl implements OnlineMatchController {

    @Override
    public void setOnMovementHandler(final BiConsumer<Movement, MovementResult> onMovementHandler) {
        final NetworkMatch netMatch = (NetworkMatch) this.getApplicationInstance().getMatch().get();
        netMatch.setOnMovementHandler(onMovementHandler);

    }

    @Override
    public boolean isWhitePlayer() {
        final NetworkMatch netMatch = (NetworkMatch) this.getApplicationInstance().getMatch().get();
        return netMatch.isWhitePlayer();
    }

    @Override
    public void deleteMatch() {
        final NetworkMatch netMatch = (NetworkMatch) this.getApplicationInstance().getMatch().get();
        netMatch.disconnect();
        super.deleteMatch();
    }

}
