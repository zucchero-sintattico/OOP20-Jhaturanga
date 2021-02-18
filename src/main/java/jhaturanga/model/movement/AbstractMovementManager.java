package jhaturanga.model.movement;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.GameController;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;

public abstract class AbstractMovementManager implements MovementManager {

    private final Board board;
    private final PieceMovementStrategyFactory pieceMovementStrategies;
    private final GameController gameController;

    public AbstractMovementManager(final GameController gameController) {
        this.gameController = gameController;
        this.board = this.gameController.boardState();
        this.pieceMovementStrategies = this.gameController.getPieceMovementStrategyFactory();
    }

    /**
     * Use this method to define what occurs when a movement is passed.
     * 
     * @param movement
     * @return true if the movement for the specific variant is possible, false
     *         otherwise
     */
    public abstract boolean move(Movement movement);

    protected final void conditionalPawnUpgrade(final Movement movement) {
        if (movement.getPieceInvolved().getType().equals(PieceType.PAWN)
                && (movement.getDestination().getY() == 0 || movement.getDestination().getY() == board.getRows() - 1)) {
            this.board.remove(movement.getPieceInvolved());
            this.board
                    .add(movement.getPieceInvolved().getPlayer().getPieceFactory().getQueen(movement.getDestination()));
        }
    }

    /**
     * 
     * @param movement
     * @return true if the movement is castle, false otherwise
     */
    protected final boolean isCastle(final Movement movement) {
        return movement.getPieceInvolved().getType().equals(PieceType.KING)
                && Math.abs(movement.getOrigin().getX() - movement.getDestination().getX()) == 2;
    }

    protected final Set<BoardPosition> filterOnPossibleMovesBasedOnGameController(final Movement movement) {

        final Piece pieceInvolved = movement.getPieceInvolved();

        // Save a copy of the piece's position
        final BoardPosition oldPosition = new BoardPositionImpl(pieceInvolved.getPiecePosition());

        // Get all possible moves of the piece
        final Set<BoardPosition> positions = this.pieceMovementStrategies.getPieceMovementStrategy(pieceInvolved)
                .getPossibleMoves(this.board);

        final Set<BoardPosition> result = new HashSet<>();

        positions.forEach(x -> {

            final Movement mov = new MovementImpl(pieceInvolved, oldPosition, x);
            if (!this.isCastle(mov)
                    || this.isCastle(mov) && !this.gameController.isInCheck(pieceInvolved.getPlayer())) {

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
            }
        });

        return result;

    }

    protected final Board getBoard() {
        return board;
    }

    protected final PieceMovementStrategyFactory getPieceMovementStrategies() {
        return pieceMovementStrategies;
    }

    protected final GameController getGameController() {
        return gameController;
    }

}
