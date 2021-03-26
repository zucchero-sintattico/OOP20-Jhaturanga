package jhaturanga.model.editor;

import java.util.Optional;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public interface Editor {

    /**
     * Use this method to add a Piece to the board.
     * 
     * @param piece to add to the board from the view.
     */
    void addPieceToBoard(Piece piece);

    /**
     * Use this method to get the CreatedBoard once you finished editing your
     * customizable Board if present.
     * 
     * @return Optional<StringBoard> - representing the created board as a
     *         StringBoard.
     */
    Optional<StringBoard> getCreatedBoard();

    /**
     * Use this method to remove a Piece from the board.
     * 
     * @param position of the piece to remove.
     */
    void removePiece(BoardPosition position);

    /**
     * Use this method to transform a Board in a StringBoard.
     * 
     * @param startingBoard - the Board to transform in StringBoard.
     * @return StringBoard - the starting board represented as a String.
     */
    StringBoard stringBoardFromNormal(Board startingBoard);

    /**
     * Use this method to create the starting board String that will be used by the
     * StartingBoardFactory.
     * 
     */
    void createStartingBoard();

    /**
     * Use this method to change the position of a Piece.
     * 
     * @param piece    the piece of which to change the position.
     * @param position where to move the Piece.
     * @return true if the position change was successful.
     */
    boolean changePiecePosition(Piece piece, BoardPosition position);

    /**
     * Use this method to get the actual status of the board.
     * 
     * @return Board representing the status of the board.
     */
    Board getBoardStatus();

    /**
     * Use this method to change the the current dimensions of the board. Note:
     * calling this method removes also from the board all Pieces previously added.
     * 
     * @param columns the number of columns of the board to be.
     * @param rows    the number of rows of the board to be.
     */
    void changeBoardDimensions(int columns, int rows);
}
