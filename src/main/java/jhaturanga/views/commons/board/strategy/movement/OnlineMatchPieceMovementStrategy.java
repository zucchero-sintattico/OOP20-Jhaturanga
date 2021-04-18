package jhaturanga.views.commons.board.strategy.movement;

import javafx.scene.input.MouseEvent;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.views.commons.board.MatchBoard;
import jhaturanga.views.commons.component.PieceRectangle;

/**
 * The GraphicPieceMovementStrategy for an online match that forbid to move
 * other player's pieces.
 */
public final class OnlineMatchPieceMovementStrategy extends NormalMatchPieceMovementStrategy {

    private final boolean isWhite;

    public OnlineMatchPieceMovementStrategy(final MatchBoard board, final MatchController controller,
            final boolean isWhite) {
        super(board, controller);
        this.isWhite = isWhite;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPiecePressed(final MouseEvent event) {
        final PieceRectangle piece = (PieceRectangle) event.getSource();
        if (!this.isLocalPlayerPiece(piece.getPiece())) {
            System.out.println("NO PLAYER");
            return;
        }
        super.onPiecePressed(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPieceDragged(final MouseEvent event) {
        final PieceRectangle piece = (PieceRectangle) event.getSource();
        if (!this.isLocalPlayerPiece(piece.getPiece())) {
            return;
        }
        super.onPieceDragged(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPieceReleased(final MouseEvent event) {
        final PieceRectangle piece = (PieceRectangle) event.getSource();
        if (!this.isLocalPlayerPiece(piece.getPiece())) {
            return;
        }
        super.onPieceReleased(event);
    }

    private boolean isLocalPlayerPiece(final Piece piece) {
        return this.isWhite ? this.getMatchController().getWhitePlayer().equals(piece.getPlayer())
                : this.getMatchController().getBlackPlayer().equals(piece.getPlayer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoardPosition getBoardPositionsFromGridCoordinates(final double x, final double y) {
        final BoardPosition position = super.getBoardPositionsFromGridCoordinates(x, y);
        return this.isWhite ? position
                : new BoardPositionImpl(position.getX(),
                        this.getMatchController().getBoard().getRows() - 1 - position.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoardPosition getGridCoordinateFromBoardPosition(final BoardPosition position) {
        final BoardPosition pos = super.getGridCoordinateFromBoardPosition(position);
        return this.isWhite ? pos
                : new BoardPositionImpl(pos.getX(), this.getMatchController().getBoard().getRows() - 1 - pos.getY());
    }

}
