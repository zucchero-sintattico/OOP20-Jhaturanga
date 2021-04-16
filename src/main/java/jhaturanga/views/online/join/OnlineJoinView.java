package jhaturanga.views.online.join;

import org.eclipse.paho.client.mqttv3.MqttException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import jhaturanga.controllers.online.join.OnlineJoinController;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * The View where the user join an online match.
 *
 */
public final class OnlineJoinView extends AbstractJavaFXView {

    @FXML
    private TextField matchID;

    @Override
    public void init() {
    }

    private void onReady() {
        System.out.println("READYY");
        Platform.runLater(() -> PageLoader.getInstance().switchPage(this.getStage(), Pages.ONLINE_MATCH,
                this.getController().getApplicationInstance()));
    }

    @FXML
    public void onJoinClick(final ActionEvent event) {
        final String matchID = this.matchID.getText();
        try {
            this.getOnlineJoinController().join(matchID, this::onReady);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.getInstance().switchPage(this.getStage(), Pages.ONLINE,
                this.getController().getApplicationInstance());
    }

    private OnlineJoinController getOnlineJoinController() {
        return (OnlineJoinController) this.getController();
    }
}
