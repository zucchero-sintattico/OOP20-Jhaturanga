package jhaturanga.test.game.match;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.controllers.match.MovementResult;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.MatchStatusEnum;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.builder.MatchBuilder;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.test.commons.Constants;

class ClassicGameTypeMatchTest {

    private Player whitePlayer;
    private Player blackPlayer;

    @BeforeEach
    public void init() {
        this.whitePlayer = new PlayerImpl(PlayerColor.WHITE, UsersManager.GUEST);
        this.blackPlayer = new PlayerImpl(PlayerColor.BLACK, UsersManager.GUEST);
    }

    @Test
    void testMovementsFromMatch() {
        final MatchBuilder matchBuilder = new MatchBuilderImpl();

        final Match match = matchBuilder
                .gameType(GameTypesEnum.CLASSIC_GAME.getGameType(this.whitePlayer, this.blackPlayer)).build();

        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.ZERO)).get(),
                new BoardPositionImpl(Constants.ZERO, Constants.TWO))).equals(MovementResult.NONE));

        // Controllo che ci siano 32 pezzi
        assertEquals(match.getBoard().getBoardState().size(), Constants.THIRTY_TWO);

        // Controllo che il cavallo sia nella nuova posizione
        assertEquals(PieceType.KNIGHT, match.getBoard()
                .getPieceAtPosition(new BoardPositionImpl(Constants.ZERO, Constants.TWO)).get().getType());

        // Controllo che nella vecchia posizione del cavallo non ci sia più il cavallo
        assertTrue(match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.ZERO)).isEmpty());

        // Muovo il pedino per andare a mangiare il cavallo
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.SIX)).get(),
                new BoardPositionImpl(Constants.ONE, Constants.FIVE))).equals(MovementResult.NONE));

        // Random move for turn purpose
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.SEVEN, Constants.ONE)).get(),
                new BoardPositionImpl(Constants.SEVEN, Constants.THREE))).equals(MovementResult.NONE));

        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.FIVE)).get(),
                new BoardPositionImpl(Constants.ONE, Constants.FOUR))).equals(MovementResult.NONE));
        // Random move for turn purpose
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.SEVEN, Constants.THREE)).get(),
                new BoardPositionImpl(Constants.SEVEN, Constants.FOUR))).equals(MovementResult.NONE));

        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.FOUR)).get(),
                new BoardPositionImpl(Constants.ONE, Constants.THREE))).equals(MovementResult.NONE));

        // Random move for turn purpose
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.SEVEN, Constants.FOUR)).get(),
                new BoardPositionImpl(Constants.SEVEN, Constants.FIVE))).equals(MovementResult.NONE));

        // Controllo di poter mangiare il cavallo
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.THREE)).get(),
                new BoardPositionImpl(Constants.ZERO, Constants.TWO))).equals(MovementResult.NONE));

        // Controllo che ci siano 31 pezzi - il cavallo è stato mangiato
        assertEquals(match.getBoard().getBoardState().size(), Constants.THIRTY_ONE);

        // Controllo che il pedone sia nella nuova posizione
        assertEquals(PieceType.PAWN, match.getBoard()
                .getPieceAtPosition(new BoardPositionImpl(Constants.ZERO, Constants.TWO)).get().getType());

        // Controllo che nella vecchia posizione del pedone non ci sia più il pedone
        assertTrue(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.THREE)).isEmpty());
    }

    @Test
    void testPawnCaptureFromMatch() {

        final MatchBuilder matchBuilder = new MatchBuilderImpl();
        final GameType gameType = GameTypesEnum.CLASSIC_GAME.getGameType(this.whitePlayer, this.blackPlayer);

        final Match match = matchBuilder.gameType(gameType).build();

        // Move white pawn from 2,1 to 2,3
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.TWO, Constants.ONE)).get(),
                new BoardPositionImpl(Constants.TWO, Constants.THREE))).equals(MovementResult.NONE));

        // Move black pawn from 3,6 to 3,4
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.THREE, Constants.SIX)).get(),
                new BoardPositionImpl(Constants.THREE, Constants.FOUR))).equals(MovementResult.NONE));

        // Save a reference to the pawn that is going to be captured
        final Piece capturedPawn = match.getBoard()
                .getPieceAtPosition(new BoardPositionImpl(Constants.TWO, Constants.THREE)).get();

        // Random move
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.SEVEN, Constants.ONE)).get(),
                new BoardPositionImpl(Constants.SEVEN, Constants.THREE))).equals(MovementResult.NONE));

        // Black pawn in 3,4 capture white pawn in 2,3
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.THREE, Constants.FOUR)).get(),
                new BoardPositionImpl(Constants.TWO, Constants.THREE))).equals(MovementResult.NONE));

        assertTrue(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.THREE, Constants.FOUR)).isEmpty());

        assertEquals(this.blackPlayer, match.getBoard()
                .getPieceAtPosition(new BoardPositionImpl(Constants.TWO, Constants.THREE)).get().getPlayer());

        // Check that the piece was really captured and there isn't more present in
        // board
        assertFalse(match.getBoard().contains(capturedPawn));
    }

    @Test
    void testKnightCaptureFromMatch() {

        final MatchBuilder matchBuilder = new MatchBuilderImpl();

        final GameType gameType = GameTypesEnum.CLASSIC_GAME.getGameType(this.whitePlayer, this.blackPlayer);

        final Match match = matchBuilder.gameType(gameType).build();

        // 7 R k B Q K B k R
        // 6 P P P P P P P P
        // 5 x x x x x k x x
        // 4 x x x x x x x x Simulating BlackKnight(Top k) capturing WhiteKnight(Bot k)
        // 3 x x x x k x x x
        // 2 x x x x x x x x
        // 1 P P P P P P P P
        // 0 R x B Q K B k R
        // - 0 1 2 3 4 5 6 7

        // Move white knight from 1,0 to 2,2
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ONE, Constants.ZERO)).get(),
                new BoardPositionImpl(Constants.TWO, Constants.TWO))).equals(MovementResult.NONE));

        // Random move for turn purpose
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.SEVEN, Constants.SIX)).get(),
                new BoardPositionImpl(Constants.SEVEN, Constants.FIVE))).equals(MovementResult.NONE));

        // Move white knight from 2,2 to 4,3
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.TWO, Constants.TWO)).get(),
                new BoardPositionImpl(Constants.FOUR, Constants.THREE))).equals(MovementResult.NONE));

        // Move black knight from 6,7 to 5,5
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.SIX, Constants.SEVEN)).get(),
                new BoardPositionImpl(Constants.FIVE, Constants.FIVE))).equals(MovementResult.NONE));

        // Save a reference to white knight in 4,3 beacuse it's going to be captured
        final Piece knightBeforeBeingCaptured = match.getBoard()
                .getPieceAtPosition(new BoardPositionImpl(Constants.FOUR, Constants.THREE)).get();

        // Random move for turn purpose
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.SEVEN, Constants.ONE)).get(),
                new BoardPositionImpl(Constants.SEVEN, Constants.TWO))).equals(MovementResult.NONE));

        // Black night in 5,5 capture white knight in 4,3
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.FIVE, Constants.FIVE)).get(),
                new BoardPositionImpl(Constants.FOUR, Constants.THREE))).equals(MovementResult.NONE));

        // Controllo che il cavallo nero si sia effettivamente mosso
        assertTrue(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.FIVE, Constants.FIVE)).isEmpty());

        // Controllo che in 4,3 ci sia il cavallo
        assertEquals(PieceType.KNIGHT, match.getBoard()
                .getPieceAtPosition(new BoardPositionImpl(Constants.FOUR, Constants.THREE)).get().getType());

        // Controllo che in 4,3 ci sia il cavallo NERO
        assertEquals(this.blackPlayer, match.getBoard()
                .getPieceAtPosition(new BoardPositionImpl(Constants.FOUR, Constants.THREE)).get().getPlayer());
        // Check that in position 4,3 there is a knight
        assertEquals(PieceType.KNIGHT, match.getBoard()
                .getPieceAtPosition(new BoardPositionImpl(Constants.FOUR, Constants.THREE)).get().getType());

        // Check that in position 4,3 there is a piece of black player
        assertEquals(this.blackPlayer, match.getBoard()
                .getPieceAtPosition(new BoardPositionImpl(Constants.FOUR, Constants.THREE)).get().getPlayer());

        // Assure that white captured knight is now gone from board
        assertFalse(match.getBoard().contains(knightBeforeBeingCaptured));

        // The game is not completed
        assertTrue(match.matchStatus().equals(MatchStatusEnum.NOT_OVER));

        // 7 R k B Q K B x R
        // 6 P P P P P P P P
        // 5 x x x x x x x x
        // 4 x x x x x x x x Simulating BlackKnight(Top k) checking WhitePlayer
        // 3 x x x x x x x x
        // 2 x x x k x x x x
        // 1 P P P P P P P P
        // 0 R x B Q K B k R
        // - 0 1 2 3 4 5 6 7

        // Random move for turn purpose
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.THREE, Constants.ONE)).get(),
                new BoardPositionImpl(Constants.THREE, Constants.TWO))).equals(MovementResult.NONE));

        // Move black knight from 4,3 to 2,4
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.FOUR, Constants.THREE)).get(),
                new BoardPositionImpl(Constants.TWO, Constants.FOUR))).equals(MovementResult.NONE));

        // Random move for turn purpose
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.FIVE, Constants.ONE)).get(),
                new BoardPositionImpl(Constants.FIVE, Constants.TWO))).equals(MovementResult.NONE));

        // Move black knight from 2,4 to 3,2 and make check to white player
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.TWO, Constants.FOUR)).get(),
                new BoardPositionImpl(Constants.THREE, Constants.TWO))).equals(MovementResult.NONE));

        // Non è uno scacco matto
        assertFalse(match.getGameController().checkGameStatus(this.blackPlayer).equals(MatchStatusEnum.CHECKMATE));

        // Check that white player is under check
        assertTrue(match.getGameController().isInCheck(whitePlayer));

        // Check that's not a draw
        assertFalse(match.getGameController().checkGameStatus(this.blackPlayer).equals(MatchStatusEnum.DRAW));

        // Check that's not endgame
        assertTrue(match.getGameController().checkGameStatus(this.blackPlayer).equals(MatchStatusEnum.NOT_OVER));

        // Now whitePlayer is under check and moves that do not prevent the king from
        // being under check must return false when invoked
        assertFalse(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.ZERO, Constants.ONE)).get(),
                new BoardPositionImpl(Constants.ZERO, Constants.TWO))).equals(MovementResult.NONE));

        // This move saves the king, it should be possible
        assertTrue(!match.move(new MovementImpl(
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(Constants.FOUR, Constants.ONE)).get(),
                new BoardPositionImpl(Constants.THREE, Constants.TWO))).equals(MovementResult.NONE));

    }

}
