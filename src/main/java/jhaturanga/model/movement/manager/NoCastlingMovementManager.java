package jhaturanga.model.movement.manager;

import jhaturanga.model.game.GameController;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementResult;

public class NoCastlingMovementManager extends ClassicMovementManager {

    public NoCastlingMovementManager(final GameController gameController) {
        super(gameController);
    }

    @Override
    public final MovementResult move(final Movement movement) {
        if (!this.getPlayerTurn().equals(movement.getPieceInvolved().getPlayer())) {
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
            super.conditionalPawnUpgrade(movement);
            super.setActualPlayersTurn(this.getPlayerTurnIterator().next());
            movement.getPieceInvolved().hasMoved(true);
            return super.resultingMovementResult(captured);
        }
        return MovementResult.INVALID_MOVE;
    }

}
