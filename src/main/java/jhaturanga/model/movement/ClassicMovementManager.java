package jhaturanga.model.movement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.GameController;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;

public class ClassicMovementManager implements MovementManager {

    private final Board board;
    private final PieceMovementStrategyFactory pieceMovementStrategies;
    private final GameController gameController;
    private final Iterator<Player> playerTurnIterator;
    private Player actualPlayersTurn;

    public ClassicMovementManager(final Board startingBoard, final PieceMovementStrategyFactory pieceMovementStrategies,
            final GameController gameController) {
        this.board = startingBoard;
        this.pieceMovementStrategies = pieceMovementStrategies;
        this.gameController = gameController;
        this.playerTurnIterator = Stream.generate(() -> this.gameController.getPlayers()).flatMap(i -> i.stream()).iterator();
        this.actualPlayersTurn = this.playerTurnIterator.next();
    }

    @Override
    public final boolean move(final Movement movement) {
        if (!this.actualPlayersTurn.equals(movement.getPieceInvolved().getPlayer())) {
            return false;
        }
        // Check if the movement is possible watching only in moves that don't put the
        // player under check.
        if (this.filterOnPossibleMovesBasedOnGameController(movement).contains(movement.getDestination())) {
            // Remove the piece in destination position, if present
            this.board.removeAtPosition(movement.getDestination());
            movement.execute();
            this.actualPlayersTurn = this.playerTurnIterator.next();
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
                this.board.remove(oldPiece.get());
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

}
