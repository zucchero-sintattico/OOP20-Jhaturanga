package jhaturanga.views.settings;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import jhaturanga.commons.style.ApplicationStyleEnum;
import jhaturanga.commons.style.PieceStyleEnum;
import jhaturanga.commons.style.StyleSettingManager;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class SettingsViewImpl extends AbstractJavaFXView {

    @FXML
    private ChoiceBox<ApplicationStyleEnum> styleListChoiceBox;

    @FXML
    private ChoiceBox<PieceStyleEnum> piecesListChoiceBox;

    @Override
    public void init() {
        this.styleListChoiceBox.getItems().addAll(ApplicationStyleEnum.values());
        this.piecesListChoiceBox.getItems().addAll(PieceStyleEnum.values());
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
        try {
            StyleSettingManager.setAndSaveApplicationStyle(this.styleListChoiceBox.getValue());
            StyleSettingManager.setAndSavePieceStyle(this.piecesListChoiceBox.getValue());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PageLoader.switchPage(this.getStage(), Pages.SETTINGS, this.getController().getApplicationInstance());

    }

}
