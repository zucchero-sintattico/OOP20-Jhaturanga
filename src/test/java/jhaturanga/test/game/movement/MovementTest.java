package jhaturanga.test.game.movement;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.piece.factory.PieceFactory;
import jhaturanga.model.piece.factory.PieceFactoryImpl;
import jhaturanga.model.piece.movement.NormalPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PieceMovementStrategy;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;

class MovementTest {

    private BoardBuilder bb;
    private PieceFactory pfPlayer1;
    private PieceFactory pfPlayer2;
    private Player player1;
    private Player player2;
    private Board board;
    private BoardPosition bp;
    private PieceMovementStrategy pms;

    @BeforeEach
    public void init() {
	player1 = new PlayerImpl(PlayerColor.WHITE);
	player2 = new PlayerImpl(PlayerColor.BLACK);
	pfPlayer1 = new PieceFactoryImpl(player1);
	pfPlayer2 = new PieceFactoryImpl(player2);

	bb = new BoardBuilderImpl();
	board = bb.columns(8).rows(8).addPiece(pfPlayer1.getKing(new BoardPositionImpl(4, 4)))
		.addPiece(pfPlayer2.getPawn(new BoardPositionImpl(3, 6)))
		.addPiece(pfPlayer1.getQueen(new BoardPositionImpl(5, 4))).build();

	// System.out.println(board.getPieceAtPosition(new BoardPositionImpl(4,
	// 4)).get().getIdentifier());
	pms = new NormalPieceMovementStrategyFactory()
		.getQueenMovementStrategy(board.getPieceAtPosition(new BoardPositionImpl(5, 4)).get());
	pms.getPossibleMoves(board);
	System.out.println(pms.getPossibleMoves(board).toString());
	System.out.println(pms.getPossibleMoves(board).size());

	if (pms.getPossibleMoves(board).contains(new BoardPositionImpl(4, 4))) {
	    board.getPieceAtPosition(new BoardPositionImpl(5, 4))
		    .ifPresent(x -> x.setPosition(new BoardPositionImpl(4, 4)));
	}
	board.getPieceAtPosition(new BoardPositionImpl(4, 4)).ifPresent(x -> x.getIdentifier().toString());
    }


    @Test
    void test() {
	assertTrue(board.getPieceAtPosition(new BoardPositionImpl(0, 0)).isPresent());
	assertEquals(PieceType.QUEEN, board.getPieceAtPosition(new BoardPositionImpl(5, 4)).get().getType());
	assertEquals(pfPlayer1.getKing(new BoardPositionImpl(4, 4)), pfPlayer1.getKing(new BoardPositionImpl(4, 4)));
	assertNotEquals(pfPlayer1.getKing(new BoardPositionImpl(4, 4)), pfPlayer1.getKing(new BoardPositionImpl(0, 1)));

    }

}
