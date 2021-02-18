package jhaturanga.model.startingboards;

import java.util.List;
import java.util.stream.Stream;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.player.Player;

public final class SidewaysStartingBoard {
    private static final int BOARD_ROWS = 8;
    private static final int BOARD_COLUMNS = 3;
    private static final int KNIGHT_COLUMN_POS = 2;
    private static final int QUEEN_COLUMN_POS = 1;
    private static final int KING_COLUMN_POS = 0;

    private SidewaysStartingBoard() {
    }

    public static Board createStartingBoard(final Player whitePlayer, final Player blackPlayer) {
        final BoardBuilder boardBuilder = new BoardBuilderImpl();
        /**
         * Add Pawns to the BoardBuilder
         */
        Stream.iterate(0, x -> x + 1).limit(BOARD_COLUMNS).forEach(x -> boardBuilder
                .addPiece(blackPlayer.getPieceFactory().getPawn(new BoardPositionImpl(x, BOARD_ROWS - 2))));

        /**
         * Add pawns to the white players in a particular way
         */
        Stream.iterate(0, x -> x + 1).limit(BOARD_COLUMNS).forEach(
                x -> boardBuilder.addPiece(whitePlayer.getPieceFactory().getPawn(new BoardPositionImpl(x, 0))));

        /**
         * Add Knights to the BoardBuilder
         */
        List.of(new BoardPositionImpl(KNIGHT_COLUMN_POS, BOARD_ROWS - 1))
                .forEach(x -> boardBuilder.addPiece(blackPlayer.getPieceFactory().getKnight(x)));
        List.of(new BoardPositionImpl(KNIGHT_COLUMN_POS, 0))
                .forEach(x -> boardBuilder.addPiece(whitePlayer.getPieceFactory().getKnight(x)));
        /**
         * Add Queens to the boardBuilder
         */
        boardBuilder.addPiece(
                blackPlayer.getPieceFactory().getQueen(new BoardPositionImpl(QUEEN_COLUMN_POS, BOARD_ROWS - 1)));
        boardBuilder.addPiece(whitePlayer.getPieceFactory().getQueen(new BoardPositionImpl(QUEEN_COLUMN_POS, 0)));

        /**
         * Add Kings to the boardBuilder
         */
        boardBuilder.addPiece(
                blackPlayer.getPieceFactory().getKing(new BoardPositionImpl(KING_COLUMN_POS, BOARD_ROWS - 1)));
        boardBuilder.addPiece(whitePlayer.getPieceFactory().getKing(new BoardPositionImpl(KING_COLUMN_POS, 0)));

        boardBuilder.rows(BOARD_ROWS).columns(BOARD_COLUMNS);
        return boardBuilder.build();
    }
}
