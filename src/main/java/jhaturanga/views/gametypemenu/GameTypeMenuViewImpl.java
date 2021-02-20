package jhaturanga.views.gametypemenu;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import jhaturanga.controllers.gametypemenu.GameTypeMenuController;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
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

        final Iterator<GameType> it = Arrays.stream(GameTypesEnum.values())
                .map(i -> i.getNewGameType(new PlayerImpl(PlayerColor.WHITE), new PlayerImpl(PlayerColor.BLACK)))
                .collect(Collectors.toList()).iterator();
        final int xUpperBoundary = (this.getGameTypeMenuController().getnNumbersOfGameTipes() % 2 == 0
                ? this.getGameTypeMenuController().getNumberOfRow()
                : this.getGameTypeMenuController().getNumberOfRow() + 1);

        for (int y = 0; y < this.getGameTypeMenuController().getNumberOfColumn(); y++) {
            for (int x = 0; x < xUpperBoundary; x++) {

                if (it.hasNext()) {
                    final GameType gameType = it.next();
                    final Tabs tab = new Tabs(gameGrid.widthProperty(), gameGrid.heightProperty(),
                            this.getGameTypeMenuController().getnNumbersOfGameTipes());
                    tab.setButtonText(gameType.getGameName());
                    tab.setDescription(gameType.getGameTypeDescription());
                    tab.getButton().setOnAction(e -> {
                        this.getGameTypeMenuController().setGameType(gameType);
                        try {
                            PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    });
                    grid.add(tab, x, y);
                }
            }

        }

        gameGrid.getChildren().add(grid);

    }

    @Override
    public GameTypeMenuController getGameTypeMenuController() {

        return (GameTypeMenuController) this.getController();
    }

}
