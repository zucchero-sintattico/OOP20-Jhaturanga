package jhaturanga.views.gametypemenu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import jhaturanga.controllers.gametypemenu.GameTypeMenuController;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.views.AbstractView;

public final class GameTypeMenuViewImpl extends AbstractView implements GameTypeMenuView {

    @FXML
    private AnchorPane gameGrid;

    @FXML
    private final GridPane grid = new GridPane();

    private final List<GameType> l = Arrays.stream(GameTypesEnum.values())
            .map(i -> i.getNewGameType(new PlayerImpl(PlayerColor.WHITE), new PlayerImpl(PlayerColor.BLACK)))
            .collect(Collectors.toList());

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

                tab.setButtonText(l.get(enumCounter).getGameName());
                enumCounter++;

                tab.setDescription(l.get(enumCounter).getGameTypeDescription());
                grid.add(tab, x, y);

            }

        }

        if (enumCounter < this.getGameTypeMenuController().getnNumbersOfGameTipes()) {

            final Tabs tab = new Tabs(gameGrid.widthProperty(), gameGrid.heightProperty(),
                    this.getGameTypeMenuController().getnNumbersOfGameTipes());

            tab.setButtonText(l.get(enumCounter).getGameName());
            tab.setDescription(l.get(enumCounter).getGameTypeDescription());
            grid.add(tab, this.getGameTypeMenuController().getNumberOfColumn() + 1, 0);
        }

        // grid.setGridLinesVisible(true);

        gameGrid.getChildren().add(grid);

    }

    @Override
    public GameTypeMenuController getGameTypeMenuController() {

        return (GameTypeMenuController) this.getController();
    }

}
