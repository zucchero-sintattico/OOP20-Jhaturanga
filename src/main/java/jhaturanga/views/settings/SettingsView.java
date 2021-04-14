package jhaturanga.views.settings;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import jhaturanga.commons.settings.dynamicconfiguration.configuratonobject.ApplicationStyleConfigurationObjectStrategy;
import jhaturanga.commons.settings.dynamicconfiguration.configuratonobject.PieceStyleconfigurationObjectStrategy;
import jhaturanga.commons.settings.filegetter.ApplicationStyleListStrategy;
import jhaturanga.commons.settings.filegetter.PieceStyleListStrategy;
import jhaturanga.controllers.settings.SettingsController;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class SettingsView extends AbstractJavaFXView {

    @FXML
    private ChoiceBox<ApplicationStyleConfigurationObjectStrategy> styleListChoiceBox;

    @FXML
    private ChoiceBox<PieceStyleconfigurationObjectStrategy> piecesListChoiceBox;

    @FXML
    private Slider volumeSlider;

    private SettingsController getSettingController() {
        return (SettingsController) this.getController();
    }

    @Override
    public void init() {

        this.styleListChoiceBox.getItems().addAll(new ApplicationStyleListStrategy().getAll());
        this.piecesListChoiceBox.getItems().addAll(new PieceStyleListStrategy().getAll());

        this.styleListChoiceBox.setValue(this.getSettingController().getCurrentApplicationStyle());
        this.piecesListChoiceBox.setValue(this.getSettingController().getCurrentPieceStyle());

        this.volumeSlider.setValue(this.getSettingController().getCurrentApplicationVolume());

    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void saveButton(final Event event) {

        this.getSettingController().setApplicationStyle(this.styleListChoiceBox.getValue());
        this.getSettingController().setPieceStyle(this.piecesListChoiceBox.getValue());
        this.getSettingController().setApplicationVolume(this.volumeSlider.getValue());
        PageLoader.switchPage(this.getStage(), Pages.SETTINGS, this.getController().getApplicationInstance());

    }

}
