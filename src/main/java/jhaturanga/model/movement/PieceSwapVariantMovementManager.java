package jhaturanga.model.movement;

import java.util.Map;

import jhaturanga.controllers.game.ActionType;
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
    public final ActionType move(final Movement movement) {
        if (!this.getActualPlayersTurn().equals(movement.getPieceInvolved().getPlayer())) {
            return ActionType.NONE;
        }
        // Check if the movement is possible watching only in moves that don't put the
        // player under check.
        if (this.filterOnPossibleMovesBasedOnGameController(movement).contains(movement.getDestination())) {
            // Remove the piece in destination position, if present
            boolean captured = false;
            if (this.getGameController().boardState().getPieceAtPosition(movement.getDestination()).isPresent()) {
                captured = true;
            }
            this.getGameController().boardState().removeAtPosition(movement.getDestination());
            movement.execute();
            this.swapPieceType(movement);
            this.conditionalPawnUpgrade(movement);
            this.setActualPlayersTurn(this.getPlayerTurnIterator().next());
            return this.resultingActionTypeFromMovement(captured);
        }
        return ActionType.NONE;
    }

    private void swapPieceType(final Movement movement) {
        if (this.pieceTypeSwapper.containsKey(movement.getPieceInvolved().getType())) {
            this.getGameController().boardState().remove(movement.getPieceInvolved());
            this.getGameController().boardState()
                    .add(new PieceImpl(this.pieceTypeSwapper.get(movement.getPieceInvolved().getType()),
                            movement.getPieceInvolved().getPiecePosition(), movement.getPieceInvolved().getPlayer()));
        }
    }
}
