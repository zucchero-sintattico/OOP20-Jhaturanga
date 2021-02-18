package jhaturanga.model.movement;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import jhaturanga.model.game.GameController;
import jhaturanga.model.piece.PieceImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;

public class PieceSwapVariantMovementManager extends AbstractMovementManager {

    private final Iterator<Player> playerTurnIterator;
    private Player actualPlayersTurn;
    private final Map<PieceType, PieceType> pieceTypeSwapper = Map.of(PieceType.BISHOP, PieceType.KNIGHT,
            PieceType.KNIGHT, PieceType.ROOK, PieceType.ROOK, PieceType.BISHOP);

    public PieceSwapVariantMovementManager(final GameController gameController) {
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
            this.swapPieceType(movement);
            this.conditionalPawnUpgrade(movement);
            this.actualPlayersTurn = this.playerTurnIterator.next();
            return true;
        }
        return false;
    }

    private void swapPieceType(final Movement movement) {
        if (this.pieceTypeSwapper.containsKey(movement.getPieceInvolved().getType())) {
            this.getBoard().remove(movement.getPieceInvolved());
            this.getBoard().add(new PieceImpl(this.pieceTypeSwapper.get(movement.getPieceInvolved().getType()),
                    movement.getPieceInvolved().getPiecePosition(), movement.getPieceInvolved().getPlayer()));
        }
    }

}
