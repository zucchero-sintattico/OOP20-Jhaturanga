package jhaturanga.test.game.piece;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.factory.PieceFactory;
import jhaturanga.model.piece.factory.PieceFactoryImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;

class PieceTest {

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
     * Test equal implementation of Piece class.
     */
    @Test
    void testPieceEquals() {

	// Same piece
	assertEquals(pfPlayer1.getKing(new BoardPositionImpl(0, 0)), pfPlayer1.getKing(new BoardPositionImpl(0, 0)));

	// same piece but different position
	assertNotEquals(pfPlayer1.getKing(new BoardPositionImpl(4, 4)), pfPlayer1.getKing(new BoardPositionImpl(0, 1)));

	// Different pieces but same position
	assertNotEquals(pfPlayer1.getKing(new BoardPositionImpl(0, 0)),
		pfPlayer1.getQueen(new BoardPositionImpl(0, 0)));

	// Different player but same piece and same position
	assertNotEquals(pfPlayer1.getKing(new BoardPositionImpl(0, 0)), pfPlayer2.getKing(new BoardPositionImpl(0, 0)));

    }

}
