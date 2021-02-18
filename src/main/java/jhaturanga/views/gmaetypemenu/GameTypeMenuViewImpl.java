package jhaturanga.views.gmaetypemenu;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import jhaturanga.controllers.gametypemenu.GameTypeMenuController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.views.AbstractView;

public final class GameTypeMenuViewImpl extends AbstractView implements GameTypeMenuView {

    @FXML
    private GridPane gameGrid;

    @FXML
    void inizialize() {

    }

    @Override
    public void init() {
        gameGrid.setAlignment(Pos.CENTER);

        for (int x = 0; x < this.getGameTypeMenuController().getNumberOfColumn(); x++) {
            for (int y = 0; y < this.getGameTypeMenuController().getNumberOfRow(); y++) {
                final Button button = new Button();
                button.setText(GameTypesEnum.values()[x].toString());
                gameGrid.add(new Button(), x, y, 1, 1);
            }

        }

        Button button = new Button();
        gameGrid.add(button, 0, 0, 1, 1);
    }

    @Override
    public GameTypeMenuController getGameTypeMenuController() {

        return (GameTypeMenuController) this.getController();
    }

}
