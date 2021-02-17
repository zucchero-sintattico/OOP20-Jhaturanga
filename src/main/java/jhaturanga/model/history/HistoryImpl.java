package jhaturanga.model.history;

import java.util.ArrayList;
import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceImpl;

public class HistoryImpl implements History {

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

        return boardBuilder.build();
    }

    @Override
    public final void addMoveToHistory(final Movement movement) {

        this.movements.add(movement);

        // Create a clone of the last board
        final Board board = this.cloneBoard(this.status.get(this.status.size() - 1));

        // Get the piece at the origin position and change it
        final Piece piece = board.getPieceAtPosition(movement.getOrigin()).get();
        piece.setPosition(movement.getDestination());

        this.status.add(board);
    }

    @Override
    public final Board getBoardAtIndex(final int index) {
        return this.status.get(index);
    }

}
