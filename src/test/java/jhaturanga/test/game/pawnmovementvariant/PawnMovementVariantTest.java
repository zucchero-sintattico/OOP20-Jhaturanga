package jhaturanga.test.game.pawnmovementvariant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.builder.MatchBuilder;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
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
        final MatchBuilder matchBuilder = new MatchBuilderImpl();

        final Match match = matchBuilder
                .gameType(GameTypesEnum.PAWN_MOVEMENT_VARIANT.getGameType(this.whitePlayer, this.blackPlayer))
                .build();

        /**
         * In this variant pawns are able to move in every direction except for the ones
         * in the opposite direction of the normal Pawn's For instance: white pawns can
         * move left,up-left,up,up-right and right. But not bottom, bottom-right and
         * bottom-left. In this variant pawns do not have the two-step-first forward
         * move.
         */

        // White pawn doing top right move
        assertTrue(match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.TWO, Constants.ONE)).get(),
                new BoardPositionImpl(Constants.THREE, Constants.TWO))));

        // Black pawn can't do double-step forward move
        assertFalse(match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.SIX)).get(),
                new BoardPositionImpl(Constants.ONE, Constants.FOUR))));

        // Let's move black pawn bottom left
        assertTrue(match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.SIX)).get(),
                new BoardPositionImpl(Constants.ZERO, Constants.FIVE))));
    }

}
