package jhaturanga.views.commons.board;

import javafx.application.Platform;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.views.commons.board.strategy.history.NormalHistoryKeyHandlerStrategy;
import jhaturanga.views.commons.board.strategy.movement.OnlineMatchPieceMovementStrategy;

public final class OnlineMatchBoard extends MatchBoard {

    private final MatchController controller;
    private final boolean isWhite;

    public OnlineMatchBoard(final MatchController controller, final Runnable onMatchFinish, final boolean isWhite) {
        super(controller, onMatchFinish);
        this.isWhite = isWhite;
        this.controller = controller;
        this.setGraphicPieceMovementStrategy(new OnlineMatchPieceMovementStrategy(this, this.controller, isWhite));
        this.setHistoryKeyHandlerStrategy(new NormalHistoryKeyHandlerStrategy(this, this.controller));
        this.createBoard();
        this.redraw(this.controller.getBoard());
        Platform.runLater(() -> this.getGrid().requestFocus());
    }

    @Override
    protected BoardPosition getGridPositionFromBoardPosition(final BoardPosition position) {
        final BoardPosition pos = super.getGridPositionFromBoardPosition(position);
        return this.isWhite ? pos
                : new BoardPositionImpl(pos.getX(), this.controller.getBoard().getRows() - 1 - pos.getY());
    }

}
