package jhaturanga.commons.graphics;

import javafx.scene.input.MouseEvent;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.views.editor.PieceRectangleImpl;

public final class OnlineMatchPieceMovementStrategy extends NormalMatchPieceMovementStrategy {

    private final MatchBoard board;
    private final boolean isWhite;

    public OnlineMatchPieceMovementStrategy(final MatchBoard board, final boolean isWhite) {
        super(board);
        this.isWhite = isWhite;
        this.board = board;
    }

    @Override
    public void onPieceClicked(final MouseEvent event) {
        final PieceRectangleImpl piece = (PieceRectangleImpl) event.getSource();
        if (!this.isLocalPlayerPiece(piece.getPiece())) {
            System.out.println("NO PLAYERRRRR");
            return;
        }
        super.onPieceClicked(event);
    }

    @Override
    public void onPieceDragged(final MouseEvent event) {
        final PieceRectangleImpl piece = (PieceRectangleImpl) event.getSource();
        if (!this.isLocalPlayerPiece(piece.getPiece())) {
            return;
        }
        super.onPieceDragged(event);
    }

    @Override
    public void onPieceReleased(final MouseEvent event) {
        final PieceRectangleImpl piece = (PieceRectangleImpl) event.getSource();
        if (!this.isLocalPlayerPiece(piece.getPiece())) {
            return;
        }
        super.onPieceReleased(event);
    }

    private boolean isLocalPlayerPiece(final Piece piece) {
        return this.isWhite ? this.board.getMatchController().getWhitePlayer().equals(piece.getPlayer())
                : this.board.getMatchController().getBlackPlayer().equals(piece.getPlayer());
    }

    @Override
    public BoardPosition getBoardPositionsFromGridCoordinates(final double x, final double y) {
        final BoardPosition position = super.getBoardPositionsFromGridCoordinates(x, y);
        return this.isWhite ? position
                : new BoardPositionImpl(position.getX(),
                        this.board.getMatchController().getBoardStatus().getRows() - 1 - position.getY());
    }

    @Override
    public BoardPosition getGridCoordinateFromBoardPosition(final BoardPosition position) {
        final BoardPosition pos = super.getGridCoordinateFromBoardPosition(position);
        return this.isWhite ? pos
                : new BoardPositionImpl(pos.getX(),
                        this.board.getMatchController().getBoardStatus().getRows() - 1 - pos.getY());
    }

}
