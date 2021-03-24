package jhaturanga.model.movement;

import java.util.List;
import java.util.Set;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.GameController;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;

public final class ChessProblemsMovementManagerDecorator implements MovementManager {

    private final ClassicMovementManager classicMovementManager;
    private final List<BasicMovement> problemCorrectMoves;
    private int moveIndex;

    public ChessProblemsMovementManagerDecorator(final GameController gameController,
            final List<BasicMovement> problemCorrectMoves) {
        this.moveIndex = 0;
        this.classicMovementManager = new ClassicMovementManager(gameController);
        this.problemCorrectMoves = problemCorrectMoves;
    }

    @Override
    public MovementResult move(final Movement movement) {
        if (this.isTheMovementIndexFeasable()
                && this.isMovementCorrect(this.problemCorrectMoves.get(this.moveIndex), movement)) {
            this.moveIndex++;
            final MovementResult res = this.classicMovementManager.move(movement);
            this.executeOpponentNextMoveIfPresent();
            return res;
        } else {
            return MovementResult.INVALID_MOVE;
        }
    }

    private boolean isTheMovementIndexFeasable() {
        return this.moveIndex < this.problemCorrectMoves.size();
    }

    private boolean isMovementCorrect(final BasicMovement mov1, final Movement mov2) {
        return mov1.getDestination().equals(mov2.getDestination()) && mov1.getOrigin().equals(mov2.getOrigin());
    }

    private void executeOpponentNextMoveIfPresent() {
        if (this.moveIndex < this.problemCorrectMoves.size()) {
            final BoardPosition origin = this.problemCorrectMoves.get(this.moveIndex).getOrigin();
            final BoardPosition destination = this.problemCorrectMoves.get(this.moveIndex).getDestination();
            final Piece pieceToMove = this.classicMovementManager.getGameController().boardState()
                    .getPieceAtPosition(origin).get();
            this.classicMovementManager.getGameController().boardState().removeAtPosition(destination);
            pieceToMove.setPosition(destination);
            this.classicMovementManager
                    .setActualPlayersTurn(this.classicMovementManager.getPlayerTurnIterator().next());
            this.moveIndex++;
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
