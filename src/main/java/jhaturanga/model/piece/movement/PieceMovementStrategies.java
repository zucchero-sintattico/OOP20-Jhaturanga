package jhaturanga.model.piece.movement;

import jhaturanga.model.piece.Piece;

public interface PieceMovementStrategies {

    /**
     * @param piece is the piece of which we want to have it's MovementStrategy.
     * @return the PieceMovementStrategy of the passed piece.
     */
    MovementStrategy getPieceMovementStrategy(Piece piece);
}
