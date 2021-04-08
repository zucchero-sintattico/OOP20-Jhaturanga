package jhaturanga.test.model.game.movement;

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
import jhaturanga.model.piece.movement.ClassicWithCastlingPieceMovementStrategies;
import jhaturanga.model.piece.movement.MovementStrategy;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.test.model.commons.Constants;

class MovementTest {

    private Player player1;
    private Player player2;

    @BeforeEach
    public void init() {
        player1 = new PlayerImpl(PlayerColor.WHITE, UsersManager.GUEST);
        player2 = new PlayerImpl(PlayerColor.BLACK, UsersManager.GUEST);
    }

    @Test
    void testQueenMovement() {
        final BoardBuilder bb = new BoardBuilderImpl();
        final Board board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player2.getPieceFactory().getPawn(new BoardPositionImpl(Constants.THREE, Constants.SIX)))
                .addPiece(player1.getPieceFactory().getQueen(new BoardPositionImpl(Constants.FIVE, Constants.FOUR)))
                .build();

        MovementStrategy pms = new ClassicWithCastlingPieceMovementStrategies().getPieceMovementStrategy(
                board.getPieceAtPosition(new BoardPositionImpl(Constants.FIVE, Constants.FOUR)).get());
        pms.getPossibleMoves(board);

        if (pms.getPossibleMoves(board).contains(new BoardPositionImpl(Constants.FOUR, Constants.FOUR))) {
            board.getPieceAtPosition(new BoardPositionImpl(Constants.FIVE, Constants.FOUR))
                    .ifPresent(x -> x.setPosition(new BoardPositionImpl(Constants.FOUR, Constants.FOUR)));
        }

        pms = new ClassicWithCastlingPieceMovementStrategies().getPieceMovementStrategy(
                board.getPieceAtPosition(new BoardPositionImpl(Constants.FOUR, Constants.FOUR)).get());

        assertTrue(board.getPieceAtPosition(new BoardPositionImpl(Constants.FOUR, Constants.FOUR)).isPresent());
        assertEquals(Constants.TWENTY_SEVEN, pms.getPossibleMoves(board).size());

        board.getPieceAtPosition(new BoardPositionImpl(Constants.FOUR, Constants.FOUR))
                .ifPresent(x -> x.setPosition(new BoardPositionImpl(Constants.ONE, Constants.SEVEN)));

        assertEquals(PieceType.QUEEN,
                board.getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.SEVEN)).get().getType());
    }

    /**
     * This test is used to verify that the Pawns movements are correct for the
     * NormalGame GameType.
     */

    @Test
    void testPawnMovement() {
        final BoardBuilder bb = new BoardBuilderImpl();
        final Board board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player1.getPieceFactory().getPawn(new BoardPositionImpl(Constants.FOUR, Constants.ONE)))
                .addPiece(player2.getPieceFactory().getPawn(new BoardPositionImpl(Constants.FIVE, Constants.TWO)))
                .addPiece(player2.getPieceFactory().getPawn(new BoardPositionImpl(Constants.THREE, Constants.TWO)))
                .build();

        final MovementStrategy pms = new ClassicWithCastlingPieceMovementStrategies().getPieceMovementStrategy(
                board.getPieceAtPosition(new BoardPositionImpl(Constants.FOUR, Constants.ONE)).get());

        // This pawn can capture in upper sx, upper dx, go upfront by one and by two
        // because it's in original position
        assertEquals(Set.of(new BoardPositionImpl(Constants.THREE, Constants.TWO),
                new BoardPositionImpl(Constants.FOUR, Constants.TWO),
                new BoardPositionImpl(Constants.FIVE, Constants.TWO),
                new BoardPositionImpl(Constants.FOUR, Constants.THREE)), pms.getPossibleMoves(board));
    }

    /**
     * This test is used to verify that the Rook's movements are correct for the
     * NormalGame GameType.
     */

    @Test
    void testRookMovement() {
        final BoardBuilder bb = new BoardBuilderImpl();
        final Board board = bb.columns(Constants.EIGHT).rows(Constants.EIGHT)
                .addPiece(player1.getPieceFactory().getRook(new BoardPositionImpl(Constants.FOUR, Constants.FOUR)))
                .addPiece(player2.getPieceFactory().getPawn(new BoardPositionImpl(Constants.FOUR, Constants.SIX)))
                .addPiece(player1.getPieceFactory().getPawn(new BoardPositionImpl(Constants.FOUR, Constants.THREE)))
                .build();

        final MovementStrategy pms = new ClassicWithCastlingPieceMovementStrategies().getPieceMovementStrategy(
                board.getPieceAtPosition(new BoardPositionImpl(Constants.FOUR, Constants.FOUR)).get());

        assertFalse(pms.getPossibleMoves(board).contains(new BoardPositionImpl(Constants.FOUR, Constants.SEVEN)));
        assertTrue(pms.getPossibleMoves(board).contains(new BoardPositionImpl(Constants.FOUR, Constants.SIX)));
        assertFalse(pms.getPossibleMoves(board).contains(new BoardPositionImpl(Constants.FOUR, Constants.THREE)));
    }

}
