package jhaturanga.controllers.home;

import java.util.Optional;

import jhaturanga.controllers.BasicController;
import jhaturanga.model.user.User;

/**
 * Basic implementation for the HomeController.
 */
public final class HomeControllerImpl extends BasicController implements HomeController {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> getFirstUser() {
        return this.getModel().getFirstUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> getSecondUser() {
        return this.getModel().getSecondUser();
    }

}
