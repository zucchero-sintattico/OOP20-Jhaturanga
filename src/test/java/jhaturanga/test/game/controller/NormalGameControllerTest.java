package jhaturanga.test.game.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.NormalGameController;
import jhaturanga.model.piece.factory.PieceFactory;
import jhaturanga.model.piece.factory.PieceFactoryImpl;
import jhaturanga.model.piece.movement.NormalPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;

class NormalGameControllerTest {

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
    void testWinner() {
        final BoardBuilder bb = new BoardBuilderImpl();
        Board board = bb.columns(8).rows(8).addPiece(pfPlayer2.getRook(new BoardPositionImpl(6, 1)))
                .addPiece(pfPlayer2.getQueen(new BoardPositionImpl(6, 6)))
                .addPiece(pfPlayer1.getRook(new BoardPositionImpl(1, 6))).addPiece(pfPlayer1.getKing(new BoardPositionImpl(7, 7)))
                .build();

        final PieceMovementStrategyFactory pmsf = new NormalPieceMovementStrategyFactory();
        final GameController gameContr = new NormalGameController(board, pmsf, List.of(player1, player2));
        assertTrue(board.getPieceAtPosition(new BoardPositionImpl(7, 7)).isPresent());
        assertFalse(gameContr.isWinner(player2));
        assertTrue(gameContr.isInCheck(player1));
    }

    @Test
    void testIsInCheck() {
        final BoardBuilder bb = new BoardBuilderImpl();
        Board board = bb.columns(8).rows(8).addPiece(pfPlayer2.getRook(new BoardPositionImpl(6, 1)))
                .addPiece(pfPlayer2.getQueen(new BoardPositionImpl(6, 6)))
                .addPiece(pfPlayer1.getRook(new BoardPositionImpl(1, 6))).addPiece(pfPlayer1.getKing(new BoardPositionImpl(7, 7)))
                .build();

        final PieceMovementStrategyFactory pmsf = new NormalPieceMovementStrategyFactory();
        final GameController gameContr = new NormalGameController(board, pmsf, List.of(player1, player2));
        assertTrue(board.getPieceAtPosition(new BoardPositionImpl(7, 7)).isPresent());
        assertTrue(gameContr.isInCheck(player1));
    }

    @Test
    void testDraw() {
        final BoardBuilder bb = new BoardBuilderImpl();
        Board board = bb.columns(8).rows(8).addPiece(pfPlayer2.getKing(new BoardPositionImpl(2, 7)))
                .addPiece(pfPlayer1.getKing(new BoardPositionImpl(2, 5))).addPiece(pfPlayer1.getPawn(new BoardPositionImpl(2, 6)))
                .build();

        PieceMovementStrategyFactory pmsf = new NormalPieceMovementStrategyFactory();
        GameController gameContr = new NormalGameController(board, pmsf, List.of(player1, player2));
        assertTrue(gameContr.isDraw());
        assertFalse(gameContr.isWinner(player1));
        assertFalse(gameContr.isWinner(player2));
        assertTrue(gameContr.isOver());

        final BoardBuilder bb1 = new BoardBuilderImpl();
        board = bb1.columns(8).rows(8).addPiece(pfPlayer2.getRook(new BoardPositionImpl(1, 1)))
                .addPiece(pfPlayer2.getPawn(new BoardPositionImpl(5, 3))).addPiece(pfPlayer2.getKing(new BoardPositionImpl(5, 4)))
                .addPiece(pfPlayer1.getKing(new BoardPositionImpl(5, 2))).build();

        pmsf = new NormalPieceMovementStrategyFactory();
        gameContr = new NormalGameController(board, pmsf, List.of(player1, player2));
        assertTrue(gameContr.isDraw());
        assertFalse(gameContr.isWinner(player1));
        assertFalse(gameContr.isWinner(player2));
        assertTrue(gameContr.isOver());
    }

}
