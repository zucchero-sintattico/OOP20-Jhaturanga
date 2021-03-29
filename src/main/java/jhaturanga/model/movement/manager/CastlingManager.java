package jhaturanga.model.movement.manager;

import jhaturanga.model.movement.Movement;

public interface CastlingManager {
    /**
     * Use this method to check whether the passed movement can be considered a
     * castling movement.
     * 
     * @param movement - the movement onto check if it can considered a Castling.
     */
    void checkAndExecuteCastling(Movement movement);

    /**
     * Use this method to check if the passed castling movement is correct.
     * 
     * @param movement - the movement to check if is a correct Castle.
     * @return true if it's a correct castling.
     */
    boolean isCastlingFullyCorrect(Movement movement);

    /**
     * Use this method to check if the passed movement is a possible castling.
     * 
     * @param movement - the movement to check if is a Castle.
     * @return true if it's a Castle.
     */
    boolean mightItBeCastle(Movement movement);

}
