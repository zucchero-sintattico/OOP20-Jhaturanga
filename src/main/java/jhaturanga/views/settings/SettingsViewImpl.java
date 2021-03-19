package jhaturanga.views.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jhaturanga.controllers.settings.SettingsController;
import jhaturanga.views.AbstractView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class SettingsViewImpl extends AbstractView implements SettingsView {

//    @FXML
//    private ChoiceBox<ApplicationStyleEnum> styleListChoiceBox;

    @Override
    public void init() {

    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }

    @Override
    public SettingsController getSettingsController() {
        return (SettingsController) this.getController();
    }

//    @FXML
//    public void initialize() {
//        styleListChoiceBox.getItems().addAll(ApplicationStyleEnum.values());
//        styleListChoiceBox.setValue(ApplicationStyle.getApplicationStyle());
//    }
//
//    @FXML
//    public void saveButton(final Event event) {
//        this.getSettingsController().setApplicationStyle(styleListChoiceBox.getValue());
//        PageLoader.updatePage(getStage(), Pages.SETTINGS);
//    }
//
//    @FXML
//    public void gameTypeMenuButton(final Event event) throws IOException {
//        PageLoader.switchPage(this.getStage(), Pages.GAME_TYPE_MENU, this.getController().getModel());
//    }

}
