package jhaturanga.test.game.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.controllers.game.ActionType;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.ClassicGameController;
import jhaturanga.model.game.GameController;
import jhaturanga.model.movement.ClassicMovementManager;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.movement.MovementManager;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.piece.movement.ClassicPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.test.commons.Constants;

class MovementManagerTest {

    private Player player1;
    private Player player2;

    @BeforeEach
    public void init() {
        player1 = new PlayerImpl(PlayerColor.WHITE, UsersManager.GUEST);
        player2 = new PlayerImpl(PlayerColor.BLACK, UsersManager.GUEST);

    }

    /**
     * Test a basic capture movement.
     */
    @Test
    public void testBasicCapture() {
        final BoardBuilder bb = new BoardBuilderImpl();
        final Board board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player1.getPieceFactory().getRook(new BoardPositionImpl(Constants.SIX, Constants.ONE)))
                .addPiece(player1.getPieceFactory().getQueen(new BoardPositionImpl(Constants.SIX, Constants.SIX)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.NINE, Constants.SIX)))
                .addPiece(player2.getPieceFactory().getKing(new BoardPositionImpl(Constants.FIVE, Constants.SIX)))
                .addPiece(player2.getPieceFactory().getRook(new BoardPositionImpl(Constants.SIX, Constants.TWO)))
                .build();

        final PieceMovementStrategyFactory pmsf = new ClassicPieceMovementStrategyFactory();
        final GameController gameController = new ClassicGameController(board, pmsf, List.of(player1, player2));
        final MovementManager movementManager = new ClassicMovementManager(gameController);

        // Queen in 6,6 capture rook in 6,2
        assertEquals(ActionType.CAPTURE,
                movementManager.move(new MovementImpl(
                        board.getPieceAtPosition(new BoardPositionImpl(Constants.SIX, Constants.SIX)).get(),
                        new BoardPositionImpl(Constants.SIX, Constants.TWO))));

        // Check that queen is now in 6,2
        assertEquals(PieceType.QUEEN,
                board.getPieceAtPosition(new BoardPositionImpl(Constants.SIX, Constants.TWO)).get().getType());
    }

    @Test
    public void testKnightCheckKing() {
        final BoardBuilder bb1 = new BoardBuilderImpl();
        final Board board = bb1.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.THREE, Constants.ZERO)))
                .addPiece(player1.getPieceFactory().getBishop(new BoardPositionImpl(Constants.FOUR, Constants.ZERO)))
                .addPiece(player1.getPieceFactory().getPawn(new BoardPositionImpl(Constants.ONE, Constants.ONE)))
                .addPiece(player1.getPieceFactory().getPawn(new BoardPositionImpl(Constants.TWO, Constants.ONE)))
                .addPiece(player1.getPieceFactory().getPawn(new BoardPositionImpl(Constants.FOUR, Constants.ONE)))
                .addPiece(player2.getPieceFactory().getKnight(new BoardPositionImpl(Constants.TWO, Constants.TWO)))
                .build();

        final PieceMovementStrategyFactory pmsf = new ClassicPieceMovementStrategyFactory();
        final GameController gameContr = new ClassicGameController(board, pmsf, List.of(player1, player2));

        final MovementManager movementManager = new ClassicMovementManager(gameContr);

        // There is no winner
        assertFalse(gameContr.isWinner(player1));

        // White player is under check by the knight
        assertTrue(gameContr.isInCheck(player1));

        // This move doesn't prevent from being under check so this is not a possible
        // move
        assertFalse(!movementManager.move(
                new MovementImpl(board.getPieceAtPosition(new BoardPositionImpl(Constants.FOUR, Constants.ZERO)).get(),
                        new BoardPositionImpl(Constants.FIVE, Constants.ONE)))
                .equals(ActionType.NONE));

        // The pawn can capture the knight and save the king from being under check
        assertTrue(!movementManager.move(
                new MovementImpl(board.getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.ONE)).get(),
                        new BoardPositionImpl(Constants.TWO, Constants.TWO)))
                .equals(ActionType.NONE));

        // There is no winner
        assertFalse(gameContr.isWinner(player1));

    }

}
