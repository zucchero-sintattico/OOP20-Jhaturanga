package jhaturanga.views.online.create;

import jhaturanga.controllers.online.OnlineSetupController;
import jhaturanga.views.View;

public interface OnlineCreateView extends View {

    default OnlineSetupController getOnlineSetupController() {
        return (OnlineSetupController) this.getController();
    }
}
