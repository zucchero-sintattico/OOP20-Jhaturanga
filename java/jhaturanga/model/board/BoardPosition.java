package jhaturanga.model.board;

public interface BoardPosition {
    /**
     * @return the X position of the Piece on the Board
     * 
     */
    int getX();

    /**
     * @return the Y position of the Piece on the Board
     * 
     */
    int getY();

    /**
     * @param xNewPos is the X position to which set the Piece's new X position
     * 
     */
    void setX(int xNewPos);

    /**
     * @param yNewPos is the Y position to which set the Piece's new Y position
     * 
     */
    void setY(int yNewPos);
}
