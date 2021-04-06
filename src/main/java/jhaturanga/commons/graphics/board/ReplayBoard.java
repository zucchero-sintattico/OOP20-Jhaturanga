package jhaturanga.commons.graphics.board;

import javafx.application.Platform;
import jhaturanga.commons.graphics.strategy.history.NormalHistoryKeyHandlerStrategy;
import jhaturanga.commons.graphics.strategy.movement.NonMovableGraphicPieceMovementStrategy;
import jhaturanga.controllers.replay.ReplayController;

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
