package jhaturanga.model.movement;

import jhaturanga.controllers.match.MovementResult;
import jhaturanga.model.game.GameController;

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
        if (this.filterOnPossibleMovesBasedOnGameController(movement.getPieceInvolved())
                .contains(movement.getDestination())) {
            // Remove the piece in destination position, if present
            boolean captured = false;
            if (this.getGameController().boardState().getPieceAtPosition(movement.getDestination()).isPresent()) {
                captured = true;
            }
            this.getGameController().boardState().removeAtPosition(movement.getDestination());
            movement.execute();
            this.conditionalPawnUpgrade(movement);
            this.setActualPlayersTurn(this.getPlayerTurnIterator().next());
            movement.getPieceInvolved().hasMoved(true);
            return this.resultingMovementResult(captured);
        }
        return MovementResult.INVALID_MOVE;
    }

}
