package jhaturanga.model.piece.movement;

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
     * @param piece - the pawn
     * @return the pawn movement strategy
     */
    PieceMovementStrategy getPawnMovementStrategy(Piece piece);

    /**
     * Get the movement strategy for the rook.
     * 
     * @param piece - the rook
     * @return the rook movement strategy
     */
    PieceMovementStrategy getRookMovementStrategy(Piece piece);

    /**
     * Get the movement strategy for the knight.
     * 
     * @param piece - the knight.
     * @return the knight movement strategy
     */
    PieceMovementStrategy getKnightMovementStrategy(Piece piece);

    /**
     * Get the movement strategy for the bishop.
     * 
     * @param piece - the bishop
     * @return the bishop movement strategy
     */
    PieceMovementStrategy getBishopMovementStrategy(Piece piece);

    /**
     * Get the movement strategy for the queen.
     * 
     * @param piece - the queen
     * @return the queen movement strategy
     */
    PieceMovementStrategy getQueenMovementStrategy(Piece piece);

    /**
     * Get the movement strategy for the king.
     * 
     * @param piece - the king
     * @return the king movement strategy
     */
    PieceMovementStrategy getKingMovementStrategy(Piece piece);

}
