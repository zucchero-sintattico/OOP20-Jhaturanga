package jhaturanga.model.movement.manager;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.movement.BasicMovement;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;

public final class ChessProblemsMovementManager implements MovementManager {

    private final ClassicMovementManager classicMovementManager;
    private final ListIterator<BasicMovement> problemCorrectMovesIterator;

    public ChessProblemsMovementManager(final GameController gameController,
            final List<BasicMovement> problemCorrectMoves) {
        this.classicMovementManager = new ClassicMovementManager(gameController);
        this.problemCorrectMovesIterator = problemCorrectMoves.listIterator();
    }

    @Override
    public MovementResult move(final PieceMovement movement) {
        if (this.problemCorrectMovesIterator.hasNext() && this.isMovementCorrectBasedOnProblemSequence(movement)) {
            final MovementResult movementResult = this.classicMovementManager.move(movement);
            this.executeOpponentNextMoveIfPresent();
            return movementResult;
        }
        return MovementResult.INVALID_MOVE;
    }

    private boolean isMovementCorrectBasedOnProblemSequence(final PieceMovement movement) {
        final BasicMovement moveToWhichCompareUsersMovement = this.problemCorrectMovesIterator.next();
        if (movement.getDestination().equals(moveToWhichCompareUsersMovement.getDestination())
                && movement.getOrigin().equals(moveToWhichCompareUsersMovement.getOrigin())) {
            return true;
        }
        this.problemCorrectMovesIterator.previous();
        return false;
    }

    private void executeOpponentNextMoveIfPresent() {
        if (this.problemCorrectMovesIterator.hasNext()) {
            final BasicMovement blackMovement = this.problemCorrectMovesIterator.next();
            final Piece pieceToMove = this.classicMovementManager.getGameController().getBoard()
                    .getPieceAtPosition(blackMovement.getOrigin()).get();
            this.classicMovementManager.getGameController().getBoard()
                    .removeAtPosition(blackMovement.getDestination());
            pieceToMove.setPosition(blackMovement.getDestination());
            this.classicMovementManager
                    .setActualPlayersTurn(this.classicMovementManager.getPlayerTurnIterator().next());
        }
    }

    @Override
    public Player getPlayerTurn() {
        return this.classicMovementManager.getPlayerTurn();
    }

    @Override
    public Set<BoardPosition> filterOnPossibleMovesBasedOnGameController(final Piece piece) {
        return this.classicMovementManager.filterOnPossibleMovesBasedOnGameController(piece);
    }

}
