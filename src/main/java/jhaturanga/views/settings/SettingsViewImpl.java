package jhaturanga.views.settings;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import jhaturanga.commons.style.ApplicationStyle;
import jhaturanga.commons.style.ApplicationStyleEnum;

import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;


public final class SettingsViewImpl extends AbstractJavaFXView  {

    @FXML
    private ChoiceBox<ApplicationStyleEnum> styleListChoiceBox;

    @Override
    public void init() {
        styleListChoiceBox.getItems().addAll(ApplicationStyleEnum.values());
//        this.getStage().setMinHeight(this.getStage().getHeight());
//        this.getStage().setMinWidth(this.getStage().getWidth());

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
        ApplicationStyle.setApplicationStyle(this.styleListChoiceBox.getValue());
        PageLoader.switchPage(this.getStage(), Pages.SETTINGS, this.getController().getApplicationInstance());

    }



}
