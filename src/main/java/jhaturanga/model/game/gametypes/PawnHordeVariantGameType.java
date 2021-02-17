package jhaturanga.model.game.gametypes;

import java.util.List;

import jhaturanga.model.game.ClassicGameController;
import jhaturanga.model.movement.ClassicMovementManager;
import jhaturanga.model.piece.movement.ClassicPieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.startingboards.PawnVsClassicStartingBoard;

public class PawnHordeVariantGameType extends BaseGameTypeImpl {

    private static final String GAME_TYPE_NAME = "PawnHordeMode";

    public PawnHordeVariantGameType(final Player whitePlayer, final Player blackPlayer) {
        this.setGameTypeName(GAME_TYPE_NAME);
        this.setGameController(
                new ClassicGameController(PawnVsClassicStartingBoard.createStartingBoard(whitePlayer, blackPlayer),
                        new ClassicPieceMovementStrategyFactory(), List.of(whitePlayer, blackPlayer)));
        this.setMovementManager(new ClassicMovementManager(this.getGameController().boardState(),
                this.getGameController().getPieceMovementStrategyFactory(), this.getGameController()));
    }
}
