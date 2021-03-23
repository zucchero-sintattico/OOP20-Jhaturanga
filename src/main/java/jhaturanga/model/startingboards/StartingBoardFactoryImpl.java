package jhaturanga.model.startingboards;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jhaturanga.commons.Pair;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardBuilder;
import jhaturanga.model.board.BoardBuilderImpl;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;

public class StartingBoardFactoryImpl implements StartingBoardFactory {

    private static final int CLASSIC_BOARD_COLUMNS = 8;
    private static final int CLASSIC_BOARD_ROWS = 8;
    private static final int ONE_D_BOARD_COLUMNS = 1;
    private static final int ROWS_OF_PAWNS = 4;
    private static final int THREECOL_BOARD_COLUMNS = 3;

    private final Map<String, PieceType> letterToPieceType = Map.of("k", PieceType.KING, "q", PieceType.QUEEN, "b",
            PieceType.BISHOP, "r", PieceType.ROOK, "p", PieceType.PAWN, "n", PieceType.KNIGHT);

    private Board fromString(final Player whitePlayer, final Player blackPlayer, final String board, final int columns,
            final int rows) {
        final BoardBuilder boardBuilder = new BoardBuilderImpl();

        Arrays.stream(board.split("/")).map(x -> x.split(",")).map(x -> this.getPieceFromComponents(whitePlayer,
                blackPlayer, x[0], Integer.parseInt(x[1]), Integer.parseInt(x[2]))).forEach(boardBuilder::addPiece);
        boardBuilder.columns(columns).rows(rows);
        return boardBuilder.build();
    }

    private Piece getPieceFromComponents(final Player whitePlayer, final Player blackPlayer, final String letter,
            final int x, final int y) {
        return this.choosePlayerOwner(whitePlayer, blackPlayer, letter).getPieceFactory()
                .getPieceFromPieceType(this.fromLetterToPieceType(letter), new BoardPositionImpl(x, y));
    }

    private PieceType fromLetterToPieceType(final String piece) {
        return this.letterToPieceType.get(piece.toLowerCase(Locale.ITALIAN));
    }

    private Player choosePlayerOwner(final Player whitePlayer, final Player blackPlayer, final String letter) {
        return letter.toUpperCase(Locale.ITALIAN).equals(letter) ? whitePlayer : blackPlayer;
    }

    @Override
    public final Board classicBoard(final Player whitePlayer, final Player blackPlayer) {
        return this.fromString(whitePlayer, blackPlayer,
                "R,0,0/N,1,0/B,2,0/Q,3,0/K,4,0/B,5,0/N,6,0/R,7,0/P,0,1/P,1,1/P,2,1/P,3,1/"
                        + "P,4,1/P,5,1/P,6,1/P,7,1/r,0,7/n,1,7/b,2,7/q,3,7/k,4,7/b,5,7/"
                        + "n,6,7/r,7,7/p,0,6/p,1,6/p,2,6/p,3,6/p,4,6/p,5,6/p,6,6/p,7,6",
                CLASSIC_BOARD_COLUMNS, CLASSIC_BOARD_ROWS);
    }

    @Override
    public final Board threeColumnsBoard(final Player whitePlayer, final Player blackPlayer) {
        return this.fromString(whitePlayer, blackPlayer,
                "K,0,0/Q,1,0/N,2,0/P,0,1/P,1,1/P,2,1/k,0,7/q,1,7/n,2,7/p,0,6/p,1,6/p,2,6", THREECOL_BOARD_COLUMNS,
                CLASSIC_BOARD_ROWS);
    }

    @Override
    public final Board pawnHordeBoard(final Player whitePlayer, final Player blackPlayer) {
        final String whitePawnsPositions = Stream.iterate(0, y -> y + 1).limit(ROWS_OF_PAWNS).flatMap(
                y -> Stream.iterate(new Pair<>(0, y), i -> new Pair<>(i.getX() + 1, y)).limit(CLASSIC_BOARD_COLUMNS))
                .map(i -> "P" + "," + i.getX() + "," + i.getY() + "/").collect(Collectors.joining());

        return this.fromString(whitePlayer, blackPlayer, whitePawnsPositions
                + "K,4,4/r,0,7/n,1,7/b,2,7/q,3,7/k,4,7/b,5,7/n,6,7/r,7,7/p,0,6/p,1,6/p,2,6/p,3,6/p,4,6/p,5,6/p,6,6/p,7,6",
                CLASSIC_BOARD_COLUMNS, CLASSIC_BOARD_ROWS);
    }

    @Override
    public final Board oneDimensionBoard(final Player whitePlayer, final Player blackPlayer) {
        return this.fromString(whitePlayer, blackPlayer, "K,0,0/N,0,1/R,0,2/k,0,7/n,0,6/r,0,5", ONE_D_BOARD_COLUMNS,
                CLASSIC_BOARD_ROWS);
    }

    @Override
    public final Board customizedBoard(final String startingBoard, final int columns, final int rows,
            final Player whitePlayer, final Player blackPlayer) {
        return this.fromString(whitePlayer, blackPlayer, startingBoard, columns, rows);
    }

}
