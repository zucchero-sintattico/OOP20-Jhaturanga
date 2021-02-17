package jhaturanga.model.game.gametypes;

import java.util.List;

import jhaturanga.model.game.ClassicGameController;
import jhaturanga.model.movement.ClassicMovementManager;
import jhaturanga.model.piece.movement.PawnVariantPieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.startingboards.ClassicStartingBoard;

public class PawnMovementVariantGameType extends BaseGameTypeImpl {

    private static final String GAME_TYPE_NAME = "PawnMovementVariantGameType";

    public PawnMovementVariantGameType(final Player whitePlayer, final Player blackPlayer) {
        this.setGameTypeName(GAME_TYPE_NAME);
        this.setGameController(
                new ClassicGameController(ClassicStartingBoard.createStartingBoard(whitePlayer, blackPlayer),
                        new PawnVariantPieceMovementStrategyFactory(), List.of(whitePlayer, blackPlayer)));
        this.setMovementManager(new ClassicMovementManager(this.getGameController().boardState(),
                this.getGameController().getPieceMovementStrategyFactory(), this.getGameController()));
    }
}
