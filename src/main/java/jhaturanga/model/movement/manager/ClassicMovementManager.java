package jhaturanga.model.movement.manager;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.GameController;
import jhaturanga.model.match.MatchStatusEnum;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.piece.movement.PieceMovementStrategies;
import jhaturanga.model.player.Player;
import one.util.streamex.StreamEx;

public class ClassicMovementManager implements MovementManager {

    private final Board board;
    private final PieceMovementStrategies pieceMovementStrategies;
    private final GameController gameController;
    private final Iterator<Player> playerTurnIterator;
    private Player actualPlayersTurn;

    private final BiPredicate<Movement, GameController> testMovementForCheck = (mov, gameContr) -> {
        mov.getPieceInvolved().setPosition(mov.getDestination());
        final boolean result = !gameContr.isInCheck(mov.getPieceInvolved().getPlayer());
        mov.getPieceInvolved().setPosition(mov.getOrigin());
        return result;
    };

    public ClassicMovementManager(final GameController gameController) {
        this.gameController = gameController;
        this.board = this.gameController.boardState();
        this.pieceMovementStrategies = this.gameController.getPieceMovementStrategyFactory();

        this.playerTurnIterator = Stream.generate(() -> this.gameController.getPlayers())
                .flatMap(x -> Stream.of(x.getX(), x.getY())).iterator();

        this.actualPlayersTurn = this.playerTurnIterator.next();
    }

    /**
     * @param movement - the movement passed that has to be properly handled.
     * @return MovementResult - the result from the handling.
     */
    @Override
    public MovementResult move(final Movement movement) {

        if (!this.actualPlayersTurn.equals(movement.getPieceInvolved().getPlayer())) {
            return MovementResult.INVALID_MOVE;
        }

        if (this.filterOnPossibleMovesBasedOnGameController(movement.getPieceInvolved())
                .contains(movement.getDestination())) {
            final boolean hasCaptured = this.board.getPieceAtPosition(movement.getDestination()).isPresent();
            this.board.removeAtPosition(movement.getDestination());
            movement.execute();

            this.checkAndExecuteCastle(movement);
            this.conditionalPawnUpgrade(movement);
            this.actualPlayersTurn = this.playerTurnIterator.next();
            movement.getPieceInvolved().hasMoved(true);
            return this.resultingMovement(hasCaptured);
        }
        return MovementResult.INVALID_MOVE;
    }

    /**
     * 
     * @param hasCaptured to know if during the execution of the movement a piece
     *                    was captured
     * @return ActionType representing the actionType resulting from the executed
     *         movement.
     */
    protected MovementResult resultingMovement(final boolean hasCaptured) {
        final MatchStatusEnum matchStatus = this.gameController.checkGameStatus(this.actualPlayersTurn);

        if (matchStatus.equals(MatchStatusEnum.CHECKMATE) || matchStatus.equals(MatchStatusEnum.DRAW)) {
            return MovementResult.CHECKMATED;
        } else if (this.gameController.isInCheck(this.actualPlayersTurn)) {
            return MovementResult.CHECKED;
        } else if (hasCaptured) {
            return MovementResult.CAPTURED;
        }

        return MovementResult.MOVED;
    }

    private Optional<Piece> getClosestRookInRangeThatHasntMovedYet(final Movement mov) {
        return this.board.getPiecesStatus().stream().filter(i -> i.getType().equals(PieceType.ROOK))
                .filter(rook -> Math.abs(rook.getPiecePosition().getX() - mov.getDestination().getX()) <= 2
                        && rook.getPiecePosition().getY() == mov.getDestination().getY()
                        && rook.getPlayer().equals(mov.getPieceInvolved().getPlayer()) && !rook.hasAlreadyBeenMoved())
                .findFirst();
    }

    private void checkAndExecuteCastle(final Movement movement) {
        if (this.isCastle(movement)) {
            final int increment = movement.getOrigin().getX() > movement.getDestination().getX() ? 1 : -1;
            this.getClosestRookInRangeThatHasntMovedYet(movement).ifPresent(rook -> {
                rook.setPosition(new BoardPositionImpl(movement.getDestination().getX() + increment,
                        rook.getPiecePosition().getY()));
                movement.execute();
            });
        }
    }

    /**
     * 
     * @param movement
     * @return true if the movement is a Castle, false otherwise
     */
    protected final boolean isCastle(final Movement movement) {
        return movement.getPieceInvolved().getType().equals(PieceType.KING)
                && Math.abs(movement.getOrigin().getX() - movement.getDestination().getX()) == 2
                && movement.getOrigin().getY() == movement.getDestination().getY();
    }

    private boolean isPathToCastleFreeFromCheck(final Movement movement) {
        final int increment = movement.getOrigin().getX() > movement.getDestination().getX() ? -1 : 1;
        final BoardPosition nextToItPos = new BoardPositionImpl(movement.getOrigin().getX() + increment,
                movement.getOrigin().getY());

        return this.testMovementForCheck.test(
                new MovementImpl(movement.getPieceInvolved(), movement.getOrigin(), nextToItPos), this.gameController);
    }

    @Override
    public final Set<BoardPosition> filterOnPossibleMovesBasedOnGameController(final Piece piece) {
        return StreamEx.of(this.pieceMovementStrategies.getPieceMovementStrategy(piece).getPossibleMoves(this.board))
                .map(pos -> new MovementImpl(piece, piece.getPiecePosition(), pos))
                .filter(this::areChecksOnCastlingValid).filter(this::wouldNotBeInCheck).map(Movement::getDestination)
                .toSet();
    }

    private boolean wouldNotBeInCheck(final Movement movement) {
        final Optional<Piece> enemyPiece = this.board.getPieceAtPosition(movement.getDestination());
        enemyPiece.ifPresent(this.board::remove);
        final boolean result = this.testMovementForCheck.test(movement, this.gameController);
        enemyPiece.ifPresent(this.board::add);
        return result;
    }

    private boolean areChecksOnCastlingValid(final Movement movement) {
        return !this.pieceMovementStrategies.canCastle() || !this.isCastle(movement)
                || this.isCastlingCorrect(movement);
    }

    private boolean isCastlingCorrect(final Movement movement) {
        return !this.gameController.isInCheck(movement.getPieceInvolved().getPlayer())
                && this.isPathToCastleFreeFromCheck(movement)
                && this.getClosestRookInRangeThatHasntMovedYet(movement).isPresent();
    }

    /**
     * This method is used to check if a paws has reached the vertical farthest
     * position from it's starting one and in case upgrade it to a QUEEN.
     * 
     * @param movement
     */
    protected final void conditionalPawnUpgrade(final Movement movement) {
        if (movement.getPieceInvolved().getType().equals(PieceType.PAWN)
                && (movement.getDestination().getY() == 0 || movement.getDestination().getY() == board.getRows() - 1)) {
            this.board.remove(movement.getPieceInvolved());
            this.board
                    .add(movement.getPieceInvolved().getPlayer().getPieceFactory().getQueen(movement.getDestination()));
        }
    }

    @Override
    public final Player getPlayerTurn() {
        return this.actualPlayersTurn;
    }

    /**
     * 
     * @param actualPlayersTurn the player whom turn now is.
     */
    protected final void setActualPlayersTurn(final Player actualPlayersTurn) {
        this.actualPlayersTurn = actualPlayersTurn;
    }

    protected final GameController getGameController() {
        return gameController;
    }

    protected final Iterator<Player> getPlayerTurnIterator() {
        return playerTurnIterator;
    }

}
