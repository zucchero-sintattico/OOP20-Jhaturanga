package jhaturanga.model.movement;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.game.GameController;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;

public class PieceSwapVariantMovementManager extends AbstractMovementManager {

    private final Iterator<Player> playerTurnIterator;
    private Player actualPlayersTurn;
    private final Map<PieceType, PieceType> pieceTypeSwapper = Map.of(PieceType.BISHOP, PieceType.KNIGHT,
            PieceType.KNIGHT, PieceType.ROOK, PieceType.ROOK, PieceType.BISHOP);

    public PieceSwapVariantMovementManager(final Board startingBoard,
            final PieceMovementStrategyFactory pieceMovementStrategies, final GameController gameController) {
        super(startingBoard, pieceMovementStrategies, gameController);
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
            this.swapPieceType(movement.getPieceInvolved());
            this.conditionalPawnUpgrade(movement);
            this.actualPlayersTurn = this.playerTurnIterator.next();
            return true;
        }
        return false;
    }

    private void swapPieceType(final Piece piece) {
        this.getBoard().remove(piece);
        this.getBoard().add(
                new PieceImpl(this.pieceTypeSwapper.get(piece.getType()), piece.getPiecePosition(), piece.getPlayer()));
    }

}
