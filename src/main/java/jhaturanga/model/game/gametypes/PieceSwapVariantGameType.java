package jhaturanga.model.game.gametypes;

import java.util.List;

import jhaturanga.model.game.ClassicGameController;
import jhaturanga.model.movement.PieceSwapVariantMovementManager;
import jhaturanga.model.piece.movement.ClassicPieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.startingboards.ClassicStartingBoard;

public class PieceSwapVariantGameType extends BaseGameTypeImpl {
    private static final String GAME_TYPE_NAME = "PieceSwapVariantMode";

    public PieceSwapVariantGameType(final Player whitePlayer, final Player blackPlayer) {
        this.setGameTypeName(GAME_TYPE_NAME);
        this.setGameController(
                new ClassicGameController(ClassicStartingBoard.createStartingBoard(whitePlayer, blackPlayer),
                        new ClassicPieceMovementStrategyFactory(), List.of(whitePlayer, blackPlayer)));
        this.setMovementManager(new PieceSwapVariantMovementManager(this.getGameController().boardState(),
                this.getGameController().getPieceMovementStrategyFactory(), this.getGameController()));
    }
}
