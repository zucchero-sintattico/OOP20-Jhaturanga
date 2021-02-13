package jhaturanga;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.ClassicGameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.builder.MatchBuilder;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;

public final class Launcher {

    enum Color {
        // Color end string, color reset
        RESET("\033[0m"),

        // Regular Colors. Normal color, no bold, background color etc.
        BLACK("\033[0;30m"), // BLACK
        RED("\033[0;31m"), // RED
        GREEN("\033[0;32m"), // GREEN
        YELLOW("\033[0;33m"), // YELLOW
        BLUE("\033[0;34m"), // BLUE
        MAGENTA("\033[0;35m"), // MAGENTA
        CYAN("\033[0;36m"), // CYAN
        WHITE("\033[0;37m"), // WHITE

        // Bold
        BLACK_BOLD("\033[1;30m"), // BLACK
        RED_BOLD("\033[1;31m"), // RED
        GREEN_BOLD("\033[1;32m"), // GREEN
        YELLOW_BOLD("\033[1;33m"), // YELLOW
        BLUE_BOLD("\033[1;34m"), // BLUE
        MAGENTA_BOLD("\033[1;35m"), // MAGENTA
        CYAN_BOLD("\033[1;36m"), // CYAN
        WHITE_BOLD("\033[1;37m"), // WHITE

        // Underline
        BLACK_UNDERLINED("\033[4;30m"), // BLACK
        RED_UNDERLINED("\033[4;31m"), // RED
        GREEN_UNDERLINED("\033[4;32m"), // GREEN
        YELLOW_UNDERLINED("\033[4;33m"), // YELLOW
        BLUE_UNDERLINED("\033[4;34m"), // BLUE
        MAGENTA_UNDERLINED("\033[4;35m"), // MAGENTA
        CYAN_UNDERLINED("\033[4;36m"), // CYAN
        WHITE_UNDERLINED("\033[4;37m"), // WHITE

        // Background
        BLACK_BACKGROUND("\033[40m"), // BLACK
        RED_BACKGROUND("\033[41m"), // RED
        GREEN_BACKGROUND("\033[42m"), // GREEN
        YELLOW_BACKGROUND("\033[43m"), // YELLOW
        BLUE_BACKGROUND("\033[44m"), // BLUE
        MAGENTA_BACKGROUND("\033[45m"), // MAGENTA
        CYAN_BACKGROUND("\033[46m"), // CYAN
        WHITE_BACKGROUND("\033[47m"), // WHITE

        // High Intensity
        BLACK_BRIGHT("\033[0;90m"), // BLACK
        RED_BRIGHT("\033[0;91m"), // RED
        GREEN_BRIGHT("\033[0;92m"), // GREEN
        YELLOW_BRIGHT("\033[0;93m"), // YELLOW
        BLUE_BRIGHT("\033[0;94m"), // BLUE
        MAGENTA_BRIGHT("\033[0;95m"), // MAGENTA
        CYAN_BRIGHT("\033[0;96m"), // CYAN
        WHITE_BRIGHT("\033[0;97m"), // WHITE

        // Bold High Intensity
        BLACK_BOLD_BRIGHT("\033[1;90m"), // BLACK
        RED_BOLD_BRIGHT("\033[1;91m"), // RED
        GREEN_BOLD_BRIGHT("\033[1;92m"), // GREEN
        YELLOW_BOLD_BRIGHT("\033[1;93m"), // YELLOW
        BLUE_BOLD_BRIGHT("\033[1;94m"), // BLUE
        MAGENTA_BOLD_BRIGHT("\033[1;95m"), // MAGENTA
        CYAN_BOLD_BRIGHT("\033[1;96m"), // CYAN
        WHITE_BOLD_BRIGHT("\033[1;97m"), // WHITE

        // High Intensity backgrounds
        BLACK_BACKGROUND_BRIGHT("\033[0;100m"), // BLACK
        RED_BACKGROUND_BRIGHT("\033[0;101m"), // RED
        GREEN_BACKGROUND_BRIGHT("\033[0;102m"), // GREEN
        YELLOW_BACKGROUND_BRIGHT("\033[0;103m"), // YELLOW
        BLUE_BACKGROUND_BRIGHT("\033[0;104m"), // BLUE
        MAGENTA_BACKGROUND_BRIGHT("\033[0;105m"), // MAGENTA
        CYAN_BACKGROUND_BRIGHT("\033[0;106m"), // CYAN
        WHITE_BACKGROUND_BRIGHT("\033[0;107m"); // WHITE

        private final String code;

        Color(final String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    private Launcher() {

    }

    public static void main(final String[] args) throws IOException {
        final Scanner in = new Scanner(System.in);

        final Player whitePlayer = new PlayerImpl(PlayerColor.WHITE);
        final Player blackPlayer = new PlayerImpl(PlayerColor.BLACK);

        final MatchBuilder matchBuilder = new MatchBuilderImpl();

        Match match = matchBuilder.gameType(new ClassicGameType(whitePlayer, blackPlayer))
                .players(List.of(whitePlayer, blackPlayer)).build();

        final Iterator<Player> playerIt = Stream.generate(() -> List.of(whitePlayer, blackPlayer)).flatMap(i -> i.stream())
                .iterator();

        redraw(match);

        while (!match.isCompleted()) {
            System.out.print(Color.CYAN);
            System.out.println("\n\nOrigin[xy] = ");
            final String origin = in.nextLine();
            System.out.println("\n\nDestination[xy] = ");
            final String destination = in.nextLine();

            final Player playersTurn = playerIt.next();
            boolean isItALegalMove;
            do {
                if (!match.getBoard().getPieceAtPosition(
                        new BoardPositionImpl(Integer.parseInt(origin.substring(0, 1)), Integer.parseInt(origin.substring(1, 2))))
                        .isPresent()) {
                    System.out.println("No piece to move from this position");
                    playerIt.next();
                    break;
                }
                if (!match.getBoard().getPieceAtPosition(
                        new BoardPositionImpl(Integer.parseInt(origin.substring(0, 1)), Integer.parseInt(origin.substring(1, 2))))
                        .get().getPlayer().equals(playersTurn)) {
                    System.out.println("Cant move another players piece");
                    playerIt.next();
                    break;
                }

                isItALegalMove = match.move(new MovementImpl(
                        match.getBoard()
                                .getPieceAtPosition(new BoardPositionImpl(Integer.parseInt(origin.substring(0, 1)),
                                        Integer.parseInt(origin.substring(1, 2))))
                                .get(),
                        new BoardPositionImpl(Integer.parseInt(destination.substring(0, 1)),
                                Integer.parseInt(destination.substring(1, 2)))));
                if (!isItALegalMove) {
                    System.out.println("ILLEGAL MOVE!");
                    playerIt.next();
                    break;
                }
            } while (false);

            redraw(match);

        }

        System.out.println("CHECK MATE");
        in.close();
    }

    private static void redraw(final Match match) {
        for (int r = match.getBoard().getRows() - 1; r >= 0; r--) {
            for (int c = 0; c < match.getBoard().getColumns(); c++) {
                System.out.print(Color.YELLOW);
                if (c == 0) {
                    System.out.print("\t\t[ " + r + " ]");
                }
                if (match.getBoard().getPieceAtPosition(new BoardPositionImpl(c, r)).isPresent()) {
                    final PlayerColor pc = match.getBoard().getPieceAtPosition(new BoardPositionImpl(c, r)).get().getPlayer()
                            .getColor();

                    if (pc.equals(PlayerColor.BLACK)) {
                        System.out.print(Color.BLACK);
                    } else {
                        System.out.print(Color.WHITE);
                    }

                    String unicodeChessSymbol = fromPieceToString(
                            match.getBoard().getPieceAtPosition(new BoardPositionImpl(c, r)).get());

                    if (c == match.getBoard().getColumns() - 1) {
                        System.out.println("[ " + unicodeChessSymbol + " ]\n");
                    } else {
                        System.out.print("[ " + unicodeChessSymbol + " ]");
                    }
                } else {
                    System.out.print(Color.GREEN);
                    if (c == match.getBoard().getColumns() - 1) {
                        System.out.println("[ " + " \u2003 " + " ]\n");
                    } else if (c % 2 == 0) {
                        System.out.print("[ " + " \u2003 " + " ]");
                    } else {
                        System.out.print(" ");
                        System.out.print("[ " + " \u2003 " + " ]");
                    }
                }

            }
        }
        System.out.print(Color.YELLOW);
        System.out.print("\t\t[  ]");
        for (int i = 0; i < match.getBoard().getColumns(); i++) {
            System.out.print("[ " + i + " ]");
            if (i % 3 == 0) {
                System.out.print(" ");
            }
        }
        System.out.print("\n");
    }

    private static String fromPieceToString(final Piece piece) {
        Map<PlayerColor, Map<PieceType, String>> map = Map.of(PlayerColor.BLACK,
                Map.of(PieceType.KING, "\u265A", PieceType.QUEEN, "\u265B", PieceType.BISHOP, "\u265D", PieceType.ROOK, "\u265C",
                        PieceType.PAWN, "\u265F", PieceType.KNIGHT, "\u265E"),
                PlayerColor.WHITE, Map.of(PieceType.KING, "\u2654", PieceType.QUEEN, "\u2655", PieceType.BISHOP, "\u2657",
                        PieceType.ROOK, "\u2656", PieceType.PAWN, "\u265F", PieceType.KNIGHT, "\u2658"));

        final PlayerColor color = piece.getPlayer().getColor();
        final PieceType type = piece.getType();
        return map.get(color).get(type);

    }

}
