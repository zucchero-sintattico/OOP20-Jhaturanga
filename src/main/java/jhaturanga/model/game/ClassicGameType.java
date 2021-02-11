package jhaturanga.model.game;

import java.util.List;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.movement.MovementManager;
import jhaturanga.model.movement.ClassicMovementManager;
import jhaturanga.model.piece.movement.ClassicPieceMovementStrategyFactory;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;

public class ClassicGameType implements GameType {

    private final List<Player> players;
    private final Board startingBoard;
    private static final int BOARD_ROWS = 8;
    private static final int BOARD_COLUMNS = 8;
    private static final int BISHOP_A_COLUMN_POS = 2;
    private static final int BISHOP_B_COLUMN_POS = 5;
    private static final int KNIGHT_A_COLUMN_POS = 1;
    private static final int KNIGHT_B_COLUMN_POS = 6;
    private static final int QUEEN_COLUMN_POS = 6;
    private static final int KING_COLUMN_POS = 6;
    private final GameController gameController;
    private final ClassicPieceMovementStrategyFactory normalPieceMovementStrategyFactory;
    private final MovementManager movementManager;

    public ClassicGameType(final List<Player> players) {
        this.players = players;
        this.startingBoard = this.createStartingBoard();
        this.normalPieceMovementStrategyFactory = new ClassicPieceMovementStrategyFactory();
        this.gameController = new ClassicGameController(this.startingBoard, this.normalPieceMovementStrategyFactory, this.players);
        this.movementManager = new ClassicMovementManager(this.startingBoard, this.normalPieceMovementStrategyFactory,
                this.gameController);
    }

    private Board createStartingBoard() {
        final BoardBuilder boardBuilder = new BoardBuilderImpl();
        /**
         * Add Pawns to the BoardBuilder
         */
        Stream.iterate(0, x -> x + 1).limit(BOARD_COLUMNS - 1)
                .forEach(x -> boardBuilder.addPiece(this.players.get(0).getPieceFactory().getPawn(new BoardPositionImpl(x, 1))));
        Stream.iterate(0, x -> x + 1).limit(BOARD_COLUMNS - 1).forEach(x -> boardBuilder
                .addPiece(this.players.get(1).getPieceFactory().getPawn(new BoardPositionImpl(x, BOARD_ROWS - 2))));
        /**
         * Add Rooks to the BoardBuilder
         */
        List.of(new BoardPositionImpl(0, 0), new BoardPositionImpl(BOARD_COLUMNS - 1, 0))
                .forEach(x -> boardBuilder.addPiece(this.players.get(0).getPieceFactory().getRook(x)));
        List.of(new BoardPositionImpl(0, BOARD_ROWS - 1), new BoardPositionImpl(BOARD_COLUMNS - 1, BOARD_ROWS - 1))
                .forEach(x -> boardBuilder.addPiece(this.players.get(1).getPieceFactory().getRook(x)));
        /**
         * Add Bishops to the BoardBuilder
         */
        List.of(new BoardPositionImpl(BISHOP_A_COLUMN_POS, 0), new BoardPositionImpl(BISHOP_B_COLUMN_POS, 0))
                .forEach(x -> boardBuilder.addPiece(this.players.get(0).getPieceFactory().getBishop(x)));
        List.of(new BoardPositionImpl(BISHOP_A_COLUMN_POS, BOARD_ROWS - 1),
                new BoardPositionImpl(BISHOP_B_COLUMN_POS, BOARD_ROWS - 1))
                .forEach(x -> boardBuilder.addPiece(this.players.get(1).getPieceFactory().getBishop(x)));
        /**
         * Add Knights to the BoardBuilder
         */
        List.of(new BoardPositionImpl(KNIGHT_A_COLUMN_POS, 0), new BoardPositionImpl(KNIGHT_B_COLUMN_POS, 0))
                .forEach(x -> boardBuilder.addPiece(this.players.get(0).getPieceFactory().getKnight(x)));
        List.of(new BoardPositionImpl(KNIGHT_A_COLUMN_POS, BOARD_ROWS - 1),
                new BoardPositionImpl(KNIGHT_B_COLUMN_POS, BOARD_ROWS - 1))
                .forEach(x -> boardBuilder.addPiece(this.players.get(1).getPieceFactory().getKnight(x)));
        /**
         * Add Queens to the boardBuilder
         */
        boardBuilder.addPiece(this.players.get(0).getPieceFactory().getQueen(new BoardPositionImpl(QUEEN_COLUMN_POS, 0)));
        boardBuilder.addPiece(
                this.players.get(1).getPieceFactory().getQueen(new BoardPositionImpl(QUEEN_COLUMN_POS, BOARD_ROWS - 1)));

        /**
         * Add Kings to the boardBuilder
         */
        boardBuilder.addPiece(this.players.get(0).getPieceFactory().getKing(new BoardPositionImpl(KING_COLUMN_POS, 0)));
        boardBuilder
                .addPiece(this.players.get(1).getPieceFactory().getKing(new BoardPositionImpl(KING_COLUMN_POS, BOARD_ROWS - 1)));
        boardBuilder.rows(8).columns(8);
        return boardBuilder.build();
    }

    @Override
    public final String getGameName() {
        return "Classic Game";
    }

    @Override
    public final GameController getGameController() {
        return this.gameController;
    }

    @Override
    public final Board getStartingBoard() {
        return this.startingBoard;
    }

    @Override
    public final PieceMovementStrategyFactory getPieceMovementStrategyFactory() {
        return this.normalPieceMovementStrategyFactory;
    }

    @Override
    public final MovementManager getMovementManager() {
        return this.movementManager;
    }

}
