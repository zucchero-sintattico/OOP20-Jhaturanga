package jhaturanga;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.ClassicGameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.builder.MatchBuilder;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;

public final class Launcher {

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
            System.out.println("\n\nOrigin[xy] = ");
            final String origin = in.nextLine();
            System.out.println("\n\nDestination[xy] = ");
            final String destination = in.nextLine();

            final Player playersTurn = playerIt.next();
            boolean isItALegalMove;
            do {

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

    }

    private static void redraw(final Match match) {
        for (int r = match.getBoard().getRows() - 1; r >= 0; r--) {
            for (int c = 0; c < match.getBoard().getColumns(); c++) {
                if (c == 0) {
                    System.out.print("\t\t[" + r + " ]");
                }
                if (match.getBoard().getPieceAtPosition(new BoardPositionImpl(c, r)).isPresent()) {
                    final PlayerColor pc = match.getBoard().getPieceAtPosition(new BoardPositionImpl(c, r)).get().getPlayer()
                            .getColor();
                    final String pcs = pc.toString().substring(0, 1).toLowerCase(Locale.ITALIAN);
                    String pieceName = match.getBoard().getPieceAtPosition(new BoardPositionImpl(c, r)).get().getType().toString()
                            .substring(0, 1);
                    if (match.getBoard().getPieceAtPosition(new BoardPositionImpl(c, r)).get().getType()
                            .equals(PieceType.KNIGHT)) {
                        pieceName = "H";
                    }

                    if (c == match.getBoard().getColumns() - 1) {
                        System.out.println("[" + pcs + pieceName + "]\n");
                    } else {
                        System.out.print("[" + pcs + pieceName + "]");
                    }
                } else {
                    if (c == match.getBoard().getColumns() - 1) {
                        System.out.println("[  ]\n");
                    } else {
                        System.out.print("[  ]");
                    }
                }

            }
        }
        System.out.print("\t\t[  ]");
        for (int i = 0; i < match.getBoard().getColumns(); i++) {
            System.out.print("[" + i + " ]");
        }
        System.out.print("\n");
    }

}
