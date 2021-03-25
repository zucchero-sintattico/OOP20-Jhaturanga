package jhaturanga.model.movement;

import java.util.Random;
import java.util.function.Supplier;

import jhaturanga.commons.TriPredicate;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.GameController;
import jhaturanga.model.piece.PieceType;

public class BombVariantMovementManager extends ClassicMovementManager {

    private static final int RANGE_RATIO = 2;
    private static final int MIN_RANGE = 2;

    private final Supplier<Integer> randomRangeGenerator = () -> new Random()
            .ints(MIN_RANGE, Math.min(this.getGameController().boardState().getRows(),
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
            this.handleMovementSideEffects(movement, captured);
            return super.resultingMovementResult(captured);
        }
        return MovementResult.INVALID_MOVE;
    }

    private void handleMovementSideEffects(final Movement movement, final boolean captured) {
        if (captured) {
            super.getGameController().boardState().removeAtPosition(movement.getDestination());
            this.bombMightExplode(movement);
        }
        movement.execute();
        movement.getPieceInvolved().hasMoved(true);
        super.conditionalPawnUpgrade(movement);
        super.setActualPlayersTurn(super.getPlayerTurnIterator().next());
    }

    private void bombMightExplode(final Movement movement) {
        final int range = this.randomRangeGenerator.get();
        if (this.furtherRandomCheckBeforeExplosion()) {
            super.getGameController().boardState().getPiecesStatus().stream()
                    .filter(piece -> this.inRandomRange.test(piece.getPiecePosition(), movement.getDestination(),
                            range))
                    .filter(piece -> !piece.getType().equals(PieceType.KING))
                    .forEach(pieceToRemove -> super.getGameController().boardState().remove(pieceToRemove));
        }
    }

    private boolean furtherRandomCheckBeforeExplosion() {
        return this.randomRangeGenerator.get().intValue() % new Random().ints(1, 2).findAny().getAsInt() == 0;
    }

}
