package jhaturanga.test.game.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
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

    @Test
    void testQueenMovement() {
        final BoardBuilder bb = new BoardBuilderImpl();
        Board board = bb.columns(8).rows(8).addPiece(pfPlayer2.getPawn(new BoardPositionImpl(3, 6)))
                .addPiece(pfPlayer1.getQueen(new BoardPositionImpl(5, 4))).build();

        PieceMovementStrategy pms = new NormalPieceMovementStrategyFactory()
                .getQueenMovementStrategy(board.getPieceAtPosition(new BoardPositionImpl(5, 4)).get());
        pms.getPossibleMoves(board);

        if (pms.getPossibleMoves(board).contains(new BoardPositionImpl(4, 4))) {
            board.getPieceAtPosition(new BoardPositionImpl(5, 4)).ifPresent(x -> x.setPosition(new BoardPositionImpl(4, 4)));
        }

        pms = new NormalPieceMovementStrategyFactory()
                .getQueenMovementStrategy(board.getPieceAtPosition(new BoardPositionImpl(4, 4)).get());

        assertTrue(board.getPieceAtPosition(new BoardPositionImpl(4, 4)).isPresent());
        assertEquals(27, pms.getPossibleMoves(board).size());

        board.getPieceAtPosition(new BoardPositionImpl(4, 4)).ifPresent(x -> x.setPosition(new BoardPositionImpl(1, 7)));

        assertEquals(PieceType.QUEEN, board.getPieceAtPosition(new BoardPositionImpl(1, 7)).get().getType());
    }

    /**
     * This test is used to verify that the Pawns movements are correct for the
     * NormalGame GameType.
     */

    @Test
    void testPawnMovement() {
        final BoardBuilder bb = new BoardBuilderImpl();
        Board board = bb.columns(8).rows(8).addPiece(pfPlayer1.getPawn(new BoardPositionImpl(4, 1)))
                .addPiece(pfPlayer2.getQueen(new BoardPositionImpl(3, 2))).build();
        PieceMovementStrategy pms = new NormalPieceMovementStrategyFactory()
                .getPawnMovementStrategy(board.getPieceAtPosition(new BoardPositionImpl(4, 1)).get());
        assertEquals(Set.of(new BoardPositionImpl(3, 2), new BoardPositionImpl(4, 2)), pms.getPossibleMoves(board));
    }

    /**
     * This test is used to verify that the Rook's movements are correct for the
     * NormalGame GameType.
     */

    @Test
    void testRookMovement() {
        final BoardBuilder bb = new BoardBuilderImpl();
        Board board = bb.columns(8).rows(8).addPiece(pfPlayer1.getPawn(new BoardPositionImpl(4, 1)))
                .addPiece(pfPlayer2.getQueen(new BoardPositionImpl(3, 2))).build();

        board = bb.columns(8).rows(8).addPiece(pfPlayer1.getRook(new BoardPositionImpl(4, 4)))
                .addPiece(pfPlayer2.getPawn(new BoardPositionImpl(4, 6))).addPiece(pfPlayer1.getPawn(new BoardPositionImpl(4, 3)))
                .build();

        PieceMovementStrategy pms = new NormalPieceMovementStrategyFactory()
                .getPieceMovementStrategy(board.getPieceAtPosition(new BoardPositionImpl(4, 4)).get());

        assertFalse(pms.getPossibleMoves(board).contains(new BoardPositionImpl(4, 7)));
        assertTrue(pms.getPossibleMoves(board).contains(new BoardPositionImpl(4, 6)));
        assertFalse(pms.getPossibleMoves(board).contains(new BoardPositionImpl(4, 3)));
    }

}
