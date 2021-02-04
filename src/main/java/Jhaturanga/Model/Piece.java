package Jhaturanga.Model;
/**
 * @author Stefano
 * */
public interface Piece {
	
	/**
	 * @return the name of the type of this Piece
	 * */
	String getNameType();
	
	/**
	 * @return the unique name of this piece, it can be seen as an ID for the Piece
	 * */
	String getUniqueName();
	
	/**
	 * @param the position to which move the Piece on the Board
	 * 
	 * */
	void move(BoardPosition positionalDestination);
	
	/**
	 * @param the movementStrategy that the Piece uses to Move
	 * 
	 * */
	PieceMovementStrategy getMovementStrategy();
	
	/**
	 * @return the actual Piece's position on the board
	 * 
	 * */
	BoardPosition getPiecePosition();
	
	/**
	 * @return the actual Piece's Player owner
	 * 
	 * */
	Player getPlayer();
	
}
