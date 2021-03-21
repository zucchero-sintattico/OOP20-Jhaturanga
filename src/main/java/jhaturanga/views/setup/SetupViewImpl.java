package jhaturanga.views.setup;

import java.util.Arrays;
import java.util.Iterator;

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
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.timer.DefaultsTimers;
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
    private ChoiceBox<DefaultsTimers> timerChoice;

    private GameTypesEnum selectedGameType;

    private void onTimerChoiceChange() {
        this.getGameTypeController().getModel().setTimer(this.timerChoice.getValue());
    }

    @Override
    public void init() {

        this.getStage().setMinWidth(this.getStage().getWidth());
        this.getStage().setMinHeight(this.getStage().getHeight());

        this.timerChoice.getItems().addAll(Arrays.asList(DefaultsTimers.values()));
        this.timerChoice.getSelectionModel().select(DefaultsTimers.TEN_MINUTES);
        this.timerChoice.setOnAction(e -> this.onTimerChoiceChange());

        final Iterator<GameTypesEnum> it = Arrays.stream(GameTypesEnum.values()).iterator();

        final int xUpperBound = this.getGameTypeController().getNumberOfGameTypes() % 2 == 0
                ? this.getGameTypeController().getNumberOfRow()
                : this.getGameTypeController().getNumberOfRow() + 1;
        final GridPane grid = new GridPane();
        grid.gridLinesVisibleProperty().set(true);
        this.container.getChildren().add(grid);

        final double scrollSize = 30;
        this.container.minWidthProperty().set(grid.widthProperty().get());
        this.container.maxWidthProperty().set(grid.widthProperty().get());
        this.container.minWidthProperty().bind(grid.widthProperty());
        this.container.maxWidthProperty().bind(grid.widthProperty());

        this.scrollpane.minWidthProperty().set(this.container.widthProperty().get() + scrollSize);
        this.scrollpane.maxWidthProperty().set(this.container.widthProperty().get() + scrollSize);
        this.scrollpane.minWidthProperty().bind(this.container.widthProperty().add(scrollSize));
        this.scrollpane.maxWidthProperty().bind(this.container.widthProperty().add(scrollSize));

        for (int y = 0; y < this.getGameTypeController().getNumberOfColumn(); y++) {
            for (int x = 0; x < xUpperBound; x++) {
                if (it.hasNext()) {
                    final GameTypesEnum gameType = it.next();
                    final Button btn = new Button(gameType.getName());

                    final StackPane p = new StackPane(btn);
                    p.getStyleClass().add("mode-tab");

                    // p.setPadding(new Insets(30));
                    grid.add(p, x, y);
                    btn.setOnMouseClicked((e) -> {
                        this.getGameTypeController().setGameType(gameType);
                        this.selectedGameType = gameType;
                        this.modeInfoTitle.setText(gameType.getName());
                        this.modeInfoDescription.setText(gameType.getGameTypeDescription());
                    });

                    GridPane.setHalignment(p, HPos.CENTER);
                    GridPane.setValignment(p, VPos.CENTER);

                }
            }
        }

        this.selectedGameType = GameTypesEnum.CLASSIC_GAME;
        this.modeInfoTitle.setText(this.selectedGameType.toString());
        this.modeInfoDescription.setText(this.selectedGameType.getGameTypeDescription());

        this.getStage().show();
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
