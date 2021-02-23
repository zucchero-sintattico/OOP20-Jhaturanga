package jhaturanga.model.history;

import java.util.ArrayList;
import java.util.List;

import jhaturanga.commons.Pair;
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

    private static final int WHITE_ROOK_ROW = 0;
    private static final int BLACK_ROOK_ROW = 7;
    private static final Pair<BoardPosition, BoardPosition> WHITE_LEFT_ROOK_ORIG_DEST = new Pair<>(
            new BoardPositionImpl(0, WHITE_ROOK_ROW), new BoardPositionImpl(3, WHITE_ROOK_ROW));

    private static final Pair<BoardPosition, BoardPosition> WHITE_RIGHT_ROOK_ORIG_DEST = new Pair<>(
            new BoardPositionImpl(7, WHITE_ROOK_ROW), new BoardPositionImpl(5, WHITE_ROOK_ROW));

    private static final Pair<BoardPosition, BoardPosition> BLACK_LEFT_ROOK_ORIG_DEST = new Pair<>(
            new BoardPositionImpl(0, BLACK_ROOK_ROW), new BoardPositionImpl(3, BLACK_ROOK_ROW));

    private static final Pair<BoardPosition, BoardPosition> BLACK_RIGHT_ROOL_ORIG_DEST = new Pair<>(
            new BoardPositionImpl(7, BLACK_ROOK_ROW), new BoardPositionImpl(5, BLACK_ROOK_ROW));

    private final List<Movement> movements = new ArrayList<>();
    private final List<Board> status = new ArrayList<>();

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

        if (this.isCastle(movement)) {
            Piece rook;
            if (this.isLeftCastle(movement)) {
                if (movement.getPieceInvolved().getPlayer().getColor().equals(PlayerColor.WHITE)) {
                    rook = board.getPieceAtPosition(WHITE_LEFT_ROOK_ORIG_DEST.getX()).get();
                    rook.setPosition(WHITE_LEFT_ROOK_ORIG_DEST.getY());
                } else {
                    rook = board.getPieceAtPosition(BLACK_LEFT_ROOK_ORIG_DEST.getX()).get();
                    rook.setPosition(BLACK_LEFT_ROOK_ORIG_DEST.getY());
                }

            } else {
                if (movement.getPieceInvolved().getPlayer().getColor().equals(PlayerColor.WHITE)) {
                    rook = board.getPieceAtPosition(WHITE_RIGHT_ROOK_ORIG_DEST.getX()).get();
                    rook.setPosition(WHITE_RIGHT_ROOK_ORIG_DEST.getY());
                } else {
                    rook = board.getPieceAtPosition(BLACK_RIGHT_ROOL_ORIG_DEST.getX()).get();
                    rook.setPosition(BLACK_RIGHT_ROOL_ORIG_DEST.getY());
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
