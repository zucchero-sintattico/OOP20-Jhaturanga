package jhaturanga.model.game;

import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.movement.manager.MovementManager;

public interface GameBuilder {
    /**
     * Sets the GameTypeName.
     * 
     * @param type the type of the game
     * @return this
     */
    GameBuilderImpl type(GameType type);

    /**
     * Sets the GameController.
     * 
     * @param gameController the gameController of the gameType being created
     * @return this
     */
    GameBuilderImpl gameController(GameController gameController);

    /**
     * Sets the MovemementManager.
     * 
     * @param movementManager the MovementManager of the GameType being created
     * @return this
     */
    GameBuilderImpl movementManager(MovementManager movementManager);

    /**
     * Used to create the GameType.
     * 
     * @return GameType is the GameType created from the builder
     */
    Game build();
}
