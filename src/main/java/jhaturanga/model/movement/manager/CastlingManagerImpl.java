package jhaturanga.model.movement.manager;

import java.util.Optional;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.movement.PieceMovementImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;

public final class CastlingManagerImpl implements CastlingManager {

    private static final int LATERAL_INCREMENT = 1;

    private final GameController gameController;

    public CastlingManagerImpl(final GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void checkAndExecuteCastling(final PieceMovement movement) {
        if (this.isCastlingFullyCorrect(movement)) {
            this.executeCastle(movement);
        }
    }

    @Override
    public boolean mightItBeCastle(final PieceMovement movement) {
        return this.arePositionalConditionsCorrectToCastle(movement);
    }

    @Override
    public boolean isCastlingFullyCorrect(final PieceMovement movement) {
        return this.arePositionalConditionsCorrectToCastle(movement) && this.extraChecksOnCastling(movement);
    }

    private boolean arePositionalConditionsCorrectToCastle(final PieceMovement movement) {
        return movement.getPieceInvolved().getType().equals(PieceType.KING)
                && Math.abs(movement.getOrigin().getX() - movement.getDestination().getX()) == 2
                && movement.getOrigin().getY() == movement.getDestination().getY();
    }

    private boolean extraChecksOnCastling(final PieceMovement movement) {
        return this.isPathToCastleFreeFromCheck(movement)
                && this.getClosestRookInRangeThatHasntMovedYet(movement).isPresent();
    }

    private boolean isPathToCastleFreeFromCheck(final PieceMovement movement) {
        final int increment = movement.getOrigin().getX() > movement.getDestination().getX() ? -LATERAL_INCREMENT
                : LATERAL_INCREMENT;

        final BoardPosition nextToItPos = new BoardPositionImpl(movement.getOrigin().getX() + increment,
                movement.getOrigin().getY());

        return this.gameController
                .wouldNotBeInCheck(new PieceMovementImpl(movement.getPieceInvolved(), movement.getOrigin(), nextToItPos));
    }

    private Optional<Piece> getClosestRookInRangeThatHasntMovedYet(final PieceMovement mov) {
        return this.gameController.boardState().getPiecesStatus().stream()
                .filter(i -> i.getType().equals(PieceType.ROOK))
                .filter(rook -> Math.abs(rook.getPiecePosition().getX() - mov.getDestination().getX()) <= 2
                        && rook.getPiecePosition().getY() == mov.getDestination().getY()
                        && rook.getPlayer().equals(mov.getPieceInvolved().getPlayer()) && !rook.hasAlreadyBeenMoved())
                .findFirst();
    }

    private void executeCastle(final PieceMovement movement) {
        final int increment = movement.getOrigin().getX() > movement.getDestination().getX() ? LATERAL_INCREMENT
                : -LATERAL_INCREMENT;
        this.getClosestRookInRangeThatHasntMovedYet(movement).ifPresent(rook -> {
            rook.setPosition(new BoardPositionImpl(movement.getDestination().getX() + increment,
                    rook.getPiecePosition().getY()));
            movement.execute();
        });

    }

}
