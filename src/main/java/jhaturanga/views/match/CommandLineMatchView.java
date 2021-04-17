package jhaturanga.views.match;

import java.util.Map;

import jhaturanga.commons.console.CommandLineConsole;
import jhaturanga.commons.console.TerminalColors;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.controllers.setup.SetupController;
import jhaturanga.controllers.setup.SetupControllerImpl;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.match.MatchStatus;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.views.BasicView;
import jhaturanga.views.CommandLineView;
import jhaturanga.views.setup.CommandLineSetupView;

/**
 * The command line version of the Match View.
 */
public final class CommandLineMatchView extends BasicView implements CommandLineView {

    private final CommandLineConsole console = new CommandLineConsole();
    private final Map<PlayerColor, Map<PieceType, String>> pieceColorTypeCode = Map.of(PlayerColor.BLACK,
            Map.of(PieceType.KING, "\u265A", PieceType.QUEEN, "\u265B", PieceType.BISHOP, "\u265D", PieceType.ROOK,
                    "\u265C", PieceType.PAWN, "\u265F", PieceType.KNIGHT, "\u265E"),
            PlayerColor.WHITE, Map.of(PieceType.KING, "\u2654", PieceType.QUEEN, "\u2655", PieceType.BISHOP, "\u2657",
                    PieceType.ROOK, "\u2656", PieceType.PAWN, "\u265F", PieceType.KNIGHT, "\u2658"));

    @Override
    public void run() {
        this.getMatchController().start();

        while (this.getMatchController().getStatus().equals(MatchStatus.ACTIVE)) {
            this.gameLoop();
        }
        this.console.println("WINNER IS: "
                + this.getMatchController().getWinner().map(w -> w.getUsername()).orElseGet(() -> "No One Won"));
        this.console.readLine("Press enter to continue to the home page...");
        this.console.println("\n\n");
        this.console.print(TerminalColors.WHITE.toString());
        this.backToSetup();
    }

    private void backToSetup() {
        new Thread(() -> {
            final CommandLineSetupView view = new CommandLineSetupView();
            final SetupController controller = new SetupControllerImpl();
            controller.setApplicationInstance(this.getController().getApplicationInstance());
            view.setController(controller);
            view.run();

        }).start();
    }

    private void gameLoop() {
        this.console.clearConsole();
        this.console.println("");
        this.console.println(this.getMatchController().getWhitePlayer().getUsername() + " time left: "
                + this.getMatchController().getTimer().getRemaningTime(this.getMatchController().getWhitePlayer())
                + "s");
        this.console.println(this.getMatchController().getBlackPlayer().getUsername() + " time left: "
                + this.getMatchController().getTimer().getRemaningTime(this.getMatchController().getBlackPlayer())
                + "s");
        this.redraw(this.getMatchController().getBoard());
        this.console.print(TerminalColors.CYAN.toString());
        final String origin = this.console.readLine("\n\nOrigin[xy] = ");
        final String destination = this.console.readLine("\n\nDestination[xy] = ");

        if (this.checkIfValidInput(origin, destination)) {
            this.moveFromInput(origin, destination);
        } else {
            this.console.println("Coordinates format error");
        }
    }

    private void moveFromInput(final String origin, final String destination) {
        final BoardPosition originPosition = new BoardPositionImpl(Integer.parseInt(origin.substring(0, 1)),
                Integer.parseInt(origin.substring(1, 2)));
        final BoardPosition desinationPosition = new BoardPositionImpl(Integer.parseInt(destination.substring(0, 1)),
                Integer.parseInt(destination.substring(1, 2)));

        if (this.getMatchController().move(originPosition, desinationPosition).equals(MovementResult.INVALID_MOVE)) {
            this.console.println("ILLEGAL MOVE!");
        }
    }

    private boolean checkIfValidInput(final String origin, final String destination) {
        if (origin.length() != 2 || destination.length() != 2) {
            return false;
        }
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
                    } else {
                        this.console.print("[ " + "\u2003" + " ]");
                    }
                }
            }
        }
        this.console.print(TerminalColors.YELLOW.toString());
        this.console.print("\t\t[   ]");
        for (int i = 0; i < board.getColumns(); i++) {
            this.console.print("[ " + i + " ]");
        }
        this.console.print("\n");
    }

    private String fromPieceToString(final Piece piece) {
        return this.pieceColorTypeCode.get(piece.getPlayer().getColor()).get(piece.getType());
    }

    private MatchController getMatchController() {
        return (MatchController) this.getController();
    }
}
