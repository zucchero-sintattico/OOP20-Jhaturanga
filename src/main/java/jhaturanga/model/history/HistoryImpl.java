package jhaturanga.model.history;

import java.util.ArrayList;
import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.PieceImpl;

public class HistoryImpl implements History {

    private final List<Board> status = new ArrayList<>();
    private final Board actualBoardStatus;

    public HistoryImpl(final Board board) {
        this.actualBoardStatus = board;
        this.addCloneBoardToHistory(this.actualBoardStatus);
    }

    private Board cloneBoard(final Board board) {
        final BoardBuilder boardBuilder = new BoardBuilderImpl();

        board.getPieces().stream()
                .map(x -> new PieceImpl(x.getType(), new BoardPositionImpl(x.getPiecePosition()), x.getPlayer()))
                .forEach(boardBuilder::addPiece);

        return boardBuilder.rows(board.getRows()).columns(board.getColumns()).build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateHistory() {
        this.addCloneBoardToHistory(this.actualBoardStatus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Board getBoardAtIndex(final int index) {
        return this.status.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Board> getAllBoards() {
        return this.status;
    }

    private void addCloneBoardToHistory(final Board toCloneBoard) {
        this.status.add(this.cloneBoard(toCloneBoard));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateWithNewHistory(final List<Board> boardHistory) {
        this.status.clear();
        boardHistory.forEach(x -> {
            this.addCloneBoardToHistory(x);
        });
    }

}
