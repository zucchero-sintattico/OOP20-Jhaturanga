package jhaturanga.views.game;

import java.util.Map;

import javafx.stage.Stage;
import jhaturanga.commons.CommandLine;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.game.GameController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.match.Match;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.views.CommandLineView;

public class CommandLineGameView implements GameView, CommandLineView {

    private GameController controller;
    private Stage stage;
    private final CommandLine console = new CommandLine();
    private final Map<PlayerColor, Map<PieceType, String>> pieceColorTypeCode = Map.of(PlayerColor.BLACK,
            Map.of(PieceType.KING, "\u265A", PieceType.QUEEN, "\u265B", PieceType.BISHOP, "\u265D", PieceType.ROOK,
                    "\u265C", PieceType.PAWN, "\u265F", PieceType.KNIGHT, "\u265E"),
            PlayerColor.WHITE, Map.of(PieceType.KING, "\u2654", PieceType.QUEEN, "\u2655", PieceType.BISHOP, "\u2657",
                    PieceType.ROOK, "\u2656", PieceType.PAWN, "\u265F", PieceType.KNIGHT, "\u2658"));

    @Override
    public final Controller getController() {
        return this.controller;
    }

    @Override
    public final void setController(final Controller controller) {
        this.controller = (GameController) controller;
    }

    @Override
    public final Stage getStage() {
        return this.stage;
    }

    @Override
    public final void setStage(final Stage stage) {
        this.stage = stage;
    }

    @Override
    public final void run() {
        final Match match = this.controller.getModel().getActualMatch().get();
        this.redraw(match.getBoard());
        while (!match.isCompleted()) {
            this.gameLoop(match);
        }
    }

    private void gameLoop(final Match match) {
        this.console.print(TerminalColors.CYAN.toString());
        final String origin = this.console.readLine("\n\nOrigin[xy] = ");
        final String destination = this.console.readLine("\n\nDestination[xy] = ");

        if ("Previous".equals(origin) && "".equals(destination)) {
            this.redraw(this.controller.getPrevBoard());
        } else if ("Next".equals(origin) && "".equals(destination)) {
            this.redraw(this.controller.getNextBoard());
        } else if (this.checkIfValidInput(origin, destination)) {
            this.moveFromInput(origin, destination);
            this.redraw(match.getBoard());
        } else {
            this.console.println("Coordinates format error");
        }
    }

    private void moveFromInput(final String origin, final String destination) {
        final BoardPosition originPosition = new BoardPositionImpl(Integer.parseInt(origin.substring(0, 1)),
                Integer.parseInt(origin.substring(1, 2)));
        final BoardPosition desinationPosition = new BoardPositionImpl(Integer.parseInt(destination.substring(0, 1)),
                Integer.parseInt(destination.substring(1, 2)));
        if (!this.controller.move(originPosition, desinationPosition)) {
            this.console.println("ILLEGAL MOVE!");
        }
    }

    private boolean checkIfValidInput(final String origin, final String destination) {
        try {
            Integer.parseInt(origin);
            Integer.parseInt(destination);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void redraw(final Board board) {
        for (int r = board.getRows() - 1; r >= 0; r--) {
            for (int c = 0; c < board.getColumns(); c++) {
                this.console.print(TerminalColors.YELLOW.toString());
                if (c == 0) {
                    this.console.print("\t\t[ " + r + " ]");
                }
                if (board.getPieceAtPosition(new BoardPositionImpl(c, r)).isPresent()) {
                    final PlayerColor pc = board.getPieceAtPosition(new BoardPositionImpl(c, r)).get().getPlayer()
                            .getColor();

                    if (pc.equals(PlayerColor.BLACK)) {
                        this.console.print(TerminalColors.RED.toString());
                    } else {
                        this.console.print(TerminalColors.WHITE.toString());
                    }

                    final String unicodeChessSymbol = this
                            .fromPieceToString(board.getPieceAtPosition(new BoardPositionImpl(c, r)).get());

                    if (c == board.getColumns() - 1) {
                        this.console.println("[ " + unicodeChessSymbol + " ]\n");
                    } else {
                        this.console.print("[ " + unicodeChessSymbol + " ]");
                    }
                } else {
                    this.console.print(TerminalColors.GREEN.toString());
                    if (c == board.getColumns() - 1) {
                        this.console.println("[ " + "\u2003" + " ]\n");
                    } else if (c % 2 == 0) {
                        this.console.print("[ " + "\u2003" + " ]");
                    } else {
                        this.console.print(" ");
                        this.console.print("[ " + "\u2003" + " ]");
                    }
                }
            }
        }
        this.console.print(TerminalColors.YELLOW.toString());
        this.console.print("\t\t[   ]");
        for (int i = 0; i < board.getColumns(); i++) {
            this.console.print("[ " + i + " ]");
            if (i % 3 == 0) {
                this.console.print(" ");
            }
        }
        this.console.print("\n");
    }

    private String fromPieceToString(final Piece piece) {
        return this.pieceColorTypeCode.get(piece.getPlayer().getColor()).get(piece.getType());
    }

}
