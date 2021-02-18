package jhaturanga.views.gmaetypemenu;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import jhaturanga.controllers.gametypemenu.GameTypeMenuController;
import jhaturanga.views.AbstractView;

public final class GameTypeMenuViewImpl extends AbstractView implements GameTypeMenuView {

    @FXML
    private GridPane gameGrid;

    @Override
    public void init() {
//        Button button = new Button();
//        gameGrid.get;
    }

    @Override
    public GameTypeMenuController getSettingsController() {
        // TODO Auto-generated method stub
        return null;
    }

}
