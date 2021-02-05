package jhaturanga.model.piecemanagament;

import jhaturanga.model.piece.Piece;

public interface PieceMovementStrategyFactory {

    /**
     * @param piece is the piece of which we want to have it's MovementStrategy
     * @return the PieceMovementStrategy of the Pawn
     */
    PieceMovementStrategy getPieceMovementStrategy(Piece piece);

}
