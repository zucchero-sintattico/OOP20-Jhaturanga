package jhaturanga.model.movement;

import jhaturanga.model.board.Board;

public interface MovementManager {

    /**
     * 
     * @param movement is the move to be executed on the Board
     * @return a boolean that tells if it was possible to execute the movement
     */
    boolean move(Movement movement);

    // TODO: REMOVE
    Board getBoard();
}
