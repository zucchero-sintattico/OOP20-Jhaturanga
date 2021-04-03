package jhaturanga.model.board;

import java.io.Serializable;

public interface BoardPosition extends Serializable {

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

}
