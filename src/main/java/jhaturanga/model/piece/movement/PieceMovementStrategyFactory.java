package jhaturanga.model.piece.movement;

import java.util.Set;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;

public interface PieceMovementStrategyFactory {

    /**
     * @param piece       is the piece of which we want to have it's
     *                    MovementStrategy.
     * @param actualBoard - is the board on which to check the strategy movements.
     * @return the PieceMovementStrategy of the Pawn
     */
    Set<BoardPosition> getPieceMovementsFromStrategy(Board actualBoard, Piece piece);

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
