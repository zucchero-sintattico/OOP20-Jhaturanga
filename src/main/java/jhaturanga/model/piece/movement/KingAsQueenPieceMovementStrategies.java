package jhaturanga.model.piece.movement;

import jhaturanga.model.piece.Piece;

public class KingAsQueenPieceMovementStrategies extends ClassicNoCastlingPieceMovementStrategies {

    /**
     * This method is used to get the movement strategy of a King. It's specific of
     * the kind of variant and GameType.
     */
    @Override
    protected MovementStrategy getKingMovementStrategy(final Piece piece) {
        return this.getQueenMovementStrategy(piece);
    }
}
