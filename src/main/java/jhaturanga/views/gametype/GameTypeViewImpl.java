package jhaturanga.views.gametype;

import java.util.Arrays;
import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.views.AbstractView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * Basic implementation for the Game Type Selection View.
 */
public final class GameTypeViewImpl extends AbstractView implements GameTypeView {

    private final class ModeTab extends AnchorPane {

        ModeTab(final String title) {
            this.setPadding(new Insets(20));

            final Button btn = new Button(title);
            btn.setMinHeight(100);
            btn.setMinWidth(100);

            this.getChildren().add(btn);
        }
    }

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private AnchorPane container;

    @Override
    public void init() {

//        this.grid.minWidthProperty().bind(this.scrollpane.widthProperty());
//        this.grid.minHeightProperty().bind(this.scrollpane.heightProperty());

        final Iterator<GameTypesEnum> it = Arrays.stream(GameTypesEnum.values()).iterator();

        final int xUpperBound = this.getGameTypeController().getNumberOfGameTypes() % 2 == 0
                ? this.getGameTypeController().getNumberOfRow()
                : this.getGameTypeController().getNumberOfRow() + 1;
        final GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: #aaa");
        grid.gridLinesVisibleProperty().set(true);
        this.container.getChildren().add(grid);

        this.container.prefWidthProperty().bind(this.scrollpane.widthProperty());
        grid.prefWidthProperty().bind(this.container.widthProperty());

        for (int y = 0; y < this.getGameTypeController().getNumberOfColumn(); y++) {
            for (int x = 0; x < xUpperBound; x++) {
                if (it.hasNext()) {
                    final GameTypesEnum gameType = it.next();
                    final Button btn = new Button(gameType.toString());
//                    final Tabs tab = new Tabs(gameModesPane.widthProperty(), gameModesPane.heightProperty(),
//                            this.getGameTypeMenuController().getNumberOfGameTypes());
//                    tab.setButtonText(gameType.toString());
//                    tab.setDescription(gameType.getGameTypeDescription());
//                    tab.getButton().setOnAction(e -> {
//                        this.getGameTypeMenuController().setGameType(gameType);
//                        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
//                    });

                    btn.setMinHeight(350);
                    btn.setMinWidth(50);

                    final StackPane p = new StackPane(btn);
                    p.setPadding(new Insets(30));
                    grid.add(p, x, y);

                    GridPane.setHalignment(p, HPos.CENTER);
                    GridPane.setValignment(p, VPos.CENTER);
                }
            }
        }
    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.NEWGAME, this.getController().getModel());
    }

    @FXML
    public void onConfirmSelect(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.RESUME, this.getController().getModel());
    }

}
