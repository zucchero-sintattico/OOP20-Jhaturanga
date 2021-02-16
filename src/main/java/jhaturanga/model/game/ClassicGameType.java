package jhaturanga.model.game;

import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.movement.ClassicMovementManager;
import jhaturanga.model.piece.movement.ClassicPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.startingboards.ClassicStartingBoard;

public class ClassicGameType extends BaseGameType {

    public ClassicGameType(final Player whitePlayer, final Player blackPlayer) {
        this(new ClassicPieceMovementStrategyFactory(), ClassicStartingBoard.createStartingBoard(whitePlayer, blackPlayer),
                List.of(whitePlayer, blackPlayer));
    }

    private ClassicGameType(final PieceMovementStrategyFactory pieceMovementStrategyFactory, final Board startingBoard,
            final List<Player> players) {
        this(pieceMovementStrategyFactory, startingBoard,
                new ClassicGameController(startingBoard, pieceMovementStrategyFactory, players));
    }

    private ClassicGameType(final PieceMovementStrategyFactory pieceMovementStrategyFactory, final Board startingBoard,
            final GameController gameController) {
        super(pieceMovementStrategyFactory, startingBoard, gameController,
                new ClassicMovementManager(startingBoard, pieceMovementStrategyFactory, gameController), "Classic GameType");
    }

}
