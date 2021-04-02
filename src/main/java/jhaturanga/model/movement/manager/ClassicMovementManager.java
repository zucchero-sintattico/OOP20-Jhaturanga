package jhaturanga.model.movement.manager;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.match.MatchStatus;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerPair;

public class ClassicMovementManager implements MovementManager {

    private final Board board;
    private final GameController gameController;
    private final Iterator<Player> playerTurnIterator;
    private Player actualPlayersTurn;
    private final MovementHandlerStrategy movementHandlerStrategy;

    private final CastlingManager castlingManager;

    public ClassicMovementManager(final GameController gameController) {
        this.gameController = gameController;
        this.movementHandlerStrategy = new ClassicMovementHandlerStrategy(this.gameController);
        this.castlingManager = new CastlingManagerImpl(this.gameController);
        this.board = this.gameController.boardState();

        this.playerTurnIterator = Stream.generate(() -> this.gameController.getPlayers()).flatMap(PlayerPair::stream)
                .iterator();

        this.actualPlayersTurn = this.playerTurnIterator.next();
    }

    /**
     * @param movement - the movement passed that has to be properly handled.
     * @return MovementResult - the result from the handling.
     */
    @Override
    public MovementResult move(final Movement movement) {
        if (this.isItThisPlayersTurn(movement) && this.movementHandlerStrategy.isMovementPossible(movement)) {
            final boolean hasCaptured = this.board.removeAtPosition(movement.getDestination());
            this.handleMovementSideEffects(movement);
            return this.resultingMovement(hasCaptured);
        }
        return MovementResult.INVALID_MOVE;
    }

    private void handleMovementSideEffects(final Movement movement) {
        movement.execute();
        this.castlingManager.checkAndExecuteCastling(movement);
        this.conditionalPawnUpgrade(movement);
        this.actualPlayersTurn = this.playerTurnIterator.next();
        movement.getPieceInvolved().hasMoved(true);
    }

    protected final boolean isItThisPlayersTurn(final Movement movement) {
        return this.getPlayerTurn().equals(movement.getPieceInvolved().getPlayer());
    }

    protected final void conditionalPawnUpgrade(final Movement movement) {
        if (this.hasPawnReachedBoardUpperOrLowerBound(movement)) {
            this.upgradePawnToQueen(movement);
        }
    }

    private void upgradePawnToQueen(final Movement movement) {
        this.board.remove(movement.getPieceInvolved());
        this.board.add(movement.getPieceInvolved().getPlayer().getPieceFactory().getQueen(movement.getDestination()));
    }

    private boolean hasPawnReachedBoardUpperOrLowerBound(final Movement movement) {
        return movement.getPieceInvolved().getType().equals(PieceType.PAWN)
                && (movement.getDestination().getY() == 0 || movement.getDestination().getY() == board.getRows() - 1);
    }

    protected final MovementResult resultingMovement(final boolean hasCaptured) {

        final MatchStatus matchStatus = this.gameController.getGameStatus(this.actualPlayersTurn);

        if (matchStatus.equals(MatchStatus.CHECKMATE) || matchStatus.equals(MatchStatus.DRAW)) {
            return MovementResult.CHECKMATED;
        } else if (this.gameController.isInCheck(this.actualPlayersTurn)) {
            return MovementResult.CHECKED;
        } else if (hasCaptured) {
            return MovementResult.CAPTURED;
        }
        return MovementResult.MOVED;
    }

    @Override
    public final Set<BoardPosition> filterOnPossibleMovesBasedOnGameController(final Piece piece) {
        return this.movementHandlerStrategy.filterOnPossibleMovesBasedOnGameController(piece);
    }

    @Override
    public final Player getPlayerTurn() {
        return this.actualPlayersTurn;
    }

    protected final void setActualPlayersTurn(final Player actualPlayersTurn) {
        this.actualPlayersTurn = actualPlayersTurn;
    }

    protected final MovementHandlerStrategy getMovementHandlerStrategy() {
        return movementHandlerStrategy;
    }

    protected final GameController getGameController() {
        return gameController;
    }

    protected final Iterator<Player> getPlayerTurnIterator() {
        return playerTurnIterator;
    }

}
