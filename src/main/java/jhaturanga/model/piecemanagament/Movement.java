package jhaturanga.model.piecemanagament;


import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public interface Movement {
    /**
     * @return a set of the Pieces involved in the movement(we need to return a Set
     *         because a Movement may apply a positional change to more than one
     *         Piece on the Board)
     */
    Piece getPieceInvolved();

    /**
     * @return a Map containing as Keys the Piece's involved and as Value it's
     *         destination BoardPosition
     */
    BoardPosition getDestination();
}
