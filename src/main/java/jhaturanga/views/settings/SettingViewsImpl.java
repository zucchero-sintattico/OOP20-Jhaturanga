package jhaturanga.views.settings;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import jhaturanga.commons.style.ApplicationStyle.ApplicationsStyleEnum;

public final class SettingViewsImpl implements SettingsView {

    @FXML
    private ChoiceBox<ApplicationsStyleEnum> styleListChoiceBox;

    @FXML
    void initialize() {
        styleListChoiceBox.getItems().addAll(ApplicationsStyleEnum.values());
    }

}
