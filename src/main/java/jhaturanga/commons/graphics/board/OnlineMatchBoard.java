package jhaturanga.commons.graphics.board;

import javafx.application.Platform;
import jhaturanga.commons.graphics.strategy.history.NormalHistoryKeyHandlerStrategy;
import jhaturanga.commons.graphics.strategy.movement.OnlineMatchPieceMovementStrategy;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.views.match.MatchView;

public final class OnlineMatchBoard extends MatchBoard {

    private final MatchView matchView;
    private final boolean isWhite;

    public OnlineMatchBoard(final MatchView matchView, final Runnable onMatchFinish, final boolean isWhite) {
        super(matchView, onMatchFinish);
        this.isWhite = isWhite;
        this.matchView = matchView;
        this.setPieceMovementStrategy(new OnlineMatchPieceMovementStrategy(this, isWhite));
        this.getGrid().setOnKeyPressed(new NormalHistoryKeyHandlerStrategy(this, this.getMatchController()));
        this.drawBoard();
        this.redraw(this.getMatchController().getBoardStatus());
        Platform.runLater(() -> this.getGrid().requestFocus());
    }

    @Override
    public BoardPosition getGridCoordinateFromBoardPosition(final BoardPosition position) {
        final BoardPosition pos = super.getGridCoordinateFromBoardPosition(position);
        return this.isWhite ? pos
                : new BoardPositionImpl(pos.getX(),
                        this.matchView.getMatchController().getBoardStatus().getRows() - 1 - pos.getY());
    }

}
