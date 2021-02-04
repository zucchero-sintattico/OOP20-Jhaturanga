package jhaturanga.model.piecemanagament;

import java.util.Set;

/**
 * @author Stefano Scolari
 * 
 */

@FunctionalInterface
public interface PieceMovementStrategy {

	/**
	 * Used to get what are the possibile moves
	 * 
	 * @return a set of Movements of the Piece
	 */
	Set<Movement> getPossibleMoves();
}
