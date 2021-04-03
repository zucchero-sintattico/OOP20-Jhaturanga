package jhaturanga.test.game.pawnmovementvariant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.builder.MatchBuilder;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.movement.PieceMovementImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.player.PlayerPair;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.timer.TimerFactoryImpl;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.test.commons.Constants;

class PawnMovementVariantTest {

    private Player whitePlayer;
    private Player blackPlayer;

    @BeforeEach
    public void init() {
        this.whitePlayer = new PlayerImpl(PlayerColor.WHITE, UsersManager.GUEST);
        this.blackPlayer = new PlayerImpl(PlayerColor.BLACK, UsersManager.GUEST);
    }

    @Test
    void pawnVariantMovements() {
        final PlayerPair players = new PlayerPair(this.whitePlayer, this.blackPlayer);
        final MatchBuilder matchBuilder = new MatchBuilderImpl();
        final Timer timer = new TimerFactoryImpl().equalTimer(List.of(whitePlayer, blackPlayer), 10);
        final Match match = matchBuilder.timer(timer).gameType(GameType.PAWN_MOVEMENT_VARIANT.getGameInstance(players))
                .build();
        match.start();
        /**
         * In this variant pawns are able to move in every direction except for the ones
         * in the opposite direction of the normal Pawn's For instance: white pawns can
         * move left,up-left,up,up-right and right. But not bottom, bottom-right and
         * bottom-left. In this variant pawns do not have the two-step-first forward
         * move.
         */

        // White pawn doing top right move
        assertFalse(match.move(new PieceMovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.TWO, Constants.ONE)).get(),
                new BoardPositionImpl(Constants.THREE, Constants.TWO))).equals(MovementResult.INVALID_MOVE));

        // Black pawn can't do double-step forward move
        assertTrue(match.move(new PieceMovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.SIX)).get(),
                new BoardPositionImpl(Constants.ONE, Constants.FOUR))).equals(MovementResult.INVALID_MOVE));

        // Let's move black pawn bottom left
        assertFalse(match.move(new PieceMovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.SIX)).get(),
                new BoardPositionImpl(Constants.ZERO, Constants.FIVE))).equals(MovementResult.INVALID_MOVE));
    }

}
