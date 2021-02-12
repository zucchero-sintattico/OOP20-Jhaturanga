package jhaturanga.test.milleniumbug;

import static org.junit.Assert.assertFalse;
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
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;

class MilleniumBug {

    private Player whitePlayer;
    private Player blackPlayer;

    @BeforeEach
    public void init() {
        this.whitePlayer = new PlayerImpl(PlayerColor.WHITE);
        this.blackPlayer = new PlayerImpl(PlayerColor.BLACK);
    }

    @Test
    void testKnightCaptureFromMatch() {

        this.whitePlayer = new PlayerImpl(PlayerColor.WHITE);
        this.blackPlayer = new PlayerImpl(PlayerColor.BLACK);

        final MatchBuilder matchBuilder = new MatchBuilderImpl();

        final GameType gameType = new ClassicGameType(this.whitePlayer, this.blackPlayer);

        final Match match = matchBuilder.gameType(gameType).players(List.of(this.whitePlayer, this.blackPlayer)).build();

        // 7 R k B Q K B k R
        // 6 P P P P P P P P
        // 5 x x x x x k x x
        // 4 x x x x x x x x Simulating BlackKnight(Top k) capturing WhiteKnight(Bot k)
        // 3 x x x x k x x x
        // 2 x x x x x x x x
        // 1 P P P P P P P P
        // 0 R x B Q K B k R
        // - 0 1 2 3 4 5 6 7

        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).get(),
                new BoardPositionImpl(2, 2))));
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 2)).get(),
                new BoardPositionImpl(4, 3))));

        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(6, 7)).get(),
                new BoardPositionImpl(5, 5))));

        final Piece knightBeforeBeingCaptured = match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).get();

        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(5, 5)).get(),
                new BoardPositionImpl(4, 3))));
        assertTrue(match.getBoard().getPieceAtPosition(new BoardPositionImpl(5, 5)).isEmpty());

        // assertEquals(this.blackPlayer, this.match.getBoard().getPieceAtPosition(new
        // BoardPositionImpl(4, 3)).get().getPlayer());
        assertFalse(match.getBoard().contains(knightBeforeBeingCaptured));
        assertFalse(match.isCompleted());

        // 7 R k B Q K B x R
        // 6 P P P P P P P P
        // 5 x x x x x x x x
        // 4 x x x x x x x x Simulating BlackKnight(Top k) checking WhitePlayer
        // 3 x x x x x x x x
        // 2 x x x k x x x x
        // 1 P P P P P P P P
        // 0 R x B Q K B k R
        // - 0 1 2 3 4 5 6 7

        assertTrue(match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).isPresent());
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).get(),
                new BoardPositionImpl(2, 4))));

        assertTrue(match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 4)).isPresent());
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 1)).get(),
                new BoardPositionImpl(4, 2))));

        assertTrue(match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 4)).isPresent());
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 4)).get(),
                new BoardPositionImpl(3, 2))));

        // assertTrue(gameType.getGameController().isInCheck(this.whitePlayer));

        assertTrue(match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 2)).isPresent());

        assertFalse(match.getBoard().getBoardState().contains(knightBeforeBeingCaptured));
        System.out.println("PRINTO PRIMA DELL'ULTIMO ASSERT");
        // match.getBoard().getBoardState().stream().forEach(i ->
        // System.out.println(i.getIdentifier()));
        assertTrue(match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 1)).isEmpty());
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(5, 0)).get(),
                new BoardPositionImpl(3, 2))));
    }

}
