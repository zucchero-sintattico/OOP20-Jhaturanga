package jhaturanga.test.game.piece;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.user.management.UsersManager;

class PieceTest {

    private Player player1;
    private Player player2;

    @BeforeEach
    public void init() {
        player1 = new PlayerImpl(PlayerColor.WHITE, UsersManager.GUEST);
        player2 = new PlayerImpl(PlayerColor.BLACK, UsersManager.GUEST);
    }

    /**
     * Test equal implementation of Piece class.
     */
    @Test
    void testPieceEquals() {

        // Same piece
        assertEquals(player1.getPieceFactory().getKing(new BoardPositionImpl(0, 0)),
                player1.getPieceFactory().getKing(new BoardPositionImpl(0, 0)));

        // same piece but different position
        assertNotEquals(player1.getPieceFactory().getKing(new BoardPositionImpl(4, 4)),
                player1.getPieceFactory().getKing(new BoardPositionImpl(0, 1)));

        // Different pieces but same position
        assertNotEquals(player1.getPieceFactory().getKing(new BoardPositionImpl(0, 0)),
                player1.getPieceFactory().getQueen(new BoardPositionImpl(0, 0)));

        // Different player but same piece and same position
        assertNotEquals(player1.getPieceFactory().getKing(new BoardPositionImpl(0, 0)),
                player2.getPieceFactory().getKing(new BoardPositionImpl(0, 0)));

    }

}
