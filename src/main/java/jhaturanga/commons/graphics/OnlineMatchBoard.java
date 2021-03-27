package jhaturanga.commons.graphics;

import jhaturanga.views.match.MatchView;

public class OnlineMatchBoard extends MatchBoard {

    public OnlineMatchBoard(final MatchView matchView, final Runnable onMatchFinish) {
        super(matchView, onMatchFinish);
    }

}
