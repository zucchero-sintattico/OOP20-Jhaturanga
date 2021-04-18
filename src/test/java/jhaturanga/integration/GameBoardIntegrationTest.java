package jhaturanga.integration;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import jhaturanga.commons.Pair;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.controllers.match.MatchControllerImpl;
import jhaturanga.controllers.setup.SetupController;
import jhaturanga.controllers.setup.SetupControllerImpl;
import jhaturanga.controllers.setup.WhitePlayerChoice;
import jhaturanga.model.ApplicationInstance;
import jhaturanga.model.Model;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.match.MatchStatus;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.timer.DefaultTimers;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.views.commons.board.MatchBoard;
import jhaturanga.views.match.MatchView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

@ExtendWith(ApplicationExtension.class)
class GameBoardIntegrationTest {

    private Stage stage;
    private MatchBoard matchBoardView;
    private Model applicationInstance;

    private int columns;
    private int rows;

    // LOOP exit
    private boolean test = true;

    @Start
    public void init(final Stage stage) throws IOException {

        this.applicationInstance = new ApplicationInstance();
        this.applicationInstance.setFirstUser(UsersManager.GUEST);
        this.applicationInstance.setSecondUser(UsersManager.GUEST);

        final SetupController setupController = new SetupControllerImpl();
        setupController.setModel(this.applicationInstance);
        setupController.setWhitePlayerChoice(WhitePlayerChoice.FIRST_USER);
        setupController.setGameType(GameType.CLASSIC);
        setupController.setTimer(DefaultTimers.NO_LIMIT);
        setupController.createMatch();

        final MatchController matchController = new MatchControllerImpl();
        matchController.setModel(this.applicationInstance);
        PageLoader.getInstance().switchPageWithSpecifiedController(stage, Pages.MATCH, matchController);

        final MatchView matchView = (MatchView) matchController.getView();

        this.stage = stage;
        this.columns = this.applicationInstance.getMatch().get().getBoard().getColumns();
        this.rows = this.applicationInstance.getMatch().get().getBoard().getRows();

        matchBoardView = matchView.getBoardView();

        stage.addEventHandler(KeyEvent.ANY, e -> this.exit());
    }

    /**
     * Set the boolean for exit the {@link randomMovesTest} loop.
     */
    private void exit() {
        this.test = false;
    }

    @Test
    public void randomMovesTest(final FxRobot robot) throws InterruptedException {
        final Random random = new Random();

        while (this.applicationInstance.getMatch().get().getMatchStatus().equals(MatchStatus.ACTIVE) && this.test) {
            final List<Pair<Piece, Set<BoardPosition>>> l = this.applicationInstance.getMatch().get().getBoard()
                    .getPieces().stream()
                    .filter(p -> p.getPlayer().equals(
                            this.applicationInstance.getMatch().get().getGame().getMovementManager().getPlayerTurn()))
                    .map(p -> new Pair<>(p, this.applicationInstance.getMatch().get().getPiecePossibleMoves(p)))
                    .filter(p -> !p.getY().isEmpty()).collect(Collectors.toList());
            final Pair<Piece, Set<BoardPosition>> movement = l.get(random.nextInt(l.size()));
            final BoardPosition destination = movement.getY().stream().collect(Collectors.toList())
                    .get(random.nextInt(movement.getY().size()));

            this.move(robot,
                    position(movement.getX().getPiecePosition().getX(),
                            this.columns - 1 - movement.getX().getPiecePosition().getY()),
                    position(destination.getX(), this.rows - 1 - destination.getY()));
        }
    }

    /**
     * 
     * @param columns for point
     * @param row     for point
     * @return the equivalent Point2D
     */
    private Point2D position(final int columns, final int row) {
        final double widthTile = this.matchBoardView.getWidth() / this.columns;
        final double heightTile = this.matchBoardView.getHeight() / this.rows;

        final double xMargin = this.matchBoardView.localToScene(this.matchBoardView.getBoundsInLocal()).getMinX();
        final double yMargin = this.matchBoardView.localToScene(this.matchBoardView.getBoundsInLocal()).getMinY();
        return new Point2D(this.stage.getX() + xMargin + (widthTile * columns) + (widthTile / 2),
                this.stage.getY() + yMargin + (heightTile * row) + (heightTile / 2));
    }

    /**
     * 
     * @param robot       who perform drag action
     * @param source      where start drag
     * @param destination where end drag
     */
    private void move(final FxRobot robot, final Point2D source, final Point2D destination) {
        robot.moveTo(source).drag(MouseButton.PRIMARY).dropTo(destination).sleep(1);
    }

}
