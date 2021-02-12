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

        // Save a copy of the piece's position
        final BoardPosition oldPosition = new BoardPositionImpl(pieceInvolved.getPiecePosition());

        // Get all possible moves of the piece
        final Set<BoardPosition> positions = this.pieceMovementStrategies.getPieceMovementStrategy(pieceInvolved)
                .getPossibleMoves(this.board);

        final Set<BoardPosition> result = new HashSet<>();

        positions.forEach(x -> {

            // Try to get the piece in the x position
            final Optional<Piece> oldPiece = this.board.getPieceAtPosition(x);

            // If there is a piece in x position this is a capture move
            if (oldPiece.isPresent()) {
                try {
                    this.board.remove(oldPiece.get());
                } catch (final Exception e) {
                    System.out.println("spacca tutto");
                    // System.out.println("Movement = " + movement);
                    // System.out.println("Board = " + this.board);
                }

            }

            // Move the piece
            pieceInvolved.setPosition(x);

            // Check if the player is not under check
            if (!this.gameController.isInCheck(pieceInvolved.getPlayer())) {
                result.add(x);
            }

            // Restore previous board
            pieceInvolved.setPosition(oldPosition);

            if (oldPiece.isPresent()) {
                this.board.add(oldPiece.get());
            }
        });

        return result;
    }

    @Override
    public final Board getBoard() {
        return this.board;
    }

}
