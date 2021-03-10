package jhaturanga.test.view.board;

import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jhaturanga.model.Model;
import jhaturanga.model.ModelImpl;
import jhaturanga.model.game.gametypes.GameTypesEnum;
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


    @Start
    public void start(final Stage stage) throws IOException {

        final Model model = new ModelImpl();
        final Player blackPlayer = new PlayerImpl(PlayerColor.BLACK, UsersManager.GUEST);
        final Player whitePlayer = new PlayerImpl(PlayerColor.WHITE, UsersManager.GUEST);
        model.setGameType(GameTypesEnum.CLASSIC_GAME); //test on classic game!
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
        boardView = (BoardView) borderPane.getChildren().stream()
                .filter(n -> n instanceof BoardView)
                .findFirst().get();
    }

    @Test
    void normalMoves(final FxRobot robot) throws InterruptedException {

        //Pawns
        this.move(robot, this.position(C_5, C_6), this.position(C_5, C_4));
        this.move(robot, this.position(C_3, C_1), this.position(C_3, C_3));

        //Pawns
        this.move(robot, this.position(C_4, C_6), this.position(C_4, C_4));
        this.move(robot, this.position(C_4, C_1), this.position(C_4, C_2));

        //bishops
        this.move(robot, this.position(C_5, C_7), this.position(C_0, C_2));
        this.move(robot, this.position(C_5, C_0), this.position(C_0, C_5));

        //Knights
        this.move(robot, this.position(C_6, C_7), this.position(C_5, C_5));
        this.move(robot, this.position(C_6, C_0), this.position(C_5, C_2));

        //Knight
        this.move(robot, this.position(C_1, C_7), this.position(C_2, C_5));

        //Kings
        this.move(robot, this.position(C_4, C_0), this.position(C_6, C_0));
    }
/*
    @Test
    void illegalMoves(final FxRobot robot) throws InterruptedException {
        //Pawns
        this.move(robot, this.position(C_0, C_6), this.position(C_0, C_4));
        this.move(robot, this.position(C_1, C_6), this.position(C_1, C_4));
        this.move(robot, this.position(C_2, C_6), this.position(C_2, C_4));
        this.move(robot, this.position(C_3, C_6), this.position(C_3, C_4));
        this.move(robot, this.position(C_4, C_6), this.position(C_4, C_4));
        this.move(robot, this.position(C_5, C_6), this.position(C_5, C_4));
        this.move(robot, this.position(C_6, C_6), this.position(C_6, C_4));
        this.move(robot, this.position(C_7, C_6), this.position(C_7, C_4));

        //Other Pieces
        this.move(robot, this.position(C_0, C_7), this.position(C_0, C_4));
        this.move(robot, this.position(C_1, C_7), this.position(C_1, C_4));
        this.move(robot, this.position(C_2, C_7), this.position(C_2, C_4));
        this.move(robot, this.position(C_3, C_7), this.position(C_3, C_4));
        this.move(robot, this.position(C_4, C_7), this.position(C_4, C_4));
        this.move(robot, this.position(C_5, C_7), this.position(C_5, C_4));
        this.move(robot, this.position(C_6, C_7), this.position(C_6, C_4));
        this.move(robot, this.position(C_7, C_7), this.position(C_7, C_4));
    }
/*
    @Test
    void randomMoves(final FxRobot robot) throws InterruptedException {
        //TODO: Make possible moves
        final Random random = new Random();
        for (int i = 0; i < 10; i++) {
            var l = this.model.getActualMatch().get().getBoard().getBoardState().stream()
                    .filter(p -> p.get)
            this.move(robot,
                    this.position(random.nextInt(C_8), random.nextInt(C_8)),
                    this.position(random.nextInt(C_8), random.nextInt(C_8)));
        }
    }
*/
    private Point2D position(final int columns, final int row) {
        final double widthTile = this.boardView.getWidth() / this.columns;
        final double heightTile = this.boardView.getHeight() / this.rows;
        return new Point2D(this.stage.getX() + this.boardView.getLayoutX() + (widthTile * columns) + (widthTile / 2), 
                this.stage.getY() + this.boardView.getLayoutY() + (heightTile * row) + (heightTile / 2));
    }

    private void move(final FxRobot robot, final Point2D source, final Point2D destination) {
        robot.moveTo(source)
        .drag(MouseButton.PRIMARY)
        .dropTo(destination);
    }

}
