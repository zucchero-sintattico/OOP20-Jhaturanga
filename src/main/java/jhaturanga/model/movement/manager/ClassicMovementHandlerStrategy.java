package jhaturanga.model.movement.manager;

import java.util.Set;

import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.movement.PieceMovementImpl;
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
    public boolean isMovementPossible(final PieceMovement movement) {
        return this.possibleDestinations(movement.getPieceInvolved()).contains(movement.getDestination());
    }

    @Override
    public Set<BoardPosition> possibleDestinations(final Piece piece) {
        return StreamEx
                .of(this.pieceMovementStrategies.getPieceMovementStrategy(piece)
                        .getPossibleMoves(this.gameController.getBoard()))
                .map(pos -> new PieceMovementImpl(piece, piece.getPiecePosition(), pos))
                .filter(this::arePreliminarChecksOnCastlingValid).filter(this.gameController::wouldNotBeInCheck)
                .map(PieceMovement::getDestination).toSet();
    }

    private boolean arePreliminarChecksOnCastlingValid(final PieceMovement movement) {
        return !this.castlingManager.mightItBeCastle(movement) || this.castlingManager.isCastlingFullyCorrect(movement);
    }

}
