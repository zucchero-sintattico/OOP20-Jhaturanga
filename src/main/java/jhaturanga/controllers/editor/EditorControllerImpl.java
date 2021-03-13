package jhaturanga.controllers.editor;

import java.util.Set;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.movement.ClassicPieceMovementStrategyFactory;

public class EditorControllerImpl extends AbstractController implements EditorController {

    private Board board;

    @Override
    public final void addPieceToBoard(final Piece piece) {
        this.board.add(piece);
    }

    @Override
    public final Board getBoardStatus() {
        return this.board;
    }

    @Override
    public final Set<BoardPosition> getPiecePossibleMoves(final Piece piece) {
        return new ClassicPieceMovementStrategyFactory().getPieceMovementStrategy(piece).getPossibleMoves(this.board);
    }

    @Override
    public final Board getDefaultStartingBoard() {
        final BoardBuilder boardBuilder = new BoardBuilderImpl();
        this.board = boardBuilder.rows(8).columns(8).build();
        return this.board;
    }

    @Override
    public final void resetBoard(final int columns, final int rows) {
        final BoardBuilder boardBuilder = new BoardBuilderImpl();
        this.board = boardBuilder.rows(rows).columns(columns).build();
    }

}
