package jhaturanga.views.commons.board;

import javafx.application.Platform;
import jhaturanga.controllers.replay.ReplayController;
import jhaturanga.views.commons.board.strategy.history.NormalHistoryKeyHandlerStrategy;
import jhaturanga.views.commons.board.strategy.movement.NonMovableGraphicPieceMovementStrategy;

public final class ReplayBoard extends GraphicalBoard {

    public ReplayBoard(final ReplayController replayController) {
        super(replayController.getFirstBoard().getRows(), replayController.getFirstBoard().getColumns(),
                new NonMovableGraphicPieceMovementStrategy());
        this.setHistoryKeyHandlerStrategy(new NormalHistoryKeyHandlerStrategy(this, replayController));
        this.createBoard();
        this.redraw(replayController.getFirstBoard());
        Platform.runLater(() -> this.getGrid().requestFocus());
    }
}
