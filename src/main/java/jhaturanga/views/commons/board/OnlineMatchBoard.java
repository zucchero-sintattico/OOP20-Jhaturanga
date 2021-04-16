package jhaturanga.views.commons.board;

import java.util.Optional;

import javafx.application.Platform;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.views.commons.board.strategy.history.HistoryNavigationController;
import jhaturanga.views.commons.board.strategy.history.NormalHistoryKeyHandlerStrategy;
import jhaturanga.views.commons.board.strategy.movement.OnlineMatchPieceMovementStrategy;

/**
 * The graphical board for playing an online match.
 */
public final class OnlineMatchBoard extends MatchBoard {

    private final MatchController controller;
    private final boolean isWhite;

    public OnlineMatchBoard(final MatchController controller, final Runnable onMatchFinish, final boolean isWhite) {
        super(controller, onMatchFinish);
        this.isWhite = isWhite;
        this.controller = controller;
        this.setGraphicPieceMovementStrategy(new OnlineMatchPieceMovementStrategy(this, this.controller, isWhite));
        this.setHistoryKeyHandlerStrategy(new NormalHistoryKeyHandlerStrategy(this, new HistoryNavigationController() {

            @Override
            public Optional<Board> getPreviousBoard() {
                return controller.getPreviousBoard();
            }

            @Override
            public Optional<Board> getNextBoard() {
                return controller.getNextBoard();
            }
        }));
        this.createBoard();
        this.redraw(this.controller.getBoard());
        Platform.runLater(() -> this.getGrid().requestFocus());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BoardPosition getGridPositionFromBoardPosition(final BoardPosition position) {
        final BoardPosition pos = super.getGridPositionFromBoardPosition(position);
        return this.isWhite ? pos
                : new BoardPositionImpl(pos.getX(), this.controller.getBoard().getRows() - 1 - pos.getY());
    }

}
