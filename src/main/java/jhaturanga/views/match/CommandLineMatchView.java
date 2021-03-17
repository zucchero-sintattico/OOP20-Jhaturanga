package jhaturanga.views.match;

import java.util.Map;

import jhaturanga.commons.CommandLine;
import jhaturanga.commons.TerminalColors;
import jhaturanga.controllers.home.HomeControllerImpl;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.controllers.match.MovementResult;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.game.MatchStatusEnum;
import jhaturanga.model.match.Match;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.piece.PieceType;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.views.AbstractView;
import jhaturanga.views.CommandLineView;
import jhaturanga.views.home.CommandLineHomeView;

public class CommandLineMatchView extends AbstractView implements MatchView, CommandLineView {

    private final CommandLine console = new CommandLine();
    private final Map<PlayerColor, Map<PieceType, String>> pieceColorTypeCode = Map.of(PlayerColor.BLACK,
            Map.of(PieceType.KING, "\u265A", PieceType.QUEEN, "\u265B", PieceType.BISHOP, "\u265D", PieceType.ROOK,
                    "\u265C", PieceType.PAWN, "\u265F", PieceType.KNIGHT, "\u265E"),
            PlayerColor.WHITE, Map.of(PieceType.KING, "\u2654", PieceType.QUEEN, "\u2655", PieceType.BISHOP, "\u2657",
                    PieceType.ROOK, "\u2656", PieceType.PAWN, "\u265F", PieceType.KNIGHT, "\u2658"));

    @Override
    public final void run() {
        this.getGameController().start();
        final Match match = this.getController().getModel().getActualMatch().get();
        this.redraw(match.getBoard());
        while (match.matchStatus().equals(MatchStatusEnum.NOT_OVER)) {
            this.gameLoop(match);
        }
        this.console.println("WINNER IS: " + match.winner().get().toString());
        this.console.readLine("Press enter to continue to the home page...");
        this.console.println("\n\n");
        this.console.print(TerminalColors.WHITE.toString());
        this.backToHome();
    }

    private void backToHome() {
        new Thread(() -> {
            final CommandLineHomeView view = new CommandLineHomeView();
            final HomeControllerImpl controller = new HomeControllerImpl();
            controller.setModel(this.getGameController().getModel());
            view.setController(controller);
            view.run();

        }).start();
    }

    private void gameLoop(final Match match) {
        this.console.print(TerminalColors.CYAN.toString());
        final String origin = this.console.readLine("\n\nOrigin[xy] = ");
        final String destination = this.console.readLine("\n\nDestination[xy] = ");
        if ("Previous".equals(origin) && "".equals(destination)) {

            this.redraw(this.getGameController().getPrevBoard().get());
        } else if ("Next".equals(origin) && "".equals(destination)) {
            this.redraw(this.getGameController().getNextBoard().get());
        } else if (this.checkIfValidInput(origin, destination)) {
            this.moveFromInput(origin, destination);
            this.console.println(match.getPlayerTimeRemaining().getX() + " time left: "
                    + match.getPlayerTimeRemaining().getY() + "s");
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
        if (!this.getGameController().move(originPosition, desinationPosition).equals(MovementResult.NONE)) {
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
                    } else if (c % 2 == 0) {
                        this.console.print("[ " + "\u2003" + " ]");
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

    @Override
    public final MatchController getGameController() {
        return (MatchController) this.getController();
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

}
