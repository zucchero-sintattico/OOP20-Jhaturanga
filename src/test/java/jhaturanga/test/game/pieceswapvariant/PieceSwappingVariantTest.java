package jhaturanga.test.game.pieceswapvariant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.builder.MatchBuilder;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.test.commons.Constants;

class PieceSwappingVariantTest {

    private Player whitePlayer;
    private Player blackPlayer;

    @BeforeEach
    public void init() {
        this.whitePlayer = new PlayerImpl(PlayerColor.WHITE, UsersManager.GUEST);
        this.blackPlayer = new PlayerImpl(PlayerColor.BLACK, UsersManager.GUEST);
    }

    @Test
    void pieceSwapBasicTest() {
        final MatchBuilder matchBuilder = new MatchBuilderImpl();
        final GameType gameType = GameTypesEnum.PIECE_SWAP_VARIANT.getNewGameType(this.whitePlayer, this.blackPlayer);
        final Match match = matchBuilder.gameType(gameType).build();

        /**
         * In this variant every time you move a piece, if it's a Rook,Knight or Bishop
         * it changes it's PieceType. Rook -> Bishop, Bishop -> Knight, Knight -> Rook.
         */

        // White Knight moves
        assertTrue(match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.ZERO)).get(),
                new BoardPositionImpl(Constants.TWO, Constants.TWO))));

        // The Knight is now a Rook
        assertEquals(PieceType.ROOK, match.getBoard()
                .getPieceAtPosition(new BoardPositionImpl(Constants.TWO, Constants.TWO)).get().getType());

        // Random black move for turn purpose
        assertTrue(match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ZERO, Constants.SIX)).get(),
                new BoardPositionImpl(Constants.ZERO, Constants.FIVE))));

        // The Rook moves
        assertTrue(match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.TWO, Constants.TWO)).get(),
                new BoardPositionImpl(Constants.TWO, Constants.FOUR))));

        // The Rook is now a Bishop
        assertEquals(PieceType.BISHOP, match.getBoard()
                .getPieceAtPosition(new BoardPositionImpl(Constants.TWO, Constants.FOUR)).get().getType());

        // Random black move for turn purpose
        assertTrue(match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ZERO, Constants.FIVE)).get(),
                new BoardPositionImpl(Constants.ZERO, Constants.FOUR))));

        // The Bishop moves
        assertTrue(match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.TWO, Constants.FOUR)).get(),
                new BoardPositionImpl(Constants.THREE, Constants.THREE))));

        // The Bishop is now a Knight
        assertEquals(PieceType.KNIGHT, match.getBoard()
                .getPieceAtPosition(new BoardPositionImpl(Constants.THREE, Constants.THREE)).get().getType());

    }

}
