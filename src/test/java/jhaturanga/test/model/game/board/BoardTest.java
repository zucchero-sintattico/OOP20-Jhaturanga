package jhaturanga.test.model.game.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.user.management.UsersManager;

class BoardTest {

    private Player player1;
    private Player player2;

    @BeforeEach
    public void init() {
        player1 = new PlayerImpl(PlayerColor.WHITE, UsersManager.GUEST);
        player2 = new PlayerImpl(PlayerColor.BLACK, UsersManager.GUEST);
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
     * Test Board.remove method.
     */
    @Test
    void testBoardRemove() {

        final int columns = 8;
        final int rows = 10;

        // Get a white queen in position 0,0
        final Piece queen = player1.getPieceFactory().getQueen(new BoardPositionImpl(0, 0));
        final Board testBoard = new BoardBuilderImpl().columns(columns).rows(rows).addPiece(queen).build();

        // Check that in position 0,0 there is a piece
        assertTrue(testBoard.getPieceAtPosition(new BoardPositionImpl(0, 0)).isPresent());

        // Check that in position 0,0 there is a queen
        assertEquals(PieceType.QUEEN, testBoard.getPieceAtPosition(new BoardPositionImpl(0, 0)).get().getType());

        // Check that in position 0,0 there is the queen
        assertEquals(queen, testBoard.getPieceAtPosition(new BoardPositionImpl(0, 0)).get());

    }

    /**
     * Test Board.removeAtPosition method.
     */
    @Test
    void testBoardRemoveAtPosition() {

        final int columns = 8;
        final int rows = 10;

        // Get a queen in position 0,0
        final Piece queen = player1.getPieceFactory().getQueen(new BoardPositionImpl(0, 0));
        final Board testBoard = new BoardBuilderImpl().columns(columns).rows(rows).addPiece(queen).build();

        // Remove the queen
        testBoard.remove(queen);

        // check that queen's position is now empty
        assertTrue(testBoard.getPieceAtPosition(new BoardPositionImpl(0, 0)).isEmpty());

        // check that board doesn't contains the queen anymore
        assertFalse(testBoard.contains(queen));

        // check that piece's set has now a size of 0
        assertEquals(testBoard.getPieces().size(), 0);

    }

    /**
     * Test Board.getPieceAtPosition method.
     */
    @Test
    void testBoardGetPieceAtPosition() {

        final int columns = 8;
        final int rows = 10;

        final Board testBoard = new BoardBuilderImpl().columns(columns).rows(rows)
                .addPiece(this.player1.getPieceFactory().getPawn(new BoardPositionImpl(0, 0)))
                .addPiece(this.player2.getPieceFactory().getBishop(new BoardPositionImpl(1, 1))).build();

        // Pawn position
        assertTrue(testBoard.getPieceAtPosition(new BoardPositionImpl(0, 0)).isPresent());
        assertEquals(PieceType.PAWN, testBoard.getPieceAtPosition(new BoardPositionImpl(0, 0)).get().getType());
        assertEquals(PlayerColor.WHITE,
                testBoard.getPieceAtPosition(new BoardPositionImpl(0, 0)).get().getPlayer().getColor());

        // Bishop position
        assertTrue(testBoard.getPieceAtPosition(new BoardPositionImpl(1, 1)).isPresent());
        assertEquals(PieceType.BISHOP, testBoard.getPieceAtPosition(new BoardPositionImpl(1, 1)).get().getType());
        assertEquals(PlayerColor.BLACK,
                testBoard.getPieceAtPosition(new BoardPositionImpl(1, 1)).get().getPlayer().getColor());

        // Empty cell
        assertTrue(testBoard.getPieceAtPosition(new BoardPositionImpl(2, 2)).isEmpty());

    }

}
