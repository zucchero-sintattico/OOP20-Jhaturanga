package jhaturanga.model.game.gametypes;

import jhaturanga.model.game.GameController;
import jhaturanga.model.movement.MovementManager;

public class GameTypeBuilderImpl implements GameTypeBuilder {
    private GameController gameController;
    private String gameTypeName;
    private MovementManager movementManager;

    @Override
    public final GameTypeBuilderImpl gameTypeName(final String gameTypeName) {
        this.gameTypeName = gameTypeName;
        return this;
    }

    @Override
    public final GameTypeBuilderImpl gameController(final GameController gameController) {
        this.gameController = gameController;
        return this;
    }

    @Override
    public final GameTypeBuilderImpl movementManager(final MovementManager movementManager) {
        this.movementManager = movementManager;
        return this;
    }

    @Override
    public final GameType build() {
        return new GameTypeImpl(this.gameTypeName, this.gameController, this.movementManager);
    }

}
