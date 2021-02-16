package jhaturanga.model.game;

import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.movement.ClassicMovementManager;
import jhaturanga.model.piece.movement.PawnVariantPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.startingboards.ClassicStartingBoard;

public class PawnMovementVariantGameType extends BaseGameType {
    public PawnMovementVariantGameType(final Player whitePlayer, final Player blackPlayer) {
        this(new PawnVariantPieceMovementStrategyFactory(),
                ClassicStartingBoard.createStartingBoard(whitePlayer, blackPlayer), List.of(whitePlayer, blackPlayer));
    }

    private PawnMovementVariantGameType(final PieceMovementStrategyFactory pieceMovementStrategyFactory,
            final Board startingBoard, final List<Player> players) {
        this(pieceMovementStrategyFactory, startingBoard,
                new ClassicGameController(startingBoard, pieceMovementStrategyFactory, players));
    }

    private PawnMovementVariantGameType(final PieceMovementStrategyFactory pieceMovementStrategyFactory,
            final Board startingBoard, final GameController gameController) {
        super(pieceMovementStrategyFactory, startingBoard, gameController,
                new ClassicMovementManager(startingBoard, pieceMovementStrategyFactory, gameController),
                "Pawn Variant GameType");
    }
}
