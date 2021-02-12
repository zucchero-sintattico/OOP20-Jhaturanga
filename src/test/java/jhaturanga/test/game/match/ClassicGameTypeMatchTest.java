package jhaturanga.test.game.match;

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
    void testCaptureFromMatch() {
        final MatchBuilder matchBuilder = new MatchBuilderImpl();

        final GameType gameType = new ClassicGameType(this.whitePlayer, this.blackPlayer);

        this.match = matchBuilder.gameType(gameType).players(List.of(this.whitePlayer, this.blackPlayer)).build();

        // this.match.getBoard().getBoardState().stream().forEach(i ->
        // System.out.println(i.getIdentifier()));

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
    }

}
