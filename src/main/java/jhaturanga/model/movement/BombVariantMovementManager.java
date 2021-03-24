package jhaturanga.model.movement;

import java.util.Random;
import java.util.function.Supplier;

import jhaturanga.commons.TriPredicate;
import jhaturanga.controllers.match.MovementResult;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.GameController;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;

public class BombVariantMovementManager extends ClassicMovementManager {

    private static final int RANGE_RATIO = 2;

    private final Supplier<Integer> randomNumGen = () -> new Random()
            .ints(1, Math.min(this.getGameController().boardState().getRows(),
                    this.getGameController().boardState().getColumns()) / RANGE_RATIO)
            .findFirst().getAsInt();

    private final TriPredicate<BoardPosition, BoardPosition, Integer> inRandomRange = (p1, p2,
            range) -> Math.abs(p1.getX() - p2.getX()) <= range && Math.abs(p1.getY() - p2.getY()) <= range;

    public BombVariantMovementManager(final GameController gameController) {
        super(gameController);
    }

    @Override
    public final MovementResult move(final Movement movement) {
        if (!super.getPlayerTurn().equals(movement.getPieceInvolved().getPlayer())) {
            return MovementResult.INVALID_MOVE;
        }
        // Check if the movement is possible watching only in moves that don't put the
        // player under check.
        if (super.filterOnPossibleMovesBasedOnGameController(movement.getPieceInvolved())
                .contains(movement.getDestination())) {
            // Remove the piece in destination position, if present
            final boolean captured = super.getGameController().boardState()
                    .getPieceAtPosition(movement.getDestination()).isPresent();
            if (captured) {
                this.bombMightExplode(movement.getPieceInvolved());
            }
            super.getGameController().boardState().removeAtPosition(movement.getDestination());
            movement.execute();
            super.conditionalPawnUpgrade(movement);
            super.setActualPlayersTurn(super.getPlayerTurnIterator().next());
            movement.getPieceInvolved().hasMoved(true);
            return super.resultingMovementResult(captured);
        }
        return MovementResult.INVALID_MOVE;
    }

    private void bombMightExplode(final Piece piece) {
        final int range = this.randomNumGen.get();
        if (this.randomNumGen.get().intValue() % 2 == 0) {
            super.getGameController().boardState().getBoardState().stream()
                    .filter(i -> this.inRandomRange.test(i.getPiecePosition(), piece.getPiecePosition(), range))
                    .filter(i -> !i.getType().equals(PieceType.KING))
                    .forEach(pieceToRemove -> super.getGameController().boardState().remove(pieceToRemove));
        }
    }

}
