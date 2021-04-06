package jhaturanga.model.game;

import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.movement.manager.MovementManager;

public final class GameBuilderImpl implements GameBuilder {

    private GameType type;
    private GameController gameController;
    private MovementManager movementManager;

    @Override
    public GameBuilder type(final GameType type) {
        this.type = type;
        return this;
    }

    @Override
    public GameBuilder gameController(final GameController gameController) {
        this.gameController = gameController;
        return this;
    }

    @Override
    public GameBuilder movementManager(final MovementManager movementManager) {
        this.movementManager = movementManager;
        return this;
    }

    @Override
    public Game build() {
        return new GameImpl(this.type, this.gameController, this.movementManager);
    }

}
