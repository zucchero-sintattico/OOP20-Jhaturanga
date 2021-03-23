package jhaturanga.controllers.home;

import java.util.Optional;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.user.User;

public final class HomeControllerImpl extends AbstractController implements HomeController {

    @Override
    public Optional<User> getFirstUser() {
        return this.getApplicationInstance().getFirstUser();
    }

    @Override
    public Optional<User> getSecondUser() {
        return this.getApplicationInstance().getSecondUser();
    }

}
