package jhaturanga.views.online.create;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import jhaturanga.commons.Pair;
import jhaturanga.controllers.online.create.OnlineCreateController;
import jhaturanga.model.game.type.GameType;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class OnlineCreateView extends AbstractJavaFXView {

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private AnchorPane container;

    @FXML
    private Label modeInfoTitle;

    @FXML
    private Label modeInfoDescription;

    private final GridPane grid = new GridPane();

    private GameType selectedGameType;

    @Override
    public void init() {
        this.container.getChildren().add(grid);
        this.setupBindings();
        this.setupModesGrid();
        this.setupDefaultValues();

    }

    private void onSelectedGameTypeChange() {
        this.getOnlineSetupController().setGameType(this.selectedGameType);
    }

    private void setupDefaultValues() {
        // Setup the default game type
        this.selectedGameType = GameType.CLASSIC_GAME;
        this.modeInfoTitle.setText(this.selectedGameType.getName());
        this.modeInfoDescription.setText(this.selectedGameType.getGameTypeDescription());

        this.getOnlineSetupController().setGameType(this.selectedGameType);
    }

    private void setupModesGrid() {
        final List<GameType> validModes = Arrays.stream(GameType.values()).filter(this::isPlayableGameType)
                .collect(Collectors.toList());
        IntStream.range(0, validModes.size()).mapToObj(i -> new Pair<>(i, this.gameTypeToStackPane(validModes.get(i))))
                .forEach(x -> this.addStackPaneToGrid(x.getX(), x.getY()));
    }

    private boolean isPlayableGameType(final GameType gameType) {
        return !(gameType.equals(GameType.CHESS_PROBLEM) || gameType.equals(GameType.CUSTOM_BOARD_VARIANT)
                || gameType.equals(GameType.BOMB_VARIANT));
    }

    private void onModeClick(final GameType gameType) {
        this.getOnlineSetupController().setGameType(gameType);
        this.selectedGameType = gameType;
        this.modeInfoTitle.setText(gameType.getName());
        this.modeInfoDescription.setText(gameType.getGameTypeDescription());
        this.onSelectedGameTypeChange();
    }

    private StackPane gameTypeToStackPane(final GameType gameType) {

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
        this.container.minWidthProperty().bind(this.scrollpane.widthProperty());
        this.grid.minWidthProperty().bind(this.container.widthProperty());
    }

    private void onMatchReady() {
        System.out.println("READY");
        Platform.runLater(() -> PageLoader.switchPage(this.getStage(), Pages.ONLINE_MATCH,
                this.getController().getApplicationInstance()));
    }

    @FXML
    public void onSelectClick(final ActionEvent event) {
        final String matchID = this.getOnlineSetupController().createMatch(this::onMatchReady);
        final Alert a = new Alert(AlertType.INFORMATION, "Your Match ID is : " + matchID
                + "\nTell someone to go in Join section and enter this Match ID to play with you.");
        a.show();

    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.ONLINE, this.getController().getApplicationInstance());
    }

    private OnlineCreateController getOnlineSetupController() {
        return (OnlineCreateController) this.getController();
    }
}
