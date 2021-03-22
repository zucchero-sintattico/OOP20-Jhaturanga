package jhaturanga.views.settings;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import jhaturanga.commons.style.ApplicationStyleEnum;
import jhaturanga.commons.style.PieceStyleEnum;
import jhaturanga.controllers.settings.SettingsController;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class SettingViewsImpl extends AbstractView implements SettingsView {

    @FXML
    private ChoiceBox<ApplicationStyleEnum> styleListChoiceBox;

    @FXML
    private ChoiceBox<PieceStyleEnum> gameStyleListChoiceBox;

    @Override
    public SettingsController getSettingsController() {
        return (SettingsController) this.getController();
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void saveButton(final Event event) {
        this.getSettingsController().setApplicationStyle(styleListChoiceBox.getValue());
        PageLoader.updatePage(getStage());
        this.getSettingsController().setPlayerStyle(gameStyleListChoiceBox.getValue());
    }

    @FXML
    public void backToHomePage(final Event event) throws IOException {

        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }

    @FXML
    public void gameTypeMenuButton(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.GAME_TYPE_MENU, this.getController().getModel());
    }

    @Override
    public void init() {
        styleListChoiceBox.getItems().addAll(ApplicationStyleEnum.values());
        styleListChoiceBox.setValue(this.getSettingsController().getCurrentApplicationStyle());
        gameStyleListChoiceBox.getItems().addAll(PieceStyleEnum.values());
    }

}
