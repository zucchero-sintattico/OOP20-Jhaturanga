package jhaturanga.model.startingboards;

import java.util.List;
import java.util.stream.Stream;

import jhaturanga.commons.Pair;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.player.Player;

public final class PawnVsClassicStartingBoard {
    private static final int BOARD_ROWS = 8;
    private static final int BOARD_COLUMNS = 8;
    private static final int BISHOP_A_COLUMN_POS = 2;
    private static final int BISHOP_B_COLUMN_POS = 5;
    private static final int KNIGHT_A_COLUMN_POS = 1;
    private static final int KNIGHT_B_COLUMN_POS = 6;
    private static final int QUEEN_COLUMN_POS = 3;
    private static final int KING_COLUMN_POS = 4;
    private static final int WHITE_PAWNS_ROWS = 4;

    private PawnVsClassicStartingBoard() {
    }

    public static Board createStartingBoard(final Player whitePlayer, final Player blackPlayer) {
        final BoardBuilder boardBuilder = new BoardBuilderImpl();
        /**
         * Add Pawns to the BoardBuilder
         */
        Stream.iterate(0, x -> x + 1).limit(BOARD_COLUMNS).forEach(
                x -> boardBuilder.addPiece(blackPlayer.getPieceFactory().getPawn(new BoardPositionImpl(x, BOARD_ROWS - 2))));

        /**
         * Add pawns to the white players in a particular way
         */
        Stream.iterate(new Pair<>(0, 0), x -> new Pair<>((x.getX() + 1) % 2, x.getY() + 1))
                .takeWhile(i -> i.getY() < WHITE_PAWNS_ROWS)
                .forEach(x -> Stream.iterate(x.getX(), i -> i + 2).takeWhile(i -> i < BOARD_COLUMNS).forEach(
                        i -> boardBuilder.addPiece(whitePlayer.getPieceFactory().getPawn(new BoardPositionImpl(i, x.getY())))));

        /**
         * Add Rooks to the BoardBuilder
         */
        List.of(new BoardPositionImpl(0, BOARD_ROWS - 1), new BoardPositionImpl(BOARD_COLUMNS - 1, BOARD_ROWS - 1))
                .forEach(x -> boardBuilder.addPiece(blackPlayer.getPieceFactory().getRook(x)));
        /**
         * Add Bishops to the BoardBuilder
         */
        List.of(new BoardPositionImpl(BISHOP_A_COLUMN_POS, BOARD_ROWS - 1),
                new BoardPositionImpl(BISHOP_B_COLUMN_POS, BOARD_ROWS - 1))
                .forEach(x -> boardBuilder.addPiece(blackPlayer.getPieceFactory().getBishop(x)));
        /**
         * Add Knights to the BoardBuilder
         */
        List.of(new BoardPositionImpl(KNIGHT_A_COLUMN_POS, BOARD_ROWS - 1),
                new BoardPositionImpl(KNIGHT_B_COLUMN_POS, BOARD_ROWS - 1))
                .forEach(x -> boardBuilder.addPiece(blackPlayer.getPieceFactory().getKnight(x)));
        /**
         * Add Queen to the boardBuilder
         */
        boardBuilder.addPiece(blackPlayer.getPieceFactory().getQueen(new BoardPositionImpl(QUEEN_COLUMN_POS, BOARD_ROWS - 1)));

        /**
         * Add King to the boardBuilder
         */
        boardBuilder.addPiece(blackPlayer.getPieceFactory().getKing(new BoardPositionImpl(KING_COLUMN_POS, BOARD_ROWS - 1)));
        boardBuilder.addPiece(whitePlayer.getPieceFactory().getKing(new BoardPositionImpl(KING_COLUMN_POS - 1, 0)));
        boardBuilder.rows(8).columns(8);
        return boardBuilder.build();
    }
}
