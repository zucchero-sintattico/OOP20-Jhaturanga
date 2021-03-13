package jhaturanga.controllers.editor;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public class EditorControllerImpl extends AbstractController implements EditorController {

    @Override
    public final void addPieceToBoard(final Piece piece) {
        this.getModel().getEditor().addPieceToBoard(piece);
    }

    @Override
    public final Board getBoardStatus() {
        return this.getModel().getEditor().getBoardStatus();
    }

    @Override
    public final void resetBoard(final int columns, final int rows) {
        this.getModel().getEditor().changeBoardDimensions(columns, rows);
    }

    @Override
    public final boolean changePiecePosition(final Piece piece, final BoardPosition position) {
        return this.getModel().getEditor().changePiecePosition(piece, position);
    }

    @Override
    public final void removePieceAtPosition(final BoardPosition position) {
        this.getModel().getEditor().removePiece(position);
    }

    @Override
    public final void createCustomizedStartingBoard() {
        this.getModel().getEditor().createStartingBoard();
    }

}
