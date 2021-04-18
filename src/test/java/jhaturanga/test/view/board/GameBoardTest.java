package jhaturanga.test.view.board;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.controllers.match.MatchControllerImpl;
import jhaturanga.controllers.setup.SetupController;
import jhaturanga.controllers.setup.SetupControllerImpl;
import jhaturanga.controllers.setup.WhitePlayerChoice;
import jhaturanga.model.ApplicationInstance;
import jhaturanga.model.Model;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.timer.DefaultTimers;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.views.commons.board.MatchBoard;
import jhaturanga.views.match.MatchView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

@ExtendWith(ApplicationExtension.class)
class GameBoardTest {

    private static final int C_1 = 1;
    private static final int C_3 = 3;
    private static final int C_4 = 4;
    private static final int C_6 = 6;

    private Stage stage;
    private MatchBoard matchBoardView;

    private int columns;
    private int rows;

    @Start
    public void init(final Stage stage) throws IOException {

        final Model applicationInstance = new ApplicationInstance();
        applicationInstance.setFirstUser(UsersManager.GUEST);
        applicationInstance.setSecondUser(UsersManager.GUEST);

        final SetupController setupController = new SetupControllerImpl();
        setupController.setModel(applicationInstance);
        setupController.setWhitePlayerChoice(WhitePlayerChoice.FIRST_USER);
        setupController.setGameType(GameType.CLASSIC);
        setupController.setTimer(DefaultTimers.NO_LIMIT);
        setupController.createMatch();

        final MatchController matchController = new MatchControllerImpl();
        matchController.setModel(applicationInstance);
        PageLoader.getInstance().switchPageWithSpecifiedController(stage, Pages.MATCH, matchController);

        final MatchView matchView = (MatchView) matchController.getView();

        this.stage = stage;
        this.columns = applicationInstance.getMatch().get().getBoard().getColumns();
        this.rows = applicationInstance.getMatch().get().getBoard().getRows();

        matchBoardView = matchView.getBoardView();
    }

    @Test
    public void movesTest(final FxRobot robot) throws InterruptedException {
        assertDoesNotThrow(() -> this.move(robot, this.position(C_4, C_6), this.position(C_4, C_4)));
        assertDoesNotThrow(() -> this.move(robot, this.position(C_3, C_1), this.position(C_3, C_3)));
        assertDoesNotThrow(() -> this.move(robot, this.position(C_4, C_4), this.position(C_3, C_3)));
    }

    @Test
    public void illegalMovesTest(final FxRobot robot) throws InterruptedException {
        assertDoesNotThrow(() -> this.move(robot, this.position(C_4, C_1), this.position(C_4, C_3)));
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
