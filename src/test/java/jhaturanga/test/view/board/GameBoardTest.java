package jhaturanga.test.view.board;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import jhaturanga.commons.Pair;
import jhaturanga.model.Model;
import jhaturanga.model.ModelImpl;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.MatchStatusEnum;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.match.BoardView;

@ExtendWith(ApplicationExtension.class)
class GameBoardTest {

    private static final int C_0 = 0;
    private static final int C_1 = 1;
    private static final int C_2 = 2;
    private static final int C_3 = 3;
    private static final int C_4 = 4;
    private static final int C_5 = 5;
    private static final int C_6 = 6;
    private static final int C_7 = 7;

    private Stage stage;
    private BoardView boardView;
    private Model model;

    private int columns;
    private int rows;

    // LOOP exit
    private boolean test = true;

    // Alert resposte
    private ButtonType response;

    @Start
    public void start(final Stage stage) throws IOException {

        final Model model = new ModelImpl();
        model.setFirstUser(UsersManager.GUEST);
        model.setSecondUser(UsersManager.GUEST);
        final Player blackPlayer = new PlayerImpl(PlayerColor.BLACK, model.getFirstUser().get());
        final Player whitePlayer = new PlayerImpl(PlayerColor.WHITE, model.getSecondUser().get());
        model.setGameType(GameTypesEnum.CLASSIC_GAME); // test on classic game!
        model.setBlackPlayer(blackPlayer);
        model.setWhitePlayer(whitePlayer);
        model.createMatch();
        PageLoader.switchPage(stage, Pages.GAME, model);
        stage.setFullScreen(true);

        this.model = model;
        this.stage = stage;
        this.columns = model.getActualMatch().get().getBoard().getColumns();
        this.rows = model.getActualMatch().get().getBoard().getRows();

        final AnchorPane root = (AnchorPane) stage.getScene().getRoot();
        final BorderPane borderPane = (BorderPane) root.getChildren().get(0);
        boardView = (BoardView) borderPane.getChildren().stream().filter(n -> n instanceof BoardView).findFirst().get();

        stage.addEventHandler(KeyEvent.ANY, e -> {
            if (e.getCode() != KeyCode.ESCAPE && e.getCode() != KeyCode.UNDEFINED) {
                this.exit();
            }
        });
    }

    /**
     * Set the boolean for exit the {@link randomMovesTest} loop.
     */
    private void exit() {
        this.test = false;
    }

    /**
     * 
     * @param response to set for run/not run the tests
     */
    private void setResponse(final ButtonType response) {
        this.response = response;
    }

    @Test
    public void illegalMovesTest(final FxRobot robot) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            new Alert(AlertType.CONFIRMATION, "Do you want to run the bad moves test?")
                .showAndWait()
                .ifPresent(this::setResponse);
            latch.countDown();
        });
        latch.await();
        if (this.response == ButtonType.OK) {
            illegalMoves(robot);
        }

    }

    public void illegalMoves(final FxRobot robot) throws InterruptedException {
        // Pawns
        this.move(robot, this.position(C_0, C_6), this.position(C_0, C_4));
        this.move(robot, this.position(C_1, C_6), this.position(C_1, C_4));
        this.move(robot, this.position(C_2, C_6), this.position(C_2, C_4));
        this.move(robot, this.position(C_3, C_6), this.position(C_3, C_4));
        this.move(robot, this.position(C_4, C_6), this.position(C_4, C_4));
        this.move(robot, this.position(C_5, C_6), this.position(C_5, C_4));
        this.move(robot, this.position(C_6, C_6), this.position(C_6, C_4));
        this.move(robot, this.position(C_7, C_6), this.position(C_7, C_4));

        // Other Pieces
        this.move(robot, this.position(C_0, C_7), this.position(C_0, C_4));
        this.move(robot, this.position(C_1, C_7), this.position(C_1, C_4));
        this.move(robot, this.position(C_2, C_7), this.position(C_2, C_4));
        this.move(robot, this.position(C_3, C_7), this.position(C_3, C_4));
        this.move(robot, this.position(C_4, C_7), this.position(C_4, C_4));
        this.move(robot, this.position(C_5, C_7), this.position(C_5, C_4));
        this.move(robot, this.position(C_6, C_7), this.position(C_6, C_4));
        this.move(robot, this.position(C_7, C_7), this.position(C_7, C_4));
    }

    @Test
    public void randomMovesTest(final FxRobot robot) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            new Alert(AlertType.CONFIRMATION,
                    "Do you want to run the random game test?\n"
                            + "(During the test, press any key to exit, except ESC)\n"
                            + "(PS: be aware... Use it only if you know what you are doing!)")
                .showAndWait()
                .ifPresent(this::setResponse);
            latch.countDown();
        });
        latch.await();
        if (this.response == ButtonType.OK) {
            randomMoves(robot);
        }

    }

    public void randomMoves(final FxRobot robot) throws InterruptedException {
        final Random random = new Random();
<<<<<<< HEAD
        while (this.model.getActualMatch().get().matchStatus().equals(MatchStatusEnum.NOT_OVER) && this.test) {
=======
        while (this.model.getActualMatch().get().matchStatus().equals(MatchStatusEnum.ACTIVE) && this.test) {
>>>>>>> c15a78fb33407225901be9c3e98bf03c15840767
            final List<Pair<Piece, Set<BoardPosition>>> l = this.model.getActualMatch().get().getBoard().getBoardState()
                    .stream()
                    .filter(p -> p.getPlayer()
                            .equals(this.model.getActualMatch().get().getMovementManager().getPlayerTurn()))
                    .map(p -> new Pair<>(p, this.model.getActualMatch().get().getPiecePossibleMoves(p)))
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
        final double widthTile = this.boardView.getWidth() / this.columns;
        final double heightTile = this.boardView.getHeight() / this.rows;
        return new Point2D(this.stage.getX() + this.boardView.getLayoutX() + (widthTile * columns) + (widthTile / 2),
                this.stage.getY() + this.boardView.getLayoutY() + (heightTile * row) + (heightTile / 2));
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
