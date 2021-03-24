package jhaturanga.model.movement;

import java.util.Map;

import jhaturanga.model.game.GameController;
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
            super.getGameController().boardState().removeAtPosition(movement.getDestination());
            movement.execute();
            this.swapPieceType(movement);
            super.conditionalPawnUpgrade(movement);
            movement.getPieceInvolved().hasMoved(true);
            super.setActualPlayersTurn(super.getPlayerTurnIterator().next());
            return super.resultingMovementResult(captured);
        }
        return MovementResult.INVALID_MOVE;
    }

    private void swapPieceType(final Movement movement) {
        if (this.pieceTypeSwapper.containsKey(movement.getPieceInvolved().getType())) {
            super.getGameController().boardState().remove(movement.getPieceInvolved());
            super.getGameController().boardState()
                    .add(new PieceImpl(this.pieceTypeSwapper.get(movement.getPieceInvolved().getType()),
                            movement.getPieceInvolved().getPiecePosition(), movement.getPieceInvolved().getPlayer()));
        }
    }
}
