package jhaturanga.controllers.editor;

import java.util.Set;

import jhaturanga.controllers.Controller;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public interface EditorController extends Controller {
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
     * Get the default starting board.
     * 
     * @return the starting default board
     */
    Board getDefaultStartingBoard();

    /**
     * Reset the starting board size.
     * 
     * @param columns number of columns
     * @param rows    number of rows
     */
    void resetBoard(int columns, int rows);

    /**
     * Get the passed Piece possible BoardPositions where to move. This method is
     * mainly used to graphically represent them.
     * 
     * @param piece
     * @return Set<BoardPosition> representing the BoardPositions where the selected
     *         Piece can Move
     */
    Set<BoardPosition> getPiecePossibleMoves(Piece piece);
}
