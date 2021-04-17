package jhaturanga.model.movement.manager;

import java.util.Map;
import java.util.function.UnaryOperator;

import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceImpl;
import jhaturanga.model.piece.PieceType;

public final class PieceSwapVariantMovementManager extends ClassicMovementManager {

    private final Map<PieceType, PieceType> pieceTypeSwappingOrder = Map.of(PieceType.BISHOP, PieceType.KNIGHT,
            PieceType.KNIGHT, PieceType.ROOK, PieceType.ROOK, PieceType.BISHOP);

    private final UnaryOperator<Piece> fromPieceToSwappedPiece = (piece) -> new PieceImpl(
            this.pieceTypeSwappingOrder.get(piece.getType()), piece.getPiecePosition(), piece.getPlayer());

    public PieceSwapVariantMovementManager(final GameController gameController) {
        super(gameController);
    }

    @Override
    public MovementResult move(final PieceMovement movement) {
        if (super.isItThisPlayersTurn(movement) && super.getMovementHandlerStrategy().isMovementPossible(movement)) {
            // Remove the piece in destination position, if present
            final boolean hasCaptured = super.getGameController().getBoard()
                    .getPieceAtPosition(movement.getDestination()).isPresent();
            this.handleMovementSideEffects(movement);
            super.setActualPlayersTurn(super.getPlayerTurnIterator().next());
            return super.resultingMovement(hasCaptured);
        }
        return MovementResult.INVALID_MOVE;
    }

    private void handleMovementSideEffects(final PieceMovement movement) {
        super.getGameController().getBoard().removeAtPosition(movement.getDestination());
        movement.execute();
        this.swapPieceType(movement.getPieceInvolved());
        super.conditionalPawnUpgrade(movement);
    }

    private void swapPieceType(final Piece piece) {
        if (this.isThePieceMovedSwappable(piece)) {
            this.swapPieceToNextOrderedType(piece);
        }
    }

    private boolean isThePieceMovedSwappable(final Piece piece) {
        return this.pieceTypeSwappingOrder.containsKey(piece.getType());
    }

    private void swapPieceToNextOrderedType(final Piece piece) {
        super.getGameController().getBoard().remove(piece);
        super.getGameController().getBoard().add(this.getNewSwappedPiece(piece));
    }

    private Piece getNewSwappedPiece(final Piece piece) {
        return this.fromPieceToSwappedPiece.apply(piece);
    }

}
