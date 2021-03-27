package jhaturanga.controllers.online.join;

import jhaturanga.controllers.Controller;

public interface OnlineJoinController extends Controller {

    /**
     * 
     * @param matchID
     * @param onReady
     */
    void join(String matchID, Runnable onReady);
}
