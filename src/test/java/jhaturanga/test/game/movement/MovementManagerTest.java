package jhaturanga.test.game.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

class MovementManagerTest {

    private Player player1;
    private Player player2;

    @BeforeEach
    public void init() {
        player1 = new PlayerImpl(PlayerColor.WHITE);
        player2 = new PlayerImpl(PlayerColor.BLACK);

    }

    /**
     * Test a basic capture movement.
     */
    @Test
    public void testBasicCapture() {
        final BoardBuilder bb = new BoardBuilderImpl();
        final Board board = bb.columns(8).rows(8)
                .addPiece(player1.getPieceFactory().getRook(new BoardPositionImpl(6, 1)))
                .addPiece(player1.getPieceFactory().getQueen(new BoardPositionImpl(6, 6)))
                .addPiece(player2.getPieceFactory().getRook(new BoardPositionImpl(6, 2))).build();

        final PieceMovementStrategyFactory pmsf = new ClassicPieceMovementStrategyFactory();
        final GameController gameController = new ClassicGameController(board, pmsf, List.of(player1, player2));
        final MovementManager movementManager = new ClassicMovementManager(gameController);

        // Queen in 6,6 capture rook in 6,2
        assertTrue(movementManager.move(new MovementImpl(board.getPieceAtPosition(new BoardPositionImpl(6, 6)).get(),
                new BoardPositionImpl(6, 2))));

        // Check that queen is now in 6,2
        assertEquals(PieceType.QUEEN, board.getPieceAtPosition(new BoardPositionImpl(6, 2)).get().getType());
    }

    @Test
    public void testKnightCheckKing() {
        final BoardBuilder bb1 = new BoardBuilderImpl();
        final Board board = bb1.columns(8).rows(8)
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(3, 0)))
                .addPiece(player1.getPieceFactory().getBishop(new BoardPositionImpl(4, 0)))
                .addPiece(player1.getPieceFactory().getPawn(new BoardPositionImpl(1, 1)))
                .addPiece(player1.getPieceFactory().getPawn(new BoardPositionImpl(2, 1)))
                .addPiece(player1.getPieceFactory().getPawn(new BoardPositionImpl(4, 1)))
                .addPiece(player2.getPieceFactory().getKnight(new BoardPositionImpl(2, 2))).build();

        final PieceMovementStrategyFactory pmsf = new ClassicPieceMovementStrategyFactory();
        final GameController gameContr = new ClassicGameController(board, pmsf, List.of(player1, player2));

        final MovementManager movementManager = new ClassicMovementManager(gameContr);

        // There is no winner
        assertFalse(gameContr.isWinner(player1));

        // White player is under check by the knight
        assertTrue(gameContr.isInCheck(player1));

        // This move doesn't prevent from being under check so this is not a possible
        // move
        assertFalse(movementManager.move(new MovementImpl(board.getPieceAtPosition(new BoardPositionImpl(4, 0)).get(),
                new BoardPositionImpl(5, 1))));

        // The pawn can capture the knight and save the king from being under check
        assertTrue(movementManager.move(new MovementImpl(board.getPieceAtPosition(new BoardPositionImpl(1, 1)).get(),
                new BoardPositionImpl(2, 2))));

        // There is no winner
        assertFalse(gameContr.isWinner(player1));

    }

}
