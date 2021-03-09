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
            if (this.getBoard().getPieceAtPosition(movement.getDestination()).isPresent()) {
                captured = true;
            }
            this.getBoard().removeAtPosition(movement.getDestination());
            movement.execute();
            this.swapPieceType(movement);
            this.conditionalPawnUpgrade(movement);
            this.setActualPlayersTurn(this.getPlayerTurnIterator().next());
            if (this.getGameController().isInCheck(this.getActualPlayersTurn())) {
                return ActionType.CHECK;
            } else if (captured) {
                return ActionType.CAPTURE;
            }
            return ActionType.MOVE;
        }
        return ActionType.NONE;
    }

    private void swapPieceType(final Movement movement) {
        if (this.pieceTypeSwapper.containsKey(movement.getPieceInvolved().getType())) {
            this.getBoard().remove(movement.getPieceInvolved());
            this.getBoard().add(new PieceImpl(this.pieceTypeSwapper.get(movement.getPieceInvolved().getType()),
                    movement.getPieceInvolved().getPiecePosition(), movement.getPieceInvolved().getPlayer()));
        }
    }
}
