package jhaturanga.model.game;

import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.movement.ClassicMovementManager;
import jhaturanga.model.piece.movement.ClassicPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.startingboards.PawnVsClassicStartingBoard;

public class PawnHordeVariantGameType extends BaseGameType {

    public PawnHordeVariantGameType(final Player whitePlayer, final Player blackPlayer) {
        this(new ClassicPieceMovementStrategyFactory(),
                PawnVsClassicStartingBoard.createStartingBoard(whitePlayer, blackPlayer),
                List.of(whitePlayer, blackPlayer));
    }

    private PawnHordeVariantGameType(final PieceMovementStrategyFactory pieceMovementStrategyFactory,
            final Board startingBoard, final List<Player> players) {
        this(pieceMovementStrategyFactory, startingBoard,
                new ClassicGameController(startingBoard, pieceMovementStrategyFactory, players));
    }

    private PawnHordeVariantGameType(final PieceMovementStrategyFactory pieceMovementStrategyFactory,
            final Board startingBoard, final GameController gameController) {
        super(pieceMovementStrategyFactory, startingBoard, gameController,
                new ClassicMovementManager(startingBoard, pieceMovementStrategyFactory, gameController),
                "Pawn Horde GameType");
    }

}
