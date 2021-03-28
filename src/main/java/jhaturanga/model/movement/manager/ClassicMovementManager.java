package jhaturanga.model.movement.manager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
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

public class ClassicMovementManager implements MovementManager {

    private final Board board;
    private final PieceMovementStrategies pieceMovementStrategies;
    private final GameController gameController;
    private final Iterator<Player> playerTurnIterator;
    private Player actualPlayersTurn;

    public ClassicMovementManager(final GameController gameController) {
        this.gameController = gameController;
        this.board = this.gameController.boardState();
        this.pieceMovementStrategies = this.gameController.getPieceMovementStrategyFactory();
        this.playerTurnIterator = Stream.generate(() -> this.gameController.getPlayers())
                .flatMap(x -> Stream.of(x.getX(), x.getY())).iterator();
        this.actualPlayersTurn = this.playerTurnIterator.next();
    }

    /**
     * It is given for granted that every MovementManager variation will anyway
     * always keep the structure of this particular MovementManager; "move" is a
     * method that CAN and SHOULD be overriden by Classes who extend
     * ClassicMovementManager.
     */
    @Override
    public MovementResult move(final Movement movement) {

        if (!this.actualPlayersTurn.equals(movement.getPieceInvolved().getPlayer())) {
            return MovementResult.INVALID_MOVE;
        }
        // Check if the movement is possible only on moves that don't put the
        // player under check.
        if (this.filterOnPossibleMovesBasedOnGameController(movement.getPieceInvolved())
                .contains(movement.getDestination())) {
            // Remove the piece in destination position, if present
            final boolean captured = this.board.getPieceAtPosition(movement.getDestination()).isPresent();
            this.board.removeAtPosition(movement.getDestination());
            movement.execute();
            // After movement is executed, I need to check if it was a castle, if it was I
            // need to complete it by moving the relative Rook.
            this.checkAndExecuteCastle(movement);
            this.conditionalPawnUpgrade(movement);
            this.actualPlayersTurn = this.playerTurnIterator.next();
            movement.getPieceInvolved().hasMoved(true);
            return this.resultingMovementResult(captured);
        }
        return MovementResult.INVALID_MOVE;
    }

    /**
     * 
     * @param captured to know if during the execution of the movement a piece was
     *                 captured
     * @return ActionType representing the actionType resulting from the executed
     *         movement.
     */
    protected MovementResult resultingMovementResult(final boolean captured) {
        // It's disgraceful the use of so many if statements in a method, but a
        // different approach would probably be over-kill.
        if (this.gameController.checkGameStatus(this.actualPlayersTurn).equals(MatchStatusEnum.CHECKMATE)
                || this.gameController.checkGameStatus(this.actualPlayersTurn).equals(MatchStatusEnum.DRAW)) {
            return MovementResult.CHECKMATE;
        } else if (this.gameController.isInCheck(this.actualPlayersTurn)) {
            return MovementResult.CHECK;
        } else if (captured) {
            return MovementResult.CAPTURE;
        }
        return MovementResult.MOVE;
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

    private boolean isLastCheckOnCastleValid(final Movement movement) {
        final int increment = movement.getOrigin().getX() > movement.getDestination().getX() ? -1 : 1;
        final BoardPosition nextToItPos = new BoardPositionImpl(movement.getOrigin().getX() + increment,
                movement.getOrigin().getY());
        // Move the piece
        movement.getPieceInvolved().setPosition(nextToItPos);
        // Check whether the King is under check or not
        final boolean result = !this.gameController.isInCheck(movement.getPieceInvolved().getPlayer());

        movement.getPieceInvolved().setPosition(movement.getOrigin());

        return result;
    }

    @Override
    public final Set<BoardPosition> filterOnPossibleMovesBasedOnGameController(final Piece piece) {
        // Save a copy of the piece's position
        final BoardPosition oldPosition = new BoardPositionImpl(piece.getPiecePosition());
        // Get all possible moves of the piece
        final Set<BoardPosition> positions = this.pieceMovementStrategies.getPieceMovementStrategy(piece)
                .getPossibleMoves(this.board);

        final Set<BoardPosition> result = new HashSet<>();

        positions.forEach(pos -> {
            final Movement mov = new MovementImpl(piece, oldPosition, pos);

            if (!this.pieceMovementStrategies.canCastle() || !this.isCastle(mov)
                    || !this.gameController.isInCheck(piece.getPlayer()) && this.isLastCheckOnCastleValid(mov)
                            && this.getClosestRookInRangeThatHasntMovedYet(mov).isPresent()) {

                // Try to get the piece in the x position
                final Optional<Piece> oldPiece = this.board.getPieceAtPosition(pos);
                // If there is a piece in x position this is a capture move
                oldPiece.ifPresent(this.board::remove);
                // Move the piece
                piece.setPosition(pos);
                // Check if the player is not under check
                if (!this.gameController.isInCheck(piece.getPlayer())) {
                    result.add(pos);
                }
                // Restore previous board
                piece.setPosition(oldPosition);
                oldPiece.ifPresent(this.board::add);
            }
        });
        return result;
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
