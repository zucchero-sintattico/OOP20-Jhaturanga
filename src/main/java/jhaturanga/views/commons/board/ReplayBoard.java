package jhaturanga.views.commons.board;

import java.util.Optional;

import javafx.application.Platform;
import jhaturanga.controllers.replay.ReplayController;
import jhaturanga.model.board.Board;
import jhaturanga.views.commons.board.strategy.history.HistoryNavigationController;
import jhaturanga.views.commons.board.strategy.history.NormalHistoryKeyHandlerStrategy;
import jhaturanga.views.commons.board.strategy.movement.NonMovableGraphicPieceMovementStrategy;

/**
 * The graphical board used to replay an old match.
 */
public final class ReplayBoard extends GraphicalBoard {

    public ReplayBoard(final ReplayController replayController) {
        super(replayController.getFirstBoard().getRows(), replayController.getFirstBoard().getColumns(),
                new NonMovableGraphicPieceMovementStrategy());
        this.setHistoryKeyHandlerStrategy(new NormalHistoryKeyHandlerStrategy(this, new HistoryNavigationController() {
            @Override
            public Optional<Board> getPreviousBoard() {
                return replayController.getPreviousBoard();
            }

            @Override
            public Optional<Board> getNextBoard() {
                return replayController.getNextBoard();
            }
        }));
        this.createBoard();
        this.redraw(replayController.getFirstBoard());
        Platform.runLater(() -> this.getGrid().requestFocus());
    }
}
