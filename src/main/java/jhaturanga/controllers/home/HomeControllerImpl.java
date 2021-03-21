package jhaturanga.controllers.home;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.user.User;

public final class HomeControllerImpl extends AbstractController implements HomeController {

    @Override
    public User getFirstUser() {
        return this.getModel().getFirstUser().get();
    }

}
