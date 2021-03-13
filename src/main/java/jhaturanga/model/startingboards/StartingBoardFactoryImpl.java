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
import jhaturanga.model.piece.PieceImpl;
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

    private PieceType fromLetterToPieceType(final String piece) {
        return this.letterToPieceType.get(piece.toLowerCase(Locale.ITALIAN));
    }

    private Player choosePlayerOwner(final Player whitePlayer, final Player blackPlayer, final String letter) {
        return letter.toUpperCase(Locale.ITALIAN).equals(letter) ? whitePlayer : blackPlayer;
    }

    private Board fromString(final Player whitePlayer, final Player blackPlayer, final String board, final int columns,
            final int rows) {
        final BoardBuilder boardBuilder = new BoardBuilderImpl();
        boardBuilder.columns(columns).rows(rows);
        Arrays.stream(board.split("/"))
                .map(i -> new PieceImpl(this.fromLetterToPieceType(Character.toString(i.charAt(0))),
                        new BoardPositionImpl(Integer.parseInt(Character.toString(i.charAt(1))),
                                Integer.parseInt(Character.toString(i.charAt(2)))),
                        this.choosePlayerOwner(whitePlayer, blackPlayer, Character.toString(i.charAt(0)))))
                .forEach(i -> boardBuilder.addPiece(i));
        return boardBuilder.build();
    }

    @Override
    public final Board classicBoard(final Player whitePlayer, final Player blackPlayer) {
        return this.fromString(whitePlayer, blackPlayer,
                "R00/N10/B20/Q30/K40/B50/N60/R70/P01/P11/P21/P31/"
                        + "P41/P51/P61/P71/r07/n17/b27/q37/k47/b57/n67/r77/p06/p16/p26/p36/p46/p56/p66/p76",
                CLASSIC_BOARD_COLUMNS, CLASSIC_BOARD_ROWS);
    }

    @Override
    public final Board threeColumnsBoard(final Player whitePlayer, final Player blackPlayer) {
        return this.fromString(whitePlayer, blackPlayer, "K00/Q10/N20/P01/P11/P21/k07/q17/n27/p06/p16/p26/",
                THREECOL_BOARD_COLUMNS, CLASSIC_BOARD_ROWS);
    }

    @Override
    public final Board pawnHordeBoard(final Player whitePlayer, final Player blackPlayer) {
        final String whitePawnsPositions = Stream.iterate(0, y -> y + 1).limit(ROWS_OF_PAWNS).flatMap(
                y -> Stream.iterate(new Pair<>(0, y), i -> new Pair<>(i.getX() + 1, y)).limit(CLASSIC_BOARD_COLUMNS))
                .map(i -> "P" + i.getX() + i.getY() + "/").collect(Collectors.joining());

        return this.fromString(whitePlayer, blackPlayer,
                whitePawnsPositions + "K44/r07/n17/b27/q37/k47/b57/n67/r77/p06/p16/p26/p36/p46/p56/p66/p76",
                CLASSIC_BOARD_COLUMNS, CLASSIC_BOARD_ROWS);
    }

    @Override
    public final Board oneDimensionBoard(final Player whitePlayer, final Player blackPlayer) {
        return this.fromString(whitePlayer, blackPlayer, "K00/N01/R02/k07/n06/r05", ONE_D_BOARD_COLUMNS,
                CLASSIC_BOARD_ROWS);
    }

    @Override
    public final Board customizedBoard(final String startingBoard, final int columns, final int rows,
            final Player whitePlayer, final Player blackPlayer) {
        return this.fromString(whitePlayer, blackPlayer, startingBoard, columns, rows);
    }

}
