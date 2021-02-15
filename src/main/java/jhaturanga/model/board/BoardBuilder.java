package jhaturanga.model.board;

import jhaturanga.model.piece.Piece;

public interface BoardBuilder {
    /**
     * Used to set the columns numbers of the board to create.
     * 
     * @param rows sets the number of rows of the starting board
     * @return this
     */
    BoardBuilder rows(int rows);

    /**
     * Used to set the columns numbers of the board to create.
     * 
     * @param columns sets the number of rows of the starting board
     * @return this
     */
    BoardBuilder columns(int columns);

    /**
     * Used to gradually add pieces to the starting board.
     * 
     * @param piece is the piece to add to the starting board
     * @return this
     */
    BoardBuilder addPiece(Piece piece);

    /**
     * 
     * @return Board built
     */
    Board build();
}
