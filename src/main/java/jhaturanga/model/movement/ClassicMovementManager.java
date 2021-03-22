package jhaturanga.model.movement;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import jhaturanga.controllers.match.MovementResult;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.MatchStatusEnum;
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
     * always keep the structure of this particular MovementManager; "move" is a
     * method that CAN and SHOULD be overroden by Classes who extend
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
        return this.board.getBoardState().stream().filter(i -> i.getType().equals(PieceType.ROOK))
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

        positions.forEach(x -> {

            final Movement mov = new MovementImpl(piece, oldPosition, x);
            /**
             * For a Movement's applicability, to even be evaluated, it must respect the
             * following conditions: it must not be a castling, otherwise, if it is a
             * castling movement, the King mustn't have moved yet, the King must not be
             * under check, also, the way to castling's position must be fully free of
             * pieces(but this check is already been done by the Piece's movementStrategy),
             * also, the king's path to castle mustn't be threatened by an enemie's piece
             * attack and the Rook with which the King want's to castle mustn't have moved
             * yet.
             */
            if (!this.pieceMovementStrategies.canCastle() || !this.isCastle(mov)
                    || !this.gameController.isInCheck(piece.getPlayer()) && this.isLastCheckOnCastleValid(mov)
                            && this.getClosestRookInRangeThatHasntMovedYet(mov).isPresent()) {
                // Try to get the piece in the x position
                final Optional<Piece> oldPiece = this.board.getPieceAtPosition(x);
                // If there is a piece in x position this is a capture move
                if (oldPiece.isPresent()) {
                    this.board.remove(oldPiece.get());
                }
                // Move the piece
                piece.setPosition(x);

                // Check if the player is not under check
                if (!this.gameController.isInCheck(piece.getPlayer())) {
                    result.add(x);
                }
                // Restore previous board
                piece.setPosition(oldPosition);

                if (oldPiece.isPresent()) {
                    this.board.add(oldPiece.get());
                }
            }
        });
        return result;
    }

    /**
     * This method is used to check if a paws has reached the vertical furthest
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
