package jhaturanga.model.game.gametypes;

import jhaturanga.model.game.GameController;
import jhaturanga.model.movement.manager.MovementManager;

public final class GameTypeBuilderImpl implements GameTypeBuilder {

    private GameTypesEnum type;
    private GameController gameController;
    private MovementManager movementManager;

    @Override
    public GameTypeBuilderImpl type(final GameTypesEnum type) {
        this.type = type;
        return this;
    }

    @Override
    public GameTypeBuilderImpl gameController(final GameController gameController) {
        this.gameController = gameController;
        return this;
    }

    @Override
    public GameTypeBuilderImpl movementManager(final MovementManager movementManager) {
        this.movementManager = movementManager;
        return this;
    }

    @Override
    public GameType build() {
        return new GameTypeImpl(this.type, this.gameController, this.movementManager);
    }

}
