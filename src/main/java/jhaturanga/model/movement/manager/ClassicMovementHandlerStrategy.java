package jhaturanga.model.movement.manager;

import java.util.Set;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.movement.PieceMovementStrategies;
import one.util.streamex.StreamEx;

public final class ClassicMovementHandlerStrategy implements MovementHandlerStrategy {

    private final GameController gameController;
    private final PieceMovementStrategies pieceMovementStrategies;
    private final CastlingManager castlingManager;

    public ClassicMovementHandlerStrategy(final GameController gameController) {
        this.gameController = gameController;
        this.castlingManager = new CastlingManagerImpl(this.gameController);
        this.pieceMovementStrategies = gameController.getPieceMovementStrategies();
    }

    @Override
    public boolean isMovementPossible(final Movement movement) {
        return this.filterOnPossibleMovesBasedOnGameController(movement.getPieceInvolved())
                .contains(movement.getDestination());
    }

    @Override
    public Set<BoardPosition> filterOnPossibleMovesBasedOnGameController(final Piece piece) {
        return StreamEx
                .of(this.pieceMovementStrategies.getPieceMovementStrategy(piece)
                        .getPossibleMoves(this.gameController.boardState()))
                .map(pos -> new MovementImpl(piece, piece.getPiecePosition(), pos))
                .filter(this::arePreliminarChecksOnCastlingValid).filter(this.gameController::wouldNotBeInCheck)
                .map(Movement::getDestination).toSet();
    }

    private boolean arePreliminarChecksOnCastlingValid(final Movement movement) {
        return !this.castlingManager.mightItBeCastle(movement) || this.castlingManager.isCastlingFullyCorrect(movement);
    }

}
