package jhaturanga.views.match;

import jhaturanga.controllers.match.MatchController;
import jhaturanga.views.JavaFXView;

public interface MatchView extends JavaFXView {

    default MatchController getMatchController() {
        return (MatchController) this.getController();
    }
}
