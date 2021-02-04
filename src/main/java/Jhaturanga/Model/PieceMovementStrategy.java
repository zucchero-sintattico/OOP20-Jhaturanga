package Jhaturanga.Model;

import java.util.Set;
/**
 * @author Stefano Scolari
 * 
 * */
@FunctionalInterface
public interface PieceMovementStrategy {
	
	/**
	 * 
	 * @return a set of Movements of the Piece
	 * */
	Set<Movement> getPossibleMoves();
}
