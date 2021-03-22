package jhaturanga.views.setup;

import java.util.Arrays;
import java.util.stream.Stream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import jhaturanga.commons.Pair;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.timer.DefaultTimers;
import jhaturanga.views.AbstractView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * Basic implementation for the Game Type Selection View.
 */
public final class SetupViewImpl extends AbstractView implements SetupView {

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private AnchorPane container;

    @FXML
    private Label modeInfoTitle;

    @FXML
    private Label modeInfoDescription;

    @FXML
    private ChoiceBox<DefaultTimers> timerChoice;

    @FXML
    private ChoiceBox<String> whitePlayerChoice;

    private GameTypesEnum selectedGameType;

    @FXML
    private final GridPane grid = new GridPane();

    private void onTimerChoiceChange() {
        this.getGameTypeController().getModel().setTimer(this.timerChoice.getValue());
    }

    private void setupWhitePlayerChoice() {
        this.whitePlayerChoice.getItems().add("Random");
        this.whitePlayerChoice.getItems()
                .add(this.getGameTypeController().getModel().getFirstUser().get().getUsername());
        this.whitePlayerChoice.getSelectionModel().select("Random");
    }

    private void setupTimer() {
        this.timerChoice.getItems().addAll(Arrays.asList(DefaultTimers.values()));
        this.timerChoice.getSelectionModel().select(DefaultTimers.TEN_MINUTES);
        this.timerChoice.setOnAction(e -> this.onTimerChoiceChange());
    }

    private void setupDefaultValues() {
        // Setup the default game type
        this.selectedGameType = GameTypesEnum.CLASSIC_GAME;
        this.modeInfoTitle.setText(this.selectedGameType.getName());
        this.modeInfoDescription.setText(this.selectedGameType.getGameTypeDescription());
    }

    private void setupModesGrid() {
        Stream.iterate(0, i -> i + 1).limit(GameTypesEnum.values().length)
                .map(i -> new Pair<>(i, this.gameTypeToStackPane(GameTypesEnum.values()[i])))
                .forEach(x -> this.addStackPaneToGrid(x.getX(), x.getY()));

    }

    private void onModeClick(final GameTypesEnum gameType) {
        this.getGameTypeController().setGameType(gameType);
        this.selectedGameType = gameType;
        this.modeInfoTitle.setText(gameType.getName());
        this.modeInfoDescription.setText(gameType.getGameTypeDescription());
    }

    private StackPane gameTypeToStackPane(final GameTypesEnum gameType) {

        final Button btn = new Button(gameType.getName());
        btn.setOnMouseClicked(e -> this.onModeClick(gameType));

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

        this.scrollpane.minWidthProperty().set(this.container.widthProperty().get() + scrollSize);
        this.scrollpane.maxWidthProperty().set(this.container.widthProperty().get() + scrollSize);
        this.scrollpane.minWidthProperty().bind(this.container.widthProperty().add(scrollSize));
        this.scrollpane.maxWidthProperty().bind(this.container.widthProperty().add(scrollSize));
    }

    /**
     * Force a refresh of the stage beacuse when mode are added to gridpane the
     * scrollpane wont resize itself. TODO: Find the cause and remove this method.
     */
    private void forceRefresh() {
        final double increment = 0.001;
        this.getStage().getScene().getWindow().setWidth(this.getStage().getScene().getWidth() + increment);
        this.getStage().getScene().getWindow().setWidth(this.getStage().getScene().getWidth() - increment);
    }

    @FXML
    public void initialize() {

    }

    @Override
    public void init() {

        this.getStage().setMinWidth(this.getStage().getWidth());
        this.getStage().setMinHeight(this.getStage().getHeight());

        this.container.getChildren().add(grid);
        this.setupBindings();
        this.setupTimer();
        this.setupWhitePlayerChoice();
        this.setupModesGrid();
        this.setupDefaultValues();
        this.forceRefresh();

    }

    @FXML
    public void onSelectClick(final ActionEvent event) {
        this.getGameTypeController().setGameType(this.selectedGameType);
        PageLoader.switchPage(this.getStage(), Pages.RESUME, this.getController().getModel());
    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.NEWGAME, this.getController().getModel());
    }

}
