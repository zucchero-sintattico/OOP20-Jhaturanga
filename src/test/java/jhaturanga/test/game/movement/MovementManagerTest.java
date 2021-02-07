package jhaturanga.test.game.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.GameController;
import jhaturanga.model.game.NormalGameController;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.movement.MovementManager;
import jhaturanga.model.movement.NormalMovementManager;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.piece.factory.PieceFactory;
import jhaturanga.model.piece.factory.PieceFactoryImpl;
import jhaturanga.model.piece.movement.NormalPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;

class MovementManagerTest {

    private MovementManager movMan;
    private Player player1;
    private Player player2;
    private PieceFactory pfPlayer1;
    private PieceFactory pfPlayer2;

    @BeforeEach
    public void init() {
        player1 = new PlayerImpl(PlayerColor.WHITE);
        player2 = new PlayerImpl(PlayerColor.BLACK);
        pfPlayer1 = new PieceFactoryImpl(player1);
        pfPlayer2 = new PieceFactoryImpl(player2);

    }

    @Test
    public void testBasicCapture() {
        final BoardBuilder bb = new BoardBuilderImpl();
        Board board = bb.columns(8).rows(8).addPiece(pfPlayer2.getRook(new BoardPositionImpl(6, 1)))
                .addPiece(pfPlayer2.getQueen(new BoardPositionImpl(6, 6)))
                .addPiece(pfPlayer1.getRook(new BoardPositionImpl(6, 2))).build();

        final PieceMovementStrategyFactory pmsf = new NormalPieceMovementStrategyFactory();
        final GameController gameContr = new NormalGameController(board, pmsf, List.of(player1, player2));
        this.movMan = new NormalMovementManager(board, pmsf, gameContr);
        assertTrue(movMan.move(
                new MovementImpl(board.getPieceAtPosition(new BoardPositionImpl(6, 6)).get(), new BoardPositionImpl(6, 2))));
        assertEquals(PieceType.QUEEN, board.getPieceAtPosition(new BoardPositionImpl(6, 2)).get().getType());
        board.getBoardState().stream().forEach(i -> System.out.println(i.getIdentifier()));
    }

}
