package jhaturanga.model.game;

import java.util.List;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.movement.MovementManager;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;

public class ClassicGameType implements GameType {

    private final List<Player> players;
    private static final int BOARD_ROWS = 8;
    private static final int BOARD_COLUMNS = 8;
    private static final int BISHOP_A_COLUMN_POS = 2;
    private static final int BISHOP_B_COLUMN_POS = 5;
    private static final int KNIGHT_A_COLUMN_POS = 1;
    private static final int KNIGHT_B_COLUMN_POS = 6;
    private static final int QUEEN_COLUMN_POS = 6;
    private static final int KING_COLUMN_POS = 6;

    public ClassicGameType(final List<Player> players) {
        this.players = players;
    }

    @Override
    public final String getGameName() {
        return "Classic Game";
    }

    @Override
    public final GameController getGameController() {
        return new NormalGameController(this.getStartingBoard(), this.getPieceMovementStrategyFactory(), this.players);
    }

    @Override
    public final Board getStartingBoard() {
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
        List.of(new BoardPositionImpl(0, 0), new BoardPositionImpl(BOARD_ROWS - 1, 0))
                .forEach(x -> boardBuilder.addPiece(this.players.get(0).getPieceFactory().getRook(x)));
        List.of(new BoardPositionImpl(0, BOARD_ROWS - 1), new BoardPositionImpl(BOARD_COLUMNS - 1, BOARD_ROWS - 1))
                .forEach(x -> boardBuilder.addPiece(this.players.get(0).getPieceFactory().getRook(x)));
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
        return boardBuilder.build();
    }

    @Override
    public final PieceMovementStrategyFactory getPieceMovementStrategyFactory() {
        return null;
    }

    @Override
    public final MovementManager getMovementManager() {
        return null;
    }

}
