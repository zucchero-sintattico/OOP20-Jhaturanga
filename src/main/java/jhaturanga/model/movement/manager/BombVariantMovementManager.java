package jhaturanga.model.movement.manager;

import java.util.Random;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.GameController;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;

public class BombVariantMovementManager extends ClassicMovementManager {

    private static final int RANGE_RATIO = 2;
    private final Random rnd = new Random();

//    private final Supplier<Integer> randomValidBombRangeGenerator = () ->new Random().ints(1, Math.min(this.getGameController().boardState().getRows(),
//            this.getGameController().boardState().getColumns()) / RANGE_RATIO).findFirst().getAsInt();

//    private final TriPredicate<BoardPosition, BoardPosition, Integer> inRandomRange = (p1, p2,
//            range) -> Math.abs(p1.getX() - p2.getX()) <= range && Math.abs(p1.getY() - p2.getY()) <= range;

    public BombVariantMovementManager(final GameController gameController) {
        super(gameController);
    }

    private int getRandomValidBombRange() {
        return this.rnd.ints(1, Math.min(this.getGameController().boardState().getRows(),
                this.getGameController().boardState().getColumns()) / RANGE_RATIO).findFirst().getAsInt();
    }

    private boolean isBoardPositionNearFromRange(final BoardPosition source, final BoardPosition destination,
            final int range) {
        return Math.abs(source.getX() - destination.getX()) <= range
                && Math.abs(source.getY() - destination.getY()) <= range;
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

    private boolean shouldExplode() {
        return this.rnd.nextBoolean();
    }

    private void bombMightExplode(final Piece piece) {

        if (this.shouldExplode()) {
            final int range = this.getRandomValidBombRange();
            super.getGameController().boardState().getBoardState().stream()
                    .filter(i -> this.isBoardPositionNearFromRange(i.getPiecePosition(), piece.getPiecePosition(),
                            range))
                    .filter(i -> !i.getType().equals(PieceType.KING))
                    .forEach(pieceToRemove -> super.getGameController().boardState().remove(pieceToRemove));
        }
    }

}
