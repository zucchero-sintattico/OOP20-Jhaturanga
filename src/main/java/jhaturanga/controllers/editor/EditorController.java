package jhaturanga.controllers.editor;

import jhaturanga.controllers.setup.SetupController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public interface EditorController extends SetupController {

    /**
     * Use this method to add a Piece to the board.
     * 
     * @param piece to add to the board from the view.
     */
    void addPieceToBoard(Piece piece);

    /**
     * Get the actual board status.
     * 
     * @return the status of the most recent board
     */
    Board getBoardStatus();

    /**
     * Use this method to create the starting board and save it in the Model.
     */
    void createCustomizedStartingBoard();

    /**
     * Use this method to change the position of a Piece.
     * 
     * @param piece    the piece of which to change the position.
     * @param position where to move the Piece.
     * @return true if the position change was successful.
     */
    boolean updatePiecePosition(Piece piece, BoardPosition position);

    /**
     * Use this method to remove a piece in a certain position from the board.
     * 
     * @param position of the piece to remove.
     */
    void removePieceAtPosition(BoardPosition position);

    /**
     * Reset the starting board size.
     * 
     * @param columns number of columns
     * @param rows    number of rows
     */
    void resetBoard(int columns, int rows);

}
