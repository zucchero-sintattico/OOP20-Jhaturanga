package jhaturanga.test.game.match;

import static org.junit.Assert.assertFalse;
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
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.user.management.UsersManager;

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
                .gameType(GameTypesEnum.CLASSIC_GAME.getNewGameType(this.whitePlayer, this.blackPlayer)).build();

        // match.getBoard().getBoardState().stream().forEach(i ->
        // System.out.println(i.getIdentifier()));

        assertEquals(PieceType.KNIGHT,
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).get().getType());

        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).get(),
                new BoardPositionImpl(0, 2))));

        // Controllo che ci siano 32 pezzi
        assertEquals(match.getBoard().getBoardState().size(), 32);

        // Controllo che il cavallo sia nella nuova posizione
        assertEquals(PieceType.KNIGHT,
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(0, 2)).get().getType());

        // Controllo che nella vecchia posizione del cavallo non ci sia più il cavallo
        assertTrue(match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).isEmpty());

        // Muovo il pedino per andare a mangiare il cavallo
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 6)).get(),
                new BoardPositionImpl(1, 5))));

        // Random move for turn purpose
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(7, 1)).get(),
                new BoardPositionImpl(7, 3))));

        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 5)).get(),
                new BoardPositionImpl(1, 4))));
        // Random move for turn purpose
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(7, 3)).get(),
                new BoardPositionImpl(7, 4))));

        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 4)).get(),
                new BoardPositionImpl(1, 3))));

        // Random move for turn purpose
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(7, 4)).get(),
                new BoardPositionImpl(7, 5))));

        // Controllo di poter mangiare il cavallo
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 3)).get(),
                new BoardPositionImpl(0, 2))));

        // Controllo che ci siano 31 pezzi - il cavallo è stato mangiato
        assertEquals(match.getBoard().getBoardState().size(), 31);

        // Controllo che il pedone sia nella nuova posizione
        assertEquals(PieceType.PAWN, match.getBoard().getPieceAtPosition(new BoardPositionImpl(0, 2)).get().getType());

        // Controllo che nella vecchia posizione del pedone non ci sia più il pedone
        assertTrue(match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 3)).isEmpty());
    }

    @Test
    void testPawnCaptureFromMatch() {

        final MatchBuilder matchBuilder = new MatchBuilderImpl();
        final GameType gameType = GameTypesEnum.CLASSIC_GAME.getNewGameType(this.whitePlayer, this.blackPlayer);

        final Match match = matchBuilder.gameType(gameType).build();

        // Move white pawn from 2,1 to 2,3
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 1)).get(),
                new BoardPositionImpl(2, 3))));

        // Move black pawn from 3,6 to 3,4
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 6)).get(),
                new BoardPositionImpl(3, 4))));

        // Save a reference to the pawn that is going to be captured
        final Piece capturedPawn = match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 3)).get();

        // Random move
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(7, 1)).get(),
                new BoardPositionImpl(7, 3))));

        // Black pawn in 3,4 capture white pawn in 2,3
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 4)).get(),
                new BoardPositionImpl(2, 3))));

        assertTrue(match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 4)).isEmpty());

        assertEquals(this.blackPlayer,
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 3)).get().getPlayer());

        // Check that the piece was really captured and there isn't more present in
        // board
        assertFalse(match.getBoard().contains(capturedPawn));
    }

    @Test
    void testKnightCaptureFromMatch() {

        final MatchBuilder matchBuilder = new MatchBuilderImpl();

        final GameType gameType = GameTypesEnum.CLASSIC_GAME.getNewGameType(this.whitePlayer, this.blackPlayer);

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
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).get(),
                new BoardPositionImpl(2, 2))));

        // Random move for turn purpose
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(7, 6)).get(),
                new BoardPositionImpl(7, 5))));

        // Move white knight from 2,2 to 4,3
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 2)).get(),
                new BoardPositionImpl(4, 3))));

        // Move black knight from 6,7 to 5,5
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(6, 7)).get(),
                new BoardPositionImpl(5, 5))));

        // Save a reference to white knight in 4,3 beacuse it's going to be captured
        final Piece knightBeforeBeingCaptured = match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).get();

        // Random move for turn purpose
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(7, 1)).get(),
                new BoardPositionImpl(7, 2))));

        // Black night in 5,5 capture white knight in 4,3
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(5, 5)).get(),
                new BoardPositionImpl(4, 3))));

        // Controllo che il cavallo nero si sia effettivamente mosso
        assertTrue(match.getBoard().getPieceAtPosition(new BoardPositionImpl(5, 5)).isEmpty());

        // Controllo che in 4,3 ci sia il cavallo
        assertEquals(PieceType.KNIGHT,
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).get().getType());

        // Controllo che in 4,3 ci sia il cavallo NERO
        assertEquals(this.blackPlayer,
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).get().getPlayer());
        // Check that in position 4,3 there is a knight
        assertEquals(PieceType.KNIGHT,
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).get().getType());

        // Check that in position 4,3 there is a piece of black player
        assertEquals(this.blackPlayer,
                match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).get().getPlayer());

        // Assure that white captured knight is now gone from board
        assertFalse(match.getBoard().contains(knightBeforeBeingCaptured));

        // The game is not completed
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

        // Random move for turn purpose
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 1)).get(),
                new BoardPositionImpl(3, 2))));

        // Move black knight from 4,3 to 2,4
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).get(),
                new BoardPositionImpl(2, 4))));

        // Random move for turn purpose
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(5, 1)).get(),
                new BoardPositionImpl(5, 2))));

        // Move black knight from 2,4 to 3,2 and make check to white player
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 4)).get(),
                new BoardPositionImpl(3, 2))));

        // Non è una patta
        assertFalse(match.getGameController().isDraw());

        // Non è uno scacco matto
        assertFalse(match.getGameController().isOver());

        // Check that white player is under check
        assertTrue(match.getGameController().isInCheck(whitePlayer));

        // Check that's not a draw
        assertFalse(match.getGameController().isDraw());

        // Check that's not endgame
        assertFalse(match.getGameController().isOver());

        // Now whitePlayer is under check and moves that do not prevent the king from
        // being under check must return false when invoked
        assertFalse(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(0, 1)).get(),
                new BoardPositionImpl(0, 2))));

        // This move saves the king, it should be possible
        assertTrue(match.move(new MovementImpl(match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 1)).get(),
                new BoardPositionImpl(3, 2))));

    }

}
