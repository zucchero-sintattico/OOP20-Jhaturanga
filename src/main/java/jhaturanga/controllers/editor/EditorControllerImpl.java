package jhaturanga.controllers.editor;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.editor.Editor;
import jhaturanga.model.editor.EditorImpl;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.piece.Piece;

public class EditorControllerImpl extends AbstractController implements EditorController {

    private final Editor editor = new EditorImpl();

    @Override
    public final void addPieceToBoard(final Piece piece) {
        this.editor.addPieceToBoard(piece);
    }

    @Override
    public final Board getBoardStatus() {
        return this.editor.getBoardStatus();
    }

    @Override
    public final void resetBoard(final int columns, final int rows) {
        this.editor.changeBoardDimensions(columns, rows);
    }

    @Override
    public final boolean updatePiecePosition(final Piece piece, final BoardPosition position) {
        return this.editor.changePiecePosition(piece, position);
    }

    @Override
    public final void removePieceAtPosition(final BoardPosition position) {
        this.editor.removePiece(position);
    }

    @Override
    public final void createCustomizedStartingBoard() {
        this.editor.createStartingBoard();
        this.getModel().setDynamicGameTypeStartingBoard(this.editor.getCreatedBoard().get());
        this.getModel().setGameType(GameTypesEnum.CUSTOM_BOARD_VARIANT);
    }

}
