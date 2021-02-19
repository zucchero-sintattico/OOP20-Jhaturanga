package jhaturanga.views.gmaetypemenu;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import jhaturanga.controllers.gametypemenu.GameTypeMenuController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.views.AbstractView;

public final class GameTypeMenuViewImpl extends AbstractView implements GameTypeMenuView {

    @FXML
    private AnchorPane gameGrid;

    @FXML
    private final GridPane grid = new GridPane();

    @FXML
    void inizialize() {

    }

    @Override
    public void init() {

        int enumCounter = 0;
        for (int y = 0; y < this.getGameTypeMenuController().getNumberOfColumn(); y++) {
            for (int x = 0; x < this.getGameTypeMenuController().getNumberOfRow(); x++) {

                final Tabs tab = new Tabs(gameGrid.widthProperty(), gameGrid.heightProperty(),
                        this.getGameTypeMenuController().getnNumbersOfGameTipes());

                tab.setText(GameTypesEnum.values()[enumCounter].toString());
                enumCounter++;

                grid.add(tab, x, y);

            }

        }
        if (enumCounter < this.getGameTypeMenuController().getnNumbersOfGameTipes()) {

            final Tabs tab = new Tabs(gameGrid.widthProperty(), gameGrid.heightProperty(),
                    this.getGameTypeMenuController().getnNumbersOfGameTipes());

            tab.setText(GameTypesEnum.values()[enumCounter].toString());

            grid.add(tab, this.getGameTypeMenuController().getNumberOfColumn() + 1, 0);
        }

        gameGrid.getChildren().add(grid);

    }

    @Override
    public GameTypeMenuController getGameTypeMenuController() {

        return (GameTypeMenuController) this.getController();
    }

}
