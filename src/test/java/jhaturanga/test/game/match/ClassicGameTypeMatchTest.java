package jhaturanga.test.game.match;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.ClassicGameType;
import jhaturanga.model.game.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.builder.MatchBuilder;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;

class ClassicGameTypeMatchTest {

    private Match match;
    private Player whitePlayer;
    private Player blackPlayer;

    @BeforeEach
    public void init() {
        this.whitePlayer = new PlayerImpl(PlayerColor.WHITE);
        this.blackPlayer = new PlayerImpl(PlayerColor.BLACK);
    }

    @Test
    void testMovementsFromMatch() {
        final MatchBuilder matchBuilder = new MatchBuilderImpl();

        this.match = matchBuilder.gameType(new ClassicGameType(this.whitePlayer, this.blackPlayer))
                .players(List.of(this.whitePlayer, this.blackPlayer)).build();

        // this.match.getBoard().getBoardState().stream().forEach(i ->
        // System.out.println(i.getIdentifier()));

        assertEquals(PieceType.KNIGHT, this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).get().getType());
        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).get(),
                new BoardPositionImpl(0, 2))));

        assertEquals(PieceType.KNIGHT, this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(0, 2)).get().getType());
        assertTrue(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).isEmpty());
    }

    @Test
    void testPawnCaptureFromMatch() {
        final MatchBuilder matchBuilder = new MatchBuilderImpl();

        final GameType gameType = new ClassicGameType(this.whitePlayer, this.blackPlayer);

        this.match = matchBuilder.gameType(gameType).players(List.of(this.whitePlayer, this.blackPlayer)).build();

        System.out.println(gameType.getPieceMovementStrategyFactory()
                .getPieceMovementStrategy(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 1)).get())
                .getPossibleMoves(this.match.getBoard()));

        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 1)).get(),
                new BoardPositionImpl(2, 2))));

        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 2)).get(),
                new BoardPositionImpl(2, 3))));

        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 6)).get(),
                new BoardPositionImpl(3, 5))));
        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 5)).get(),
                new BoardPositionImpl(3, 4))));

        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 4)).get(),
                new BoardPositionImpl(2, 3))));

        assertTrue(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 4)).isEmpty());
        assertEquals(this.blackPlayer, this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 3)).get().getPlayer());
        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 3)).get(),
                new BoardPositionImpl(2, 2))));
    }

    @Test
    void testKnightCaptureFromMatch() {

        final MatchBuilder matchBuilder = new MatchBuilderImpl();

        final GameType gameType = new ClassicGameType(this.whitePlayer, this.blackPlayer);

        this.match = matchBuilder.gameType(gameType).players(List.of(this.whitePlayer, this.blackPlayer)).build();

        // 7 R k B Q K B k R
        // 6 P P P P P P P P
        // 5 x x x x x k x x
        // 4 x x x x x x x x Simulating BlackKnight(Top k) capturing WhiteKnight(Bot k)
        // 3 x x x x k x x x
        // 2 x x x x x x x x
        // 1 P P P P P P P P
        // 0 R x B Q K B k R
        // - 0 1 2 3 4 5 6 7

        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).get(),
                new BoardPositionImpl(2, 2))));
        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 2)).get(),
                new BoardPositionImpl(4, 3))));

        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(6, 7)).get(),
                new BoardPositionImpl(5, 5))));
        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(5, 5)).get(),
                new BoardPositionImpl(4, 3))));
        assertTrue(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(5, 5)).isEmpty());

        assertEquals(this.blackPlayer, this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).get().getPlayer());

        assertFalse(this.match.isCompleted());

        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).get(),
                new BoardPositionImpl(2, 4))));

        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 4)).get(),
                new BoardPositionImpl(3, 2))));

        // Now whitePlayer is under check and moves that do not prevent the king from
        // being under check must return false when invoked

        assertFalse(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(0, 1)).get(),
                new BoardPositionImpl(0, 2))));

        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 1)).get(),
                new BoardPositionImpl(3, 2))));
    }

}
