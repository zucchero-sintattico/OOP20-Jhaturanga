package jhaturanga.model.game;

import jhaturanga.model.board.Board;
import jhaturanga.model.movement.MovementManager;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;

/**
 * A generic type of game.
 */
public interface GameType {

    /**
     * Get the name of this game type.
     * 
     * @return the game's name.
     */
    String getGameName();

    /**
     * Get the game controller for this game type.
     * 
     * @return the game controller.
     */
    GameController getGameController();

    /**
     * Get the starting board of this game type.
     * 
     * @return the starting board.
     */
    Board getStartingBoard();

    /**
     * Get the pieces movement strategy factory for this game type.
     * 
     * @return the piece movement strategy factory
     */
    PieceMovementStrategyFactory getPieceMovementStrategyFactory();

    /**
     * Get the GameType's specific MovementManager.
     * 
     * @return MovementManager is the GameType's specific movementManager
     */
    MovementManager getMovementManager();
}
