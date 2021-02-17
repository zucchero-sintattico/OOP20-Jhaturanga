package jhaturanga.test.game.pieceswapvariant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.builder.MatchBuilder;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.PieceType;

class PieceSwappingVariantTest {

    @Test
    void pieceSwapBasicTest() {
        final MatchBuilder matchBuilder = new MatchBuilderImpl();
        final GameType gameType = GameTypesEnum.PIECE_SWAP_VARIANT.getNewGameType();
        final Match match = matchBuilder.gameType(gameType).players(gameType.getGameController().getPlayers()).build();

        /**
         * In this variant every time you move a piece, if it's a Rook,Knight or Bishop
         * it changes it's PieceType. Rook -> Bishop, Bishop -> Knight, Knight -> Rook.
         */

        // White Knight moves
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).get(),
                new BoardPositionImpl(2, 2))));

        // The Knight is now a Rook
        assertEquals(PieceType.ROOK, match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 2)).get().getType());

        // Random black move for turn purpose
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(0, 6)).get(),
                new BoardPositionImpl(0, 5))));

        // The Rook moves
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 2)).get(),
                new BoardPositionImpl(2, 4))));

        // The Rook is now a Bishop
        assertEquals(PieceType.BISHOP,
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 4)).get().getType());

        // Random black move for turn purpose
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(0, 5)).get(),
                new BoardPositionImpl(0, 4))));

        // The Bishop moves
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 4)).get(),
                new BoardPositionImpl(3, 3))));

        // The Bishop is now a Knight
        assertEquals(PieceType.KNIGHT,
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 3)).get().getType());

    }

}
