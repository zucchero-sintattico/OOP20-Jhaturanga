package jhaturanga.model.game;

import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.movement.manager.MovementManager;

/**
 * A builder for the game entity.
 */
public interface GameBuilder {

    /**
     * Sets the GameType.
     * 
     * @param type the type of the game
     * @return this
     */
    GameBuilder type(GameType type);

    /**
     * Sets the GameController.
     * 
     * @param gameController the gameController of the Game being created
     * @return this
     */
    GameBuilder gameController(GameController gameController);

    /**
     * Sets the MovemementManager.
     * 
     * @param movementManager the MovementManager of the Game being created
     * @return this
     */

    GameBuilder movementManager(MovementManager movementManager);

    /**
     * Used to create the Game instance.
     * 
     * @return the created Game.
     */
    Game build();
}
