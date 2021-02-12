package jhaturanga.model.movement;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.GameController;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;

public class ClassicMovementManager implements MovementManager {

    private final Board board;
    private final PieceMovementStrategyFactory pieceMovementStrategies;
    private final GameController gameController;

    public ClassicMovementManager(final Board startingBoard, final PieceMovementStrategyFactory pieceMovementStrategies,
            final GameController gameController) {
        this.board = startingBoard;
        this.pieceMovementStrategies = pieceMovementStrategies;
        this.gameController = gameController;
    }

    @Override
    public final boolean move(final Movement movement) {
        if (this.filterOnPossibleMovesBasedOnGameController(movement).contains(movement.getDestination())) {
            this.board.removeAtPosition(movement.getDestination());
            movement.execute();
            return true;
        }
        return false;
    }

    private Set<BoardPosition> filterOnPossibleMovesBasedOnGameController(final Movement movement) {
        final Piece pieceInvolved = movement.getPieceInvolved();
        final BoardPosition oldPosition = new BoardPositionImpl(pieceInvolved.getPiecePosition());
        final Set<BoardPosition> positions = this.pieceMovementStrategies.getPieceMovementStrategy(pieceInvolved)
                .getPossibleMoves(this.board);

        final Set<BoardPosition> result = new HashSet<>();

        for (final BoardPosition x : positions) {
            final Optional<Piece> oldPiece = this.board.getPieceAtPosition(x);

            if (oldPiece.isPresent()) {
                System.out.println(oldPiece.get().getIdentifier());
                System.out.println("IN " + x.toString() + " trovato pezzo quindi lo rimuovo");
                this.board.remove(oldPiece.get());
            }
            pieceInvolved.setPosition(x);
            if (!this.gameController.isInCheck(pieceInvolved.getPlayer())) {
                result.add(x);
            }
            pieceInvolved.setPosition(oldPosition);
            if (oldPiece.isPresent()) {
                this.board.add(oldPiece.get());
            }
        }

        return result;
    }

}
