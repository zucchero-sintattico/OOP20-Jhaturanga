package jhaturanga.controllers.home;

import java.util.Optional;

import jhaturanga.controllers.BasicController;
import jhaturanga.model.user.User;

public final class HomeControllerImpl extends BasicController implements HomeController {

    @Override
    public Optional<User> getFirstUser() {
        return this.getApplicationInstance().getFirstUser();
    }

    @Override
    public Optional<User> getSecondUser() {
        return this.getApplicationInstance().getSecondUser();
    }

}
