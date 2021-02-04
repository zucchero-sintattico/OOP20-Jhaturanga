package jhaturanga.model.piecemanagament;
/**
 * @author Stefano Scolari
 * */
public interface BoardPosition {
	/**
	 * @return the X position of the Piece on the Board
	 * 
	 * */
	int getX();
	
	/**
	 * @return the Y position of the Piece on the Board
	 * 
	 * */
	int getY();
	
	/**
	 * @param int is the X position to which set the Piece's new X position
	 * 
	 * */
	int setNewX(int xNewPos);
	
	/**
	 * @param int is the Y position to which set the Piece's new Y position
	 * 
	 * */
	int setNewY(int yNewPos);
}
