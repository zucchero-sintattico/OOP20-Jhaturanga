package jhaturanga.views.problem;

import java.util.stream.Stream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import jhaturanga.commons.Pair;
import jhaturanga.controllers.problem.ProblemController;
import jhaturanga.model.chessproblems.ChessProblemsEnum;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * Basic implementation for the Game Type Selection View.
 */
public final class ProblemView extends AbstractJavaFXView {

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private AnchorPane container;

    @FXML
    private Label problemInfoTitle;

    private final GridPane grid = new GridPane();

    private ChessProblemsEnum selectedProblem = ChessProblemsEnum.PROBLEM_ONE;

    private void setupDefaultValues() {
        // Setup the default game type
        this.selectedProblem = ChessProblemsEnum.PROBLEM_ONE;
        this.problemInfoTitle.setText(this.selectedProblem.getName());
        this.getProblemController().setProblem(this.selectedProblem);
    }

    private void setupModesGrid() {
        Stream.iterate(0, i -> i + 1).limit(ChessProblemsEnum.values().length)
                .map(i -> new Pair<>(i, this.mapChessProblemToStackPane(ChessProblemsEnum.values()[i])))
                .forEach(x -> this.addStackPaneToGrid(x.getX(), x.getY()));

    }

    private void onModeClick(final ChessProblemsEnum chessProblem) {
        this.selectedProblem = chessProblem;
        this.getProblemController().setProblem(chessProblem);
        this.problemInfoTitle.setText(chessProblem.getName());
    }

    private StackPane mapChessProblemToStackPane(final ChessProblemsEnum chessProblem) {
        final Button btn = new Button(chessProblem.getName());
        btn.setOnMouseClicked(e -> this.onModeClick(chessProblem));
        final StackPane p = new StackPane(btn);
        p.getStyleClass().add("mode-tab");
        return p;
    }

    private void addStackPaneToGrid(final int number, final StackPane pane) {
        final String[] colors = { "first", "second" };
        pane.getStyleClass().add(colors[number % 2]);
        this.grid.add(pane, number % 3, number / 3);
        GridPane.setHalignment(pane, HPos.CENTER);
        GridPane.setValignment(pane, VPos.CENTER);
    }

    private void setupBindings() {
        final double scrollSize = 30;
        this.container.minWidthProperty().set(this.grid.widthProperty().get());
        this.container.maxWidthProperty().set(this.grid.widthProperty().get());
        this.container.minWidthProperty().bind(this.grid.widthProperty());
        this.container.maxWidthProperty().bind(this.grid.widthProperty());
        this.scrollpane.minViewportWidthProperty().bind(this.container.widthProperty().add(scrollSize));
    }

    @Override
    public void init() {
        this.container.getChildren().add(grid);
        this.setupBindings();
        this.setupModesGrid();
        this.setupDefaultValues();
    }

    @FXML
    public void onSelectClick(final ActionEvent event) {
        this.getProblemController().createMatch();
        PageLoader.switchPage(this.getStage(), Pages.MATCH, this.getController().getApplicationInstance());
    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.SELECT_GAME, this.getController().getApplicationInstance());
    }

    private ProblemController getProblemController() {
        return (ProblemController) this.getController();
    }

}
