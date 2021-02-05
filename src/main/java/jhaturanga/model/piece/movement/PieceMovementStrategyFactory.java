package jhaturanga.model.piece.movement;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public interface PieceMovementStrategyFactory {

    /**
     * @param piece is the piece of which we want to have it's MovementStrategy
     * @return the PieceMovementStrategy of the Pawn
     */
    PieceMovementStrategy getPieceMovementStrategy(Piece piece);

    /**
     * Get the movement strategy for the pawn.
     * 
     * @param boardPosition - the position of the pawn
     * @return the pawn movement strategy
     */
    PieceMovementStrategy getPawnMovementStrategy(BoardPosition boardPosition);

    /**
     * Get the movement strategy for the rook.
     * 
     * @param boardPosition - the position of the rook
     * @return the rook movement strategy
     */
    PieceMovementStrategy getRookMovementStrategy(BoardPosition boardPosition);

    /**
     * Get the movement strategy for the knight.
     * 
     * @param piecePosition - the position of the knight.
     * @return the knight movement strategy
     */
    PieceMovementStrategy getKnightMovementStrategy(BoardPosition piecePosition);

    /**
     * Get the movement strategy for the bishop.
     * 
     * @param piecePosition - the position of the bishop
     * @return the bishop movement strategy
     */
    PieceMovementStrategy getBishopMovementStrategy(BoardPosition piecePosition);

    /**
     * Get the movement strategy for the queen.
     * 
     * @param piecePosition - the position of the queen
     * @return the queen movement strategy
     */
    PieceMovementStrategy getQueenMovementStrategy(BoardPosition piecePosition);

    /**
     * Get the movement strategy for the king.
     * 
     * @param piecePosition - the position of the king
     * @return the king movement strategy
     */
    PieceMovementStrategy getKingMovementStrategy(BoardPosition piecePosition);

}
