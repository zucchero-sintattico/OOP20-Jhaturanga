package jhaturanga.views.match.online;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.NetworkMatch;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.views.AbstractView;

public final class OnlineMatchViewImpl extends AbstractView implements OnlineMatchView {

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
        });

        final String id = this.match.create(GameTypesEnum.CLASSIC_GAME, null);
        this.createdMatchID.setText(id);
    }

    @FXML
    public void onJoin(final Event e) {

        System.out.println("JOIN - GAME ID = " + this.matchID.getText());

        this.match = new NetworkMatch(UsersManager.GUEST, () -> {
            System.out.println("CLIENT IS READY");
        });

        this.match.join(this.matchID.getText());
    }

    @Override
    public void init() {

    }

}
