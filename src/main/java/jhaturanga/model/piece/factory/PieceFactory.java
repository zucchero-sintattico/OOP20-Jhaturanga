package jhaturanga.model.piece.factory;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public interface PieceFactory {

    /**
     * @return a Piece of type Pawn
     * 
     */
    Piece getPawn(BoardPosition piecePosition);

    /**
     * @return a Piece of type King
     * 
     */
    Piece getKing(BoardPosition piecePosition);

    /**
     * @return a Piece of type Queen
     * 
     */
    Piece getQueen(BoardPosition piecePosition);

    /**
     * @return a Piece of type Bishop
     * 
     */
    Piece getBishop(BoardPosition piecePosition);

    /**
     * @return a Piece of type Knight
     * 
     */
    Piece getKnight(BoardPosition piecePosition);

    /**
     * @return a Piece of type Rook
     * 
     */
    Piece getRook(BoardPosition piecePosition);
}
