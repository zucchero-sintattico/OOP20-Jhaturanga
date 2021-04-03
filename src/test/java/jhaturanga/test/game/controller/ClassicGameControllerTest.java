package jhaturanga.test.game.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.controller.ClassicGameController;
import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.match.GameStatus;
import jhaturanga.model.piece.movement.ClassicNoCastlingPieceMovementStrategies;
import jhaturanga.model.piece.movement.ClassicWithCastlingPieceMovementStrategies;
import jhaturanga.model.piece.movement.PieceMovementStrategies;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.player.PlayerPair;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.test.commons.Constants;

class ClassicGameControllerTest {

    private Player player1;
    private Player player2;

    @BeforeEach
    public void init() {
        player1 = new PlayerImpl(PlayerColor.WHITE, UsersManager.GUEST);
        player2 = new PlayerImpl(PlayerColor.BLACK, UsersManager.GUEST);
    }

    @Test
    void testIsInCheck() {
        final BoardBuilder bb = new BoardBuilderImpl();
        final Board board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getRook(new BoardPositionImpl(Constants.SIX, Constants.ONE)))
                .addPiece(player2.getPieceFactory().getQueen(new BoardPositionImpl(Constants.SIX, Constants.SIX)))
                .addPiece(player1.getPieceFactory().getRook(new BoardPositionImpl(Constants.ONE, Constants.SIX)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.SEVEN, Constants.SEVEN)))
                .build();

        final PieceMovementStrategies pmsf = new ClassicWithCastlingPieceMovementStrategies();
        final GameController gameController = new ClassicGameController(board, pmsf, new PlayerPair(player1, player2));

        // Check that in position 7,7 there is a piece
        assertTrue(board.getPieceAtPosition(new BoardPositionImpl(Constants.SEVEN, Constants.SEVEN)).isPresent());
        // Check that the game is not finished and there is no winner or draw
        assertTrue(gameController.getGameStatus(player1).equals(GameStatus.ACTIVE));
        assertFalse(gameController.getGameStatus(player1).equals(GameStatus.DRAW));
        assertFalse(gameController.isWinner(player2));

        // Check that player 1 is under check
        assertTrue(gameController.isInCheck(player1));
    }

    @Test
    void testWinner() {

        final BoardBuilder bb = new BoardBuilderImpl();
        final Board board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getRook(new BoardPositionImpl(Constants.TWO, Constants.TWO)))
                .addPiece(player2.getPieceFactory().getKing(new BoardPositionImpl(Constants.SIX, Constants.SIX)))
                .addPiece(player2.getPieceFactory().getQueen(new BoardPositionImpl(Constants.TWO, Constants.ONE)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.TWO, Constants.ZERO)))
                .build();

        final PieceMovementStrategies pmsf = new ClassicNoCastlingPieceMovementStrategies();
        final GameController gameController = new ClassicGameController(board, pmsf, new PlayerPair(player1, player2));

        // Check that player1 is in check
        assertTrue(gameController.isInCheck(player1));

        // Check that's not a draw
        assertFalse(gameController.getGameStatus(player1).equals(GameStatus.DRAW));

        assertTrue(gameController.isWinner(player2));

        // Check that the game is over
        assertFalse(gameController.getGameStatus(player1).equals(GameStatus.ACTIVE));
    }

    @Test
    void testParticularCases() {
        // Another draw test
        final BoardBuilder bb = new BoardBuilderImpl();
        Board board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getKing(new BoardPositionImpl(Constants.TWO, Constants.SEVEN)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.TWO, Constants.FIVE)))
                .addPiece(player1.getPieceFactory().getPawn(new BoardPositionImpl(Constants.TWO, Constants.SIX)))
                .build();

        final PieceMovementStrategies pmsf = new ClassicNoCastlingPieceMovementStrategies();
        GameController gameController = new ClassicGameController(board, pmsf, new PlayerPair(player1, player2));

        assertTrue(gameController.getGameStatus(player2).equals(GameStatus.DRAW));
        assertFalse(gameController.isWinner(player1));
        assertFalse(gameController.isWinner(player2));
        assertFalse(gameController.getGameStatus(player2).equals(GameStatus.ACTIVE));

        // Another draw test
        final BoardBuilder bb1 = new BoardBuilderImpl();
        board = bb1.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getRook(new BoardPositionImpl(1, 1)))
                .addPiece(player2.getPieceFactory().getPawn(new BoardPositionImpl(Constants.FIVE, Constants.THREE)))
                .addPiece(player2.getPieceFactory().getKing(new BoardPositionImpl(Constants.FIVE, Constants.FOUR)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.FIVE, Constants.TWO)))
                .build();

        gameController = new ClassicGameController(board, pmsf, new PlayerPair(player1, player2));

        assertEquals(gameController.getGameStatus(player1), GameStatus.DRAW);

        assertFalse(gameController.isWinner(player1));
        assertFalse(gameController.isWinner(player2));
        assertFalse(gameController.getGameStatus(player1).equals(GameStatus.ACTIVE));

        // Another draw test
        final BoardBuilder bb2 = new BoardBuilderImpl();
        board = bb2.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getRook(new BoardPositionImpl(1, 1)))
                .addPiece(player2.getPieceFactory().getPawn(new BoardPositionImpl(Constants.FIVE, Constants.THREE)))
                .addPiece(player2.getPieceFactory().getKing(new BoardPositionImpl(Constants.FIVE, Constants.FOUR)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.FIVE, Constants.TWO)))
                .build();

        gameController = new ClassicGameController(board, pmsf, new PlayerPair(player1, player2));

        assertTrue(gameController.getGameStatus(player1).equals(GameStatus.DRAW));
        assertFalse(gameController.isWinner(player1));
        assertFalse(gameController.isWinner(player2));
        assertFalse(gameController.getGameStatus(player1).equals(GameStatus.ACTIVE));
    }

    @Test
    void testDrawByStaleMate() {
        final BoardBuilder bb = new BoardBuilderImpl();
        final Board board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getKing(new BoardPositionImpl(Constants.TWO, Constants.SEVEN)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.TWO, Constants.FIVE)))
                .addPiece(player1.getPieceFactory().getPawn(new BoardPositionImpl(Constants.TWO, Constants.SIX)))
                .build();

        final PieceMovementStrategies pmsf = new ClassicNoCastlingPieceMovementStrategies();
        final GameController gameController = new ClassicGameController(board, pmsf, new PlayerPair(player1, player2));

        // Check that the game ended in a draw
        assertTrue(gameController.getGameStatus(player2).equals(GameStatus.DRAW));

        // Assure that there is no winner
        assertFalse(gameController.isWinner(player1));
        assertFalse(gameController.isWinner(player2));

        // Check that the game is seen as over
        assertFalse(gameController.getGameStatus(player2).equals(GameStatus.ACTIVE));

    }

    @Test
    void testDrawKingVsKing() {
        BoardBuilder bb = new BoardBuilderImpl();
        // Draw by King vs King
        Board board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getKing(new BoardPositionImpl(Constants.TWO, Constants.SEVEN)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.ZERO, Constants.ZERO)))
                .build();

        PieceMovementStrategies pmsf = new ClassicWithCastlingPieceMovementStrategies();
        GameController gameContr = new ClassicGameController(board, pmsf, new PlayerPair(player1, player2));

        // Check that the game ended in a draw
        assertTrue(gameContr.getGameStatus(player1).equals(GameStatus.DRAW));

        // Assure that there is no winner
        assertFalse(gameContr.isWinner(player1));
        assertFalse(gameContr.isWinner(player2));

        // Check that the game is seen as over
        assertFalse(gameContr.getGameStatus(player1).equals(GameStatus.ACTIVE));

        bb = new BoardBuilderImpl();
        // Draw by King vs King and Bishop
        board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getKing(new BoardPositionImpl(Constants.TWO, Constants.SEVEN)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.ZERO, Constants.ZERO)))
                .addPiece(player1.getPieceFactory().getBishop(new BoardPositionImpl(Constants.ONE, Constants.ZERO)))
                .build();

        pmsf = new ClassicWithCastlingPieceMovementStrategies();
        gameContr = new ClassicGameController(board, pmsf, new PlayerPair(player1, player2));

        // Check that the game ended in a draw
        assertTrue(gameContr.getGameStatus(player1).equals(GameStatus.DRAW));

        // Assure that there is no winner
        assertFalse(gameContr.isWinner(player1));
        assertFalse(gameContr.isWinner(player2));

        // Check that the game is seen as over
        assertFalse(gameContr.getGameStatus(player1).equals(GameStatus.ACTIVE));

    }

    @Test
    void testDrawKingVsKingAndBishop() {
        final BoardBuilder bb = new BoardBuilderImpl();

        // Draw by King vs King and Bishop
        final Board board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getKing(new BoardPositionImpl(Constants.TWO, Constants.SEVEN)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.ZERO, Constants.ZERO)))
                .addPiece(player1.getPieceFactory().getBishop(new BoardPositionImpl(Constants.ONE, Constants.ZERO)))
                .build();

        final PieceMovementStrategies pmsf = new ClassicWithCastlingPieceMovementStrategies();
        final GameController gameContr = new ClassicGameController(board, pmsf, new PlayerPair(player1, player2));

        // Check that the game ended in a draw
        assertTrue(gameContr.getGameStatus(player1).equals(GameStatus.DRAW));

        // Assure that there is no winner
        assertFalse(gameContr.isWinner(player1));
        assertFalse(gameContr.isWinner(player2));

        // Check that the game is seen as over
        assertFalse(gameContr.getGameStatus(player1).equals(GameStatus.ACTIVE));

    }

    @Test
    void testDrawKingVsKingAndKnight() {
        final BoardBuilder bb = new BoardBuilderImpl();

        // Draw by King vs King and Bishop
        final Board board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getKing(new BoardPositionImpl(Constants.TWO, Constants.SEVEN)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.ZERO, Constants.ZERO)))
                .addPiece(player1.getPieceFactory().getKnight(new BoardPositionImpl(Constants.ONE, Constants.ZERO)))
                .build();

        final PieceMovementStrategies pmsf = new ClassicWithCastlingPieceMovementStrategies();
        final GameController gameContr = new ClassicGameController(board, pmsf, new PlayerPair(player1, player2));

        // Check that the game ended in a draw
        assertTrue(gameContr.getGameStatus(player1).equals(GameStatus.DRAW));

        // Assure that there is no winner
        assertFalse(gameContr.isWinner(player1));
        assertFalse(gameContr.isWinner(player2));

        // Check that the game is seen as over
        assertFalse(gameContr.getGameStatus(player1).equals(GameStatus.ACTIVE));

    }

    @Test
    void testDrawKingAndBishopVsKingAndBishop() {
        final BoardBuilder bb = new BoardBuilderImpl();

        // Draw by King vs King and Bishop
        final Board board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getKing(new BoardPositionImpl(Constants.TWO, Constants.SEVEN)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.ZERO, Constants.ZERO)))
                .addPiece(player1.getPieceFactory().getBishop(new BoardPositionImpl(Constants.ONE, Constants.ZERO)))
                .addPiece(player2.getPieceFactory().getBishop(new BoardPositionImpl(Constants.FIVE, Constants.ZERO)))
                .build();

        final PieceMovementStrategies pmsf = new ClassicWithCastlingPieceMovementStrategies();
        final GameController gameContr = new ClassicGameController(board, pmsf, new PlayerPair(player1, player2));

        // Check that the game ended in a draw
        assertTrue(gameContr.getGameStatus(player1).equals(GameStatus.DRAW));

        // Assure that there is no winner
        assertFalse(gameContr.isWinner(player1));
        assertFalse(gameContr.isWinner(player2));

        // Check that the game is seen as over
        assertFalse(gameContr.getGameStatus(player1).equals(GameStatus.ACTIVE));

    }

    @Test
    void testDrawEdgeCases() {
        BoardBuilder bb = new BoardBuilderImpl();

        // Draw by King and Bishop vs King and Bishop
        Board board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getKing(new BoardPositionImpl(Constants.TWO, Constants.SEVEN)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.ZERO, Constants.ZERO)))
                .addPiece(player1.getPieceFactory().getBishop(new BoardPositionImpl(Constants.ONE, Constants.ZERO)))
                .addPiece(player1.getPieceFactory().getBishop(new BoardPositionImpl(Constants.FIVE, Constants.ZERO)))
                .build();

        PieceMovementStrategies pmsf = new ClassicWithCastlingPieceMovementStrategies();
        GameController gameContr = new ClassicGameController(board, pmsf, new PlayerPair(player1, player2));

        // Check that the game did not end in a draw
        assertFalse(gameContr.getGameStatus(player1).equals(GameStatus.DRAW));

        // Assure that there is no winner
        assertFalse(gameContr.isWinner(player1));
        assertFalse(gameContr.isWinner(player2));

        // Check that the game is not seen as over
        assertTrue(gameContr.getGameStatus(player1).equals(GameStatus.ACTIVE));

        // Draw by King vs King and Bishop
        bb = new BoardBuilderImpl();
        board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getKing(new BoardPositionImpl(Constants.TWO, Constants.SEVEN)))
                .addPiece(player1.getPieceFactory().getKing(new BoardPositionImpl(Constants.ZERO, Constants.ZERO)))
                .addPiece(player1.getPieceFactory().getBishop(new BoardPositionImpl(Constants.ONE, Constants.ZERO)))
                .addPiece(player1.getPieceFactory().getKnight(new BoardPositionImpl(Constants.FIVE, Constants.ZERO)))
                .build();

        pmsf = new ClassicWithCastlingPieceMovementStrategies();
        gameContr = new ClassicGameController(board, pmsf, new PlayerPair(player1, player2));

        // Check that the game did not end in a draw
        assertFalse(gameContr.getGameStatus(player1).equals(GameStatus.DRAW));

        // Assure that there is no winner
        assertFalse(gameContr.isWinner(player1));
        assertFalse(gameContr.isWinner(player2));

        // Check that the game is not seen as over
        assertTrue(gameContr.getGameStatus(player1).equals(GameStatus.ACTIVE));

    }

}
