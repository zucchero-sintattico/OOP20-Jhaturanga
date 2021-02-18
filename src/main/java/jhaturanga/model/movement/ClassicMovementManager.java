package jhaturanga.model.movement;

import java.util.Iterator;
import java.util.stream.Stream;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.GameController;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;

public class ClassicMovementManager extends AbstractMovementManager {

    private final Iterator<Player> playerTurnIterator;
    private Player actualPlayersTurn;

    public ClassicMovementManager(final GameController gameController) {
        super(gameController);
        this.playerTurnIterator = Stream.generate(() -> this.getGameController().getPlayers()).flatMap(i -> i.stream())
                .iterator();
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
            this.getBoard().removeAtPosition(movement.getDestination());
            movement.execute();

            if (this.isCastle(movement)) {
                System.out.println("IT'A CASTLEEEE");
                final boolean sx = movement.getOrigin().getX() > movement.getDestination().getX();
                if (sx) {
                    System.out.println("SX CASTLEEEE");
                    final Piece rook = this.getBoard().getPieceAtPosition(
                            new BoardPositionImpl(movement.getDestination().getX() - 2, movement.getOrigin().getY()))
                            .get();
                    final Movement mov = new MovementImpl(rook,
                            new BoardPositionImpl(rook.getPiecePosition().getX() + 3, rook.getPiecePosition().getY()));
                    mov.execute();
                } else {
                    System.out.println("DX CASTLEEEE");
                    final Piece rook = this.getBoard().getPieceAtPosition(
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

}
