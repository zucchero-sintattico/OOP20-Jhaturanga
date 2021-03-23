package jhaturanga.model.movement;

import java.util.Set;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;

public interface MovementManager {

    /**
     * 
     * @param movement is the move to be executed on the Board
     * @return ActionType representing the type of action resulted from the action
     *         performed
     */
    MovementResult move(Movement movement);

    /**
     * Used to get the Player who's turn it is.
     * 
     * @return the Player who's turn it is
     */
    Player getPlayerTurn();

    /**
     * Get the passed Piece possible BoardPositions where to move.
     * 
     * @param piece
     * @return Set<BoardPosition> representing the BoardPositions where the selected
     *         Piece can Move
     */
    Set<BoardPosition> filterOnPossibleMovesBasedOnGameController(Piece piece);

}
