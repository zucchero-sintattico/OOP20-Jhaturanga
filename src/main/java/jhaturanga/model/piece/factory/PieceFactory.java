package jhaturanga.model.piece.factory;

import java.io.Serializable;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public interface PieceFactory extends Serializable {

    /**
     * @return a Piece of type Pawn
     * @param piecePosition is the position at which to create the Piece
     */
    Piece getPawn(BoardPosition piecePosition);

    /**
     * @return a Piece of type King
     * @param piecePosition is the position at which to create the Piece
     */
    Piece getKing(BoardPosition piecePosition);

    /**
     * @return a Piece of type Queen
     * @param piecePosition is the position at which to create the Piece
     */
    Piece getQueen(BoardPosition piecePosition);

    /**
     * @return a Piece of type Bishop
     * @param piecePosition is the position at which to create the Piece
     */
    Piece getBishop(BoardPosition piecePosition);

    /**
     * @return a Piece of type Knight
     * @param piecePosition is the position at which to create the Piece
     */
    Piece getKnight(BoardPosition piecePosition);

    /**
     * @return a Piece of type Rook
     * @param piecePosition is the position at which to create the Piece
     */
    Piece getRook(BoardPosition piecePosition);
}
