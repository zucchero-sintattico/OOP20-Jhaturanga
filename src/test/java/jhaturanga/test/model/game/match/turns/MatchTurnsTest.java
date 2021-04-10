package jhaturanga.test.model.game.match.turns;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovementImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.player.pair.PlayerPair;
import jhaturanga.model.player.pair.PlayerPairImpl;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.timer.TimerFactoryImpl;
import jhaturanga.model.user.management.UsersManager;

class MatchTurnsTest {

    private Player whitePlayer;
    private Player blackPlayer;

    @BeforeEach
    public void init() {
        this.whitePlayer = new PlayerImpl(PlayerColor.WHITE, UsersManager.GUEST);
        this.blackPlayer = new PlayerImpl(PlayerColor.BLACK, UsersManager.GUEST);
    }

    @Test
    void testMatchPlayersTurns() {
        final PlayerPair players = new PlayerPairImpl(this.whitePlayer, this.blackPlayer);
        final Timer timer = new TimerFactoryImpl().equalTimer(List.of(whitePlayer, blackPlayer), 10);
        final Match match = new MatchImpl(GameType.CLASSIC.getGameInstance(players), timer);
        match.start();
        /**
         * So at this point White player should be starting
         */

        assertFalse(
                match.move(new PieceMovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(0, 1)).get(),
                        new BoardPositionImpl(0, 3))).equals(MovementResult.INVALID_MOVE));
        /**
         * I try moving a piece of the same player (in this case White Player's), it
         * should return false and not let me do the move
         */

        assertTrue(
                match.move(new PieceMovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 1)).get(),
                        new BoardPositionImpl(1, 3))).equals(MovementResult.INVALID_MOVE));

    }
}
