package jhaturanga.views.gametypemenu;

import java.util.Arrays;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import jhaturanga.controllers.gametypemenu.GameTypeMenuController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class GameTypeMenuViewImpl extends AbstractView implements GameTypeMenuView {

    @FXML
    private AnchorPane gameModesPane;

    private final GridPane grid = new GridPane();

    @Override
    public void init() {

        final Iterator<GameTypesEnum> it = Arrays.stream(GameTypesEnum.values()).iterator();

        final int xUpperBound = this.getGameTypeMenuController().getNumberOfGameTypes() % 2 == 0
                ? this.getGameTypeMenuController().getNumberOfRow()
                : this.getGameTypeMenuController().getNumberOfRow() + 1;

        for (int y = 0; y < this.getGameTypeMenuController().getNumberOfColumn(); y++) {
            for (int x = 0; x < xUpperBound; x++) {
                if (it.hasNext()) {
                    final GameTypesEnum gameType = it.next();
                    final Tabs tab = new Tabs(gameModesPane.widthProperty(), gameModesPane.heightProperty(),
                            this.getGameTypeMenuController().getNumberOfGameTypes());
                    tab.setButtonText(gameType.toString());
                    tab.setDescription(gameType.getGameTypeDescription());
                    tab.getButton().setOnAction(e -> {
                        this.getGameTypeMenuController().setGameType(gameType);
                        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
                    });
                    this.grid.add(tab, x, y);
                }
            }
        }
        this.gameModesPane.getChildren().add(grid);
    }

    @Override
    public GameTypeMenuController getGameTypeMenuController() {
        return (GameTypeMenuController) this.getController();
    }

}
