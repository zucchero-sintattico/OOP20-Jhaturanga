package jhaturanga.model.movement;

import java.util.HashSet;
import java.util.Set;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.GameController;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;

public class NormalMovementManager implements MovementManager {

    private final Board board;
    private final PieceMovementStrategyFactory pieceMovementStrategies;
    private final GameController gameController;

    public NormalMovementManager(final Board startingBoard, final PieceMovementStrategyFactory pieceMovementStrategies,
            final GameController gameController) {
        this.board = startingBoard;
        this.pieceMovementStrategies = pieceMovementStrategies;
        this.gameController = gameController;
    }

    @Override
    public final boolean move(final Movement movement) {
        if (this.filterOnPossibleMovesBasedOnGameController(movement).contains(movement.getDestination())) {
            movement.execute();
            return true;
        }
        return false;
    }

    private Set<BoardPosition> filterOnPossibleMovesBasedOnGameController(final Movement movement) {
        final Piece pieceInvolved = movement.getPieceInvolved();
        // TODO: add overrided constructor from BoardPosition
        final BoardPosition oldPosition = new BoardPositionImpl(pieceInvolved.getPiecePosition().getX(),
                pieceInvolved.getPiecePosition().getY());
        final Set<BoardPosition> positions = pieceMovementStrategies.getPieceMovementStrategy(pieceInvolved)
                .getPossibleMoves(board);
        final Set<BoardPosition> result = new HashSet<>();

        positions.forEach(x -> {
            pieceInvolved.setPosition(x);
            if (!this.gameController.isInCheck(pieceInvolved.getPlayer())) {
                result.add(x);
            }
            pieceInvolved.setPosition(oldPosition);
        });

        return result;
    }

}
