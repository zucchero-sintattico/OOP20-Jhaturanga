package jhaturanga.views.replay;

import javafx.application.Platform;
import jhaturanga.commons.graphics.GraphicalBoard;
import jhaturanga.commons.graphics.HistoryKeyHandlerStrategy;
import jhaturanga.commons.graphics.HistoryKeyHandlerStrategyImpl;
import jhaturanga.commons.graphics.NonMovableGraphicPieceMovementStrategy;
import jhaturanga.controllers.replay.ReplayController;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;

public final class ReplayBoard extends GraphicalBoard {

    private final ReplayController replayController;
    private final HistoryKeyHandlerStrategy historyKeyHandlerStrategy;

    public ReplayBoard(final ReplayController replayController) {
        super(replayController.getFirstBoard().getRows(), replayController.getFirstBoard().getColumns(),
                new NonMovableGraphicPieceMovementStrategy());
        this.historyKeyHandlerStrategy = new HistoryKeyHandlerStrategyImpl(this, replayController);
        this.replayController = replayController;
        this.setupHistoryKeysHandler();
        this.drawBoard(replayController.getFirstBoard());
        this.redraw(replayController.getFirstBoard());
        Platform.runLater(() -> this.getGrid().requestFocus());
    }

    /*
     * Setup the handler for history navigation
     */
    private void setupHistoryKeysHandler() {
        this.getGrid().setOnKeyPressed(this.historyKeyHandlerStrategy);
    }

    @Override
    public BoardPosition getGridCoordinateFromBoardPosition(final BoardPosition position) {
        return new BoardPositionImpl(position.getX(),
                this.replayController.getFirstBoard().getRows() - 1 - position.getY());
    }

}
