package jhaturanga.test.game.match;

import static org.junit.Assert.assertFalse;
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
import jhaturanga.model.piece.Piece;
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

        assertEquals(PieceType.KNIGHT,
                this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).get().getType());
        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).get(),
                        new BoardPositionImpl(0, 2))));

        // Controllo che ci siano 32 pezzi
        assertEquals(this.match.getBoard().getBoardState().size(), 32);

        // Controllo che il cavallo sia nella nuova posizione
        assertEquals(PieceType.KNIGHT,
                this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(0, 2)).get().getType());

        // Controllo che nella vecchia posizione del cavallo non ci sia più il cavallo
        assertTrue(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).isEmpty());

        // Muovo il pedino per andare a mangiare il cavallo
        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 6)).get(),
                        new BoardPositionImpl(1, 5))));
        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 5)).get(),
                        new BoardPositionImpl(1, 4))));
        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 4)).get(),
                        new BoardPositionImpl(1, 3))));

        // Controllo di poter mangiare il cavallo
        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 3)).get(),
                        new BoardPositionImpl(0, 2))));

        // Controllo che ci siano 31 pezzi - il cavallo è stato mangiato
        assertEquals(this.match.getBoard().getBoardState().size(), 31);

        // Controllo che il pedone sia nella nuova posizione
        assertEquals(PieceType.PAWN,
                this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(0, 2)).get().getType());

        // Controllo che nella vecchia posizione del pedone non ci sia più il pedone
        assertTrue(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 3)).isEmpty());
    }

    @Test
    void testPawnCaptureFromMatch() {

        final MatchBuilder matchBuilder = new MatchBuilderImpl();

        final GameType gameType = new ClassicGameType(this.whitePlayer, this.blackPlayer);

        this.match = matchBuilder.gameType(gameType).players(List.of(this.whitePlayer, this.blackPlayer)).build();

        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 1)).get(),
                        new BoardPositionImpl(2, 2))));

        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 2)).get(),
                        new BoardPositionImpl(2, 3))));

        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 6)).get(),
                        new BoardPositionImpl(3, 5))));
        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 5)).get(),
                        new BoardPositionImpl(3, 4))));

        final Piece capturedPawn = this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 3)).get();

        // assertTrue(this.match.getBoard().contains(capturedPawn));
        // assertTrue(this.match.getBoard().contains(capturedPawn));

        assertEquals(capturedPawn, this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 3)).get());

        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 4)).get(),
                        new BoardPositionImpl(2, 3))));

        assertTrue(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(3, 4)).isEmpty());

        assertEquals(this.blackPlayer,
                this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 3)).get().getPlayer());

        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 3)).get(),
                        new BoardPositionImpl(2, 2))));
    }

    @Test
    void testKnightCaptureFromMatch() {

        final MatchBuilder matchBuilder = new MatchBuilderImpl();

        final GameType gameType = new ClassicGameType(this.whitePlayer, this.blackPlayer);

        this.match = matchBuilder.gameType(gameType).players(List.of(this.whitePlayer, this.blackPlayer)).build();

        // 7 R k B Q K B k R
        // 6 P P P P P P P P
        // 5 x x x x x k x x
        // 4 x x x x x x x x Simulating BlackKnight(Top k) capturing WhiteKnight(Bot k)
        // 3 x x x x k x x x
        // 2 x x x x x x x x
        // 1 P P P P P P P P
        // 0 R x B Q K B k R
        // - 0 1 2 3 4 5 6 7

        // Muovo il cavallo bianco di sinistra in 2, 2
        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(1, 0)).get(),
                        new BoardPositionImpl(2, 2))));

        // Muovo il cavallo bianco da 2,2 a 4,3
        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 2)).get(),
                        new BoardPositionImpl(4, 3))));

        // Muovo il cavallo nero di destra da 6,7 a 5,5
        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(6, 7)).get(),
                        new BoardPositionImpl(5, 5))));

        // Mi salvo una referenza al cavallo bianco in 4,3 poichè verrà mangiato
        final Piece knightBeforeBeingCaptured = this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3))
                .get();

        // Il cavallo nero mangia il cavallo bianco
        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(5, 5)).get(),
                        new BoardPositionImpl(4, 3))));

        // Controllo che il cavallo nero si sia effettivamente mosso
        assertTrue(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(5, 5)).isEmpty());

        // Controllo che in 4,3 ci sia il cavallo
        assertEquals(PieceType.KNIGHT,
                this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).get().getType());

        // Controllo che in 4,3 ci sia il cavallo NERO
        assertEquals(this.blackPlayer,
                this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).get().getPlayer());

        // Controllo che non ci sia più il cavallo catturato
        assertFalse(this.match.getBoard().contains(knightBeforeBeingCaptured));

        // La partita non è finita
        assertFalse(this.match.isCompleted());

        // 7 R k B Q K B x R
        // 6 P P P P P P P P
        // 5 x x x x x x x x
        // 4 x x x x x x x x Simulating BlackKnight(Top k) checking WhitePlayer
        // 3 x x x x x x x x
        // 2 x x x k x x x x
        // 1 P P P P P P P P
        // 0 R x B Q K B k R
        // - 0 1 2 3 4 5 6 7

        // Sposto il cavallo nero da 4,3 a 2,4
        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 3)).get(),
                        new BoardPositionImpl(2, 4))));

        // sposto il cavallo nero da 2,4 a 3,2 e do scacco al re
        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(2, 4)).get(),
                        new BoardPositionImpl(3, 2))));

        // controllo che sia scacco
        assertTrue(this.match.getGameController().isInCheck(whitePlayer));

        // Non è una patta
        assertFalse(this.match.getGameController().isDraw());

        // Non è uno scacco matto
        assertFalse(this.match.getGameController().isOver());

        this.match.getBoard().getBoardState().stream()
                .sorted((i, j) -> i.getPiecePosition().getX() - j.getPiecePosition().getX())
                .sorted((i, j) -> i.getPiecePosition().getY() - j.getPiecePosition().getY())
                .forEach(i -> System.out.println(i.getIdentifier()));

        // Now whitePlayer is under check and moves that do not prevent the king from
        // being under check must return false when invoked

//        assertFalse(this.match.move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(0, 1)).get(),
//                new BoardPositionImpl(0, 2))));

        assertTrue(this.match
                .move(new MovementImpl(this.match.getBoard().getPieceAtPosition(new BoardPositionImpl(4, 1)).get(),
                        new BoardPositionImpl(3, 2))));
    }

}
