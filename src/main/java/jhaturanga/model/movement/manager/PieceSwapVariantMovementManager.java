package jhaturanga.model.movement.manager;

import java.util.Map;

import jhaturanga.model.game.GameController;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceImpl;
import jhaturanga.model.piece.PieceType;

public class PieceSwapVariantMovementManager extends ClassicMovementManager {

    private final Map<PieceType, PieceType> pieceTypeSwapper = Map.of(PieceType.BISHOP, PieceType.KNIGHT,
            PieceType.KNIGHT, PieceType.ROOK, PieceType.ROOK, PieceType.BISHOP);

    public PieceSwapVariantMovementManager(final GameController gameController) {
        super(gameController);
    }

    @Override
    public final MovementResult move(final Movement movement) {
        if (super.isItThisPlayersTurn(movement) && super.getMovementHandlerStrategy().isMovementPossible(movement)) {
            // Remove the piece in destination position, if present
            final boolean hasCaptured = super.getGameController().boardState()
                    .getPieceAtPosition(movement.getDestination()).isPresent();
            this.handleMovementSideEffects(movement);
            super.setActualPlayersTurn(super.getPlayerTurnIterator().next());
            return super.resultingMovement(hasCaptured);
        }
        return MovementResult.INVALID_MOVE;
    }

    private void handleMovementSideEffects(final Movement movement) {
        super.getGameController().boardState().removeAtPosition(movement.getDestination());
        movement.execute();
        this.swapPieceType(movement);
        super.conditionalPawnUpgrade(movement);
        movement.getPieceInvolved().hasMoved(true);
    }

    private void swapPieceType(final Movement movement) {
        if (this.isThePieceMovedSwappable(movement)) {
            this.swapPieceToNextOrderedType(movement);
        }
    }

    private boolean isThePieceMovedSwappable(final Movement movement) {
        return this.pieceTypeSwapper.containsKey(movement.getPieceInvolved().getType());
    }

    private void swapPieceToNextOrderedType(final Movement movement) {
        super.getGameController().boardState().remove(movement.getPieceInvolved());
        super.getGameController().boardState().add(this.getNewSwappedPiece(movement));
    }

    private Piece getNewSwappedPiece(final Movement movement) {
        return new PieceImpl(this.pieceTypeSwapper.get(movement.getPieceInvolved().getType()),
                movement.getPieceInvolved().getPiecePosition(), movement.getPieceInvolved().getPlayer());
    }

}
