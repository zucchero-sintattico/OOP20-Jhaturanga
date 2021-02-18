package jhaturanga.model.history;

import java.util.ArrayList;
import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;

public class HistoryImpl implements History {

    private static final int WHITE_ROOK_ROW = 7;
    private static final int BLACK_ROOK_ROW = 0;

    private final List<Movement> movements = new ArrayList<>();
    private final List<Board> status = new ArrayList<>();

    private final BoardPosition whiteLeftRookStartPos = new BoardPositionImpl(0, BLACK_ROOK_ROW);
    private final BoardPosition whiteRightRookStartPos = new BoardPositionImpl(7, BLACK_ROOK_ROW);
    private final BoardPosition whiteCastleLeftRookDest = new BoardPositionImpl(3, BLACK_ROOK_ROW);
    private final BoardPosition whiteCastleRightRookDest = new BoardPositionImpl(5, BLACK_ROOK_ROW);

    private final BoardPosition blackLeftRookStartPos = new BoardPositionImpl(0, WHITE_ROOK_ROW);
    private final BoardPosition blackRightRookStartPos = new BoardPositionImpl(7, WHITE_ROOK_ROW);
    private final BoardPosition blackCastleLeftRookDest = new BoardPositionImpl(3, WHITE_ROOK_ROW);
    private final BoardPosition blackCastleRightRookDest = new BoardPositionImpl(5, WHITE_ROOK_ROW);

    public HistoryImpl(final Board board) {
        this.status.add(this.cloneBoard(board));
    }

    private Board cloneBoard(final Board board) {

        final BoardBuilder boardBuilder = new BoardBuilderImpl();

        board.getBoardState().stream()
                .map(x -> new PieceImpl(x.getType(), new BoardPositionImpl(x.getPiecePosition()), x.getPlayer()))
                .forEach(boardBuilder::addPiece);
        boardBuilder.rows(board.getRows()).columns(board.getColumns());
        return boardBuilder.build();
    }

    @Override
    public final void addMoveToHistory(final Movement movement) {

        // Create a clone of the last board
        final Board board = this.cloneBoard(this.status.get(this.status.size() - 1));
        this.movements.add(movement);

        Piece rook;

        if (this.isCastle(movement)) {
            if (this.isLeftCastle(movement)) {
                if (movement.getPieceInvolved().getPlayer().getColor().equals(PlayerColor.WHITE)) {
                    rook = board.getPieceAtPosition(this.whiteLeftRookStartPos).get();
                    rook.setPosition(this.whiteCastleLeftRookDest);
                } else {
                    rook = board.getPieceAtPosition(this.blackLeftRookStartPos).get();
                    rook.setPosition(this.blackCastleLeftRookDest);
                }

            } else {
                if (movement.getPieceInvolved().getPlayer().getColor().equals(PlayerColor.WHITE)) {
                    rook = board.getPieceAtPosition(this.whiteRightRookStartPos).get();
                    rook.setPosition(this.whiteCastleRightRookDest);
                } else {
                    rook = board.getPieceAtPosition(this.blackRightRookStartPos).get();
                    rook.setPosition(this.blackCastleRightRookDest);
                }
            }
        }

        // Get the piece at the origin position and change it
        final Piece piece = board.getPieceAtPosition(movement.getOrigin()).get();
        piece.setPosition(movement.getDestination());

        this.status.add(board);
    }

    private boolean isCastle(final Movement movement) {
        return movement.getPieceInvolved().getType().equals(PieceType.KING)
                && Math.abs(movement.getOrigin().getX() - movement.getDestination().getX()) == 2;
    }

    private boolean isLeftCastle(final Movement movement) {
        return movement.getOrigin().getX() > movement.getDestination().getX();
    }

    @Override
    public final Board getBoardAtIndex(final int index) {
        return this.status.get(index);
    }

}
