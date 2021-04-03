package jhaturanga.commons.graphics.strategy.movement;

import javafx.scene.input.MouseEvent;
import jhaturanga.commons.graphics.board.MatchBoard;
import jhaturanga.commons.graphics.components.PieceRectangle;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;

public final class OnlineMatchPieceMovementStrategy extends NormalMatchPieceMovementStrategy {

    private final boolean isWhite;

    public OnlineMatchPieceMovementStrategy(final MatchBoard board, final MatchController controller,
            final boolean isWhite) {
        super(board, controller);
        this.isWhite = isWhite;
    }

    @Override
    public void onPiecePressed(final MouseEvent event) {
        final PieceRectangle piece = (PieceRectangle) event.getSource();
        if (!this.isLocalPlayerPiece(piece.getPiece())) {
            System.out.println("NO PLAYER");
            return;
        }
        super.onPiecePressed(event);
    }

    @Override
    public void onPieceDragged(final MouseEvent event) {
        final PieceRectangle piece = (PieceRectangle) event.getSource();
        if (!this.isLocalPlayerPiece(piece.getPiece())) {
            return;
        }
        super.onPieceDragged(event);
    }

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

    @Override
    public BoardPosition getBoardPositionsFromGuiCoordinates(final double x, final double y) {
        final BoardPosition position = super.getBoardPositionsFromGuiCoordinates(x, y);
        return this.isWhite ? position
                : new BoardPositionImpl(position.getX(),
                        this.getMatchController().getBoardStatus().getRows() - 1 - position.getY());
    }

    @Override
    public BoardPosition getGridCoordinateFromBoardPosition(final BoardPosition position) {
        final BoardPosition pos = super.getGridCoordinateFromBoardPosition(position);
        return this.isWhite ? pos
                : new BoardPositionImpl(pos.getX(),
                        this.getMatchController().getBoardStatus().getRows() - 1 - pos.getY());
    }

}
