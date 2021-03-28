package jhaturanga.controllers.online.join;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.match.NetworkMatch;

public final class OnlineJoinControllerImpl extends AbstractController implements OnlineJoinController {

    @Override
    public void join(final String matchID, final Runnable onReady) {
        final NetworkMatch match = new NetworkMatch(this.getApplicationInstance().getFirstUser().get(), onReady);
        match.join(matchID);
        this.getApplicationInstance().setMatch(match);
    }

}
