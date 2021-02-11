package jhaturanga.test.game.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.ClassicGameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.builder.MatchBuilder;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;

class NormalGameTypeMatchTest {

    private Match match;
    private List<Player> players;

    @BeforeEach
    public void init() {
        this.players = new ArrayList<>();
        final Player player1 = new PlayerImpl(PlayerColor.BLACK);
        final Player player2 = new PlayerImpl(PlayerColor.WHITE);
        this.players.add(player1);
        this.players.add(player2);
    }

    @Test
    void testMatch() {
        final MatchBuilder matchBuilder = new MatchBuilderImpl();

        this.match = matchBuilder.gameType(new ClassicGameType(this.players)).players(this.players).build();

        this.match.getBoard().getBoardState().stream().forEach(i -> System.out.println(i.getIdentifier()));

        assertEquals(PieceType.KNIGHT, this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).get().getType());
        assertTrue(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).get(),
                new BoardPositionImpl(0, 2))));
    }

}
