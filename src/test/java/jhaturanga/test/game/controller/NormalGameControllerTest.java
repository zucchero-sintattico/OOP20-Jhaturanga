package jhaturanga.test.game.controller;

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
                .addPiece(pfPlayer1.getKing(new BoardPositionImpl(7, 7))).build();

//        PieceMovementStrategy pms = new NormalPieceMovementStrategyFactory()
//                .getQueenMovementStrategy(board.getPieceAtPosition(new BoardPositionImpl(5, 4)).get());
//        pms.getPossibleMoves(board);
        final PieceMovementStrategyFactory pmsf = new NormalPieceMovementStrategyFactory();
        final GameController gameContr = new NormalGameController(board, pmsf, List.of(player1, player2));
        // assertTrue(board.getPieceAtPosition(new BoardPositionImpl(7,
        // 7)).isPresent());
        // System.out.println(gameContr.isWinner(player2));
        System.out.println(gameContr.isWinner(player2));
    }

}
