package jhaturanga.views.home.online;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.NetworkMatch;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class OnlineHomeViewImpl extends AbstractJavaFXView implements OnlineHomeView {

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

            // TODO
//            this.getController().getModel().setMatch(this.match);
//            this.getController().getModel().setWhitePlayer(this.match.getWhitePlayer());
//            this.getController().getModel().setBlackPlayer(this.match.getWhitePlayer());

            System.out.println("Match setted");
            Platform.runLater(() -> {
                PageLoader.switchPageWithSameController(this.getStage(), Pages.ONLINE_MATCH, this.getController());
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

            // TODO
//            this.getController().getModel().setMatch(this.match);
//            this.getController().getModel().setWhitePlayer(this.match.getWhitePlayer());
//            this.getController().getModel().setBlackPlayer(this.match.getWhitePlayer());

            System.out.println("Match setted");

            Platform.runLater(() -> {
                PageLoader.switchPageWithSameController(this.getStage(), Pages.ONLINE_MATCH, this.getController());

            });

        });

        this.match.join(this.matchID.getText());
    }

    @Override
    public void init() {

    }

}
