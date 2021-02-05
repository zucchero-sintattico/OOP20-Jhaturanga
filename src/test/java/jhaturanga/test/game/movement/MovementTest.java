package jhaturanga.test.game.movement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.PieceImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.piece.factory.PieceFactory;
import jhaturanga.model.piece.factory.PieceFactoryImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;

class MovementTest {

//	private static final Map<String,Integer> INITIAL_RANKING = new HashMap<String,Integer>();
//	static{
//		INITIAL_RANKING.put("Nadal", 6000);
//		INITIAL_RANKING.put("Federer", 7000);
//		INITIAL_RANKING.put("Djokovic", 6500);
//		INITIAL_RANKING.put("Fognini", 4000);
//	}

    private BoardBuilder bb;
    private PieceFactory pf;
    private Player pl;
    private Board board;
    private BoardPosition bp;

    @BeforeEach
    public void init() {
	pl = new Player() {

	    @Override
	    public PlayerColor getColor() {
		return PlayerColor.WHITE;
	    }
	};
	pf = new PieceFactoryImpl(pl);
	bb = new BoardBuilderImpl();
	board = bb
		.columns(8)
		.rows(8)
		.addPiece(pf.getKing(new BoardPositionImpl(0, 0)))
		.addPiece(pf.getPawn(new BoardPositionImpl(1, 0)))
		.build();
    }

    @Test
    void test() {
	assertTrue(board.contains(new BoardPositionImpl(0, 0)));
	assertEquals(PieceType.KING, board.getPieceAtPosition(new BoardPositionImpl(0, 0)).get().getType());
    }

}
