package jhaturanga.views.online.match;

import jhaturanga.controllers.match.OnlineMatchController;
import jhaturanga.views.match.MatchView;

public interface OnlineMatchView extends MatchView {

    OnlineMatchController getOnlineMatchController();
}
