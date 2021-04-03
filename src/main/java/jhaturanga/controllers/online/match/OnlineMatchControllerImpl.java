package jhaturanga.controllers.online.match;

import java.util.Optional;
import java.util.function.BiConsumer;

import jhaturanga.controllers.match.MatchControllerImpl;
import jhaturanga.model.board.Board;
import jhaturanga.model.match.OnlineMatchImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovement;

public final class OnlineMatchControllerImpl extends MatchControllerImpl implements OnlineMatchController {

    private void setHistoryIndexToLastBoard() {
        Optional<Board> history = this.getNextBoard();
        while (history.isPresent()) {
            history = this.getNextBoard();
        }
    }

    @Override
    public void setOnMovementHandler(final BiConsumer<PieceMovement, MovementResult> onMovementHandler) {
        final OnlineMatchImpl netMatch = (OnlineMatchImpl) this.getApplicationInstance().getMatch().get();
        netMatch.setOnMovementHandler((mov, res) -> {
            this.setHistoryIndexToLastBoard();
            onMovementHandler.accept(mov, res);
        });

    }

    @Override
    public boolean isWhitePlayer() {
        final OnlineMatchImpl netMatch = (OnlineMatchImpl) this.getApplicationInstance().getMatch().get();
        return netMatch.getPlayers().getWhitePlayer().getUser()
                .equals(this.getApplicationInstance().getFirstUser().get());
    }

    @Override
    public void deleteMatch() {
        final OnlineMatchImpl netMatch = (OnlineMatchImpl) this.getApplicationInstance().getMatch().get();
        netMatch.exit();
        super.deleteMatch();
    }

}
