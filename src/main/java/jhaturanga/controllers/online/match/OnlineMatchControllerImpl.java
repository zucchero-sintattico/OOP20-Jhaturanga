package jhaturanga.controllers.online.match;

import java.util.Optional;
import java.util.function.BiConsumer;

import jhaturanga.controllers.match.MatchControllerImpl;
import jhaturanga.model.board.Board;
import jhaturanga.model.match.online.OnlineMatch;
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
        final OnlineMatch netMatch = (OnlineMatch) this.getApplicationInstance().getMatch().get();
        netMatch.setOnMovementHandler((mov, res) -> {
            this.setHistoryIndexToLastBoard();
            onMovementHandler.accept(mov, res);
        });

    }

    @Override
    public boolean isWhitePlayer() {
        final OnlineMatch netMatch = (OnlineMatch) this.getApplicationInstance().getMatch().get();
        return netMatch.isWhitePlayer();
    }

    @Override
    public void deleteMatch() {
        final OnlineMatch netMatch = (OnlineMatch) this.getApplicationInstance().getMatch().get();
        netMatch.exit();
        super.deleteMatch();
    }

}
