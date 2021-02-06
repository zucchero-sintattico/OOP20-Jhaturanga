package jhaturanga.test.game.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.piece.factory.PieceFactory;
import jhaturanga.model.piece.factory.PieceFactoryImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;

class BoardTest {

    private PieceFactory pfPlayer1;
    private PieceFactory pfPlayer2;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void init() {
        player1 = new PlayerImpl(PlayerColor.WHITE);
        player2 = new PlayerImpl(PlayerColor.BLACK);
        pfPlayer1 = new PieceFactoryImpl(player1);
        pfPlayer2 = new PieceFactoryImpl(player2);
    }

    /**
     * Test Board.contains method.
     */
    @Test
    void testBoardContains() {

        final int columns = 8;
        final int rows = 10;

        final Board testBoard = new BoardBuilderImpl().columns(columns).rows(rows).build();

        assertTrue(testBoard.contains(new BoardPositionImpl(0, 0)));
        assertTrue(testBoard.contains(new BoardPositionImpl(columns - 1, rows - 1)));
        assertFalse(testBoard.contains(new BoardPositionImpl(columns, rows - 1)));
        assertFalse(testBoard.contains(new BoardPositionImpl(columns - 1, rows)));

    }

    /**
     * Test Board.getPieceAtPosition method.
     */
    @Test
    void testBoardGetPieceAtPosition() {

        final int columns = 8;
        final int rows = 10;

        final Board testBoard = new BoardBuilderImpl().columns(columns).rows(rows)
                .addPiece(pfPlayer1.getPawn(new BoardPositionImpl(0, 0)))
                .addPiece(pfPlayer1.getBishop(new BoardPositionImpl(1, 1))).build();

        // Pawn position
        assertTrue(testBoard.getPieceAtPosition(new BoardPositionImpl(0, 0)).isPresent());
        assertEquals(PieceType.PAWN, testBoard.getPieceAtPosition(new BoardPositionImpl(0, 0)).get().getType());

        // Bishop position
        assertTrue(testBoard.getPieceAtPosition(new BoardPositionImpl(1, 1)).isPresent());
        assertEquals(PieceType.BISHOP, testBoard.getPieceAtPosition(new BoardPositionImpl(1, 1)).get().getType());

        // Empty cell
        assertTrue(testBoard.getPieceAtPosition(new BoardPositionImpl(2, 2)).isEmpty());

    }

}
