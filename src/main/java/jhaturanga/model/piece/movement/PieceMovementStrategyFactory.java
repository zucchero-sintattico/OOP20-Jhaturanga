package jhaturanga.model.piece.movement;

import jhaturanga.model.piece.Piece;

public interface PieceMovementStrategyFactory {

    /**
     * @param piece is the piece of which we want to have it's MovementStrategy.
     * @return the PieceMovementStrategy of the passed piece.
     */
    PieceMovementStrategy getPieceMovementStrategy(Piece piece);

    /**
     * 
     * @param canCastle parameter used to set if the PieceMovementStrategyFactory
     *                  has to consider castling or not
     */
    void setCanCastle(boolean canCastle);

    /**
     * 
     * @return true if the PieceMovementStrategy accepts castling, false otherwise.
     */
    boolean canCastle();
}
