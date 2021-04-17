package jhaturanga.model.game;

import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.movement.manager.MovementManager;

public final class GameBuilderImpl implements GameBuilder {

    private GameType type;
    private GameController gameController;
    private MovementManager movementManager;
    private boolean built;

    /**
     * {@inheritDoc}
     */
    @Override
    public GameBuilder type(final GameType type) {
        this.type = type;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameBuilder gameController(final GameController gameController) {
        this.gameController = gameController;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameBuilder movementManager(final MovementManager movementManager) {
        this.movementManager = movementManager;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game build() {
        if (this.built) {
            throw new IllegalStateException("Alredy Built");
        }
        if (this.type == null || this.gameController == null || this.movementManager == null) {
            throw new IllegalStateException("All fields must be setted");
        }
        this.built = true;
        return new GameImpl(this.type, this.gameController, this.movementManager);
    }

}
