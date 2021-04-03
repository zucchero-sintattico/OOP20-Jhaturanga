package jhaturanga.model.movement.manager;

import java.util.Random;
import java.util.function.Supplier;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.PieceType;

public class BombVariantMovementManager extends ClassicMovementManager {

    private static final int RANGE_RATIO = 2;
    private static final int MIN_RANGE = 1;

    private final Supplier<Integer> randomRangeGenerator = () -> new Random()
            .ints(MIN_RANGE, Math.min(this.getGameController().boardState().getRows(),
                    this.getGameController().boardState().getColumns()) / RANGE_RATIO)
            .findFirst().getAsInt();

    private final Random rnd = new Random();

    public BombVariantMovementManager(final GameController gameController) {
        super(gameController);
    }

    @Override
    public final MovementResult move(final PieceMovement movement) {
        if (super.isItThisPlayersTurn(movement) && super.getMovementHandlerStrategy().isMovementPossible(movement)) {
            // Remove the piece in destination position, if present
            final boolean captured = super.getGameController().boardState()
                    .getPieceAtPosition(movement.getDestination()).isPresent();
            this.handleMovementSideEffects(movement, captured);
            return super.resultingMovement(captured);
        }
        return MovementResult.INVALID_MOVE;
    }

    private void handleMovementSideEffects(final PieceMovement movement, final boolean captured) {
        if (captured) {
            super.getGameController().boardState().removeAtPosition(movement.getDestination());
            this.bombMightExplode(movement);
        }
        movement.execute();
        movement.getPieceInvolved().hasMoved(true);
        super.conditionalPawnUpgrade(movement);
        super.setActualPlayersTurn(super.getPlayerTurnIterator().next());
    }

    private boolean shouldExplode() {
        return this.rnd.nextBoolean();
    }

    private void bombMightExplode(final PieceMovement movement) {
        if (this.shouldExplode()) {
            final int range = this.randomRangeGenerator.get();
            super.getGameController().boardState().getPiecesStatus().stream()
                    .filter(i -> this.isBoardPositionInExplosionBlastRange(i.getPiecePosition(),
                            movement.getDestination(), range))
                    .filter(i -> !i.getType().equals(PieceType.KING))
                    .forEach(pieceToRemove -> super.getGameController().boardState().remove(pieceToRemove));
        }
    }

    private boolean isBoardPositionInExplosionBlastRange(final BoardPosition source, final BoardPosition destination,
            final int range) {
        return Math.abs(source.getX() - destination.getX()) <= range
                && Math.abs(source.getY() - destination.getY()) <= range;
    }

}
