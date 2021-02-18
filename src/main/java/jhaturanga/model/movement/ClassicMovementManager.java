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
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;

public class ClassicMovementManager implements MovementManager {

    private final Board board;
    private final PieceMovementStrategyFactory pieceMovementStrategies;
    private final GameController gameController;
    private final Iterator<Player> playerTurnIterator;
    private Player actualPlayersTurn;

    public ClassicMovementManager(final GameController gameController) {
        this.gameController = gameController;
        this.board = this.gameController.boardState();
        this.pieceMovementStrategies = this.gameController.getPieceMovementStrategyFactory();
        this.playerTurnIterator = Stream.generate(() -> this.gameController.getPlayers()).flatMap(i -> i.stream())
                .iterator();
        this.actualPlayersTurn = this.playerTurnIterator.next();
    }

    /**
     * It is given for granted that every MovementManager variation will anyway
     * always keep the structure of this particular MovementManager. "move" is a
     * method that can and should be overwritten by classes who extend
     * ClassicMovementManager.
     */
    @Override
    public boolean move(final Movement movement) {
        if (!this.actualPlayersTurn.equals(movement.getPieceInvolved().getPlayer())) {
            return false;
        }
        // Check if the movement is possible watching only in moves that don't put the
        // player under check.
        if (this.filterOnPossibleMovesBasedOnGameController(movement).contains(movement.getDestination())) {
            // Remove the piece in destination position, if present
            this.board.removeAtPosition(movement.getDestination());
            movement.execute();

            if (this.isCastle(movement)) {
                System.out.println("IT'A CASTLEEEE");
                final boolean sx = movement.getOrigin().getX() > movement.getDestination().getX();
                if (sx) {
                    System.out.println("SX CASTLEEEE");
                    final Piece rook = this.board.getPieceAtPosition(
                            new BoardPositionImpl(movement.getDestination().getX() - 2, movement.getOrigin().getY()))
                            .get();
                    final Movement mov = new MovementImpl(rook,
                            new BoardPositionImpl(rook.getPiecePosition().getX() + 3, rook.getPiecePosition().getY()));
                    mov.execute();
                } else {
                    System.out.println("DX CASTLEEEE");
                    final Piece rook = this.board.getPieceAtPosition(
                            new BoardPositionImpl(movement.getDestination().getX() + 1, movement.getOrigin().getY()))
                            .get();
                    final Movement mov = new MovementImpl(rook,
                            new BoardPositionImpl(rook.getPiecePosition().getX() - 2, rook.getPiecePosition().getY()));
                    mov.execute();
                }

            }
            // Sets the boolean kingHasMoved property to true if the movement involves the
            // king
            this.conditionalPawnUpgrade(movement);
            this.actualPlayersTurn = this.playerTurnIterator.next();
            return true;
        }
        return false;
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

    @Override
    public final Player getPlayerTurn() {
        return this.actualPlayersTurn;
    }

    protected final Player getActualPlayersTurn() {
        return actualPlayersTurn;
    }

    protected final void setActualPlayersTurn(Player actualPlayersTurn) {
        this.actualPlayersTurn = actualPlayersTurn;
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

    protected final Iterator<Player> getPlayerTurnIterator() {
        return playerTurnIterator;
    }

}
