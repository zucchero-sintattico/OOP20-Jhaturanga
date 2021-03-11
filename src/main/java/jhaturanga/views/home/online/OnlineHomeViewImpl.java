package jhaturanga.views.home.online;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.NetworkMatch;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class OnlineHomeViewImpl extends AbstractView implements OnlineHomeView {

    private NetworkMatch match;

    @FXML
    private TextField matchID;

    @FXML
    private Label createdMatchID;

    @FXML
    public void onCreate(final Event e) {
        System.out.println("CREATE");
        this.match = new NetworkMatch(UsersManager.GUEST, () -> {
            System.out.println("HOST IS READY");

            this.getController().getModel().setMatch(this.match);
            this.getController().getModel().setWhitePlayer(this.match.getWhitePlayer());
            this.getController().getModel().setBlackPlayer(this.match.getWhitePlayer());

            System.out.println("Match setted");
            Platform.runLater(() -> {
                try {
                    PageLoader.switchPageWithSameController(this.getStage(), Pages.ONLINE_GAME, this.getController());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        });

        final String id = this.match.create(GameTypesEnum.CLASSIC_GAME, null);
        this.createdMatchID.setText(id);

    }

    @FXML
    public void onJoin(final Event e) {

        System.out.println("JOIN - GAME ID = " + this.matchID.getText());

        this.match = new NetworkMatch(UsersManager.GUEST, () -> {
            System.out.println("CLIENT IS READY");

            this.getController().getModel().setMatch(this.match);
            this.getController().getModel().setWhitePlayer(this.match.getWhitePlayer());
            this.getController().getModel().setBlackPlayer(this.match.getWhitePlayer());

            System.out.println("Match setted");

            Platform.runLater(() -> {
                try {
                    PageLoader.switchPageWithSameController(this.getStage(), Pages.ONLINE_GAME, this.getController());
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });

        });

        this.match.join(this.matchID.getText());
    }

    @Override
    public void init() {

    }

}
