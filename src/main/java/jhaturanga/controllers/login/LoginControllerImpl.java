package jhaturanga.controllers.login;

import java.io.IOException;
import java.util.Optional;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.model.user.management.UsersManagerSingleton;

public final class LoginControllerImpl extends AbstractController implements LoginController {


    @Override
    public Optional<User> login(final String username, final String password) {

        try {
            if (UsersManagerSingleton.getInstance().login(username, password).isPresent()) {
                if (this.getModel().getFirstUser().isPresent()
                        && this.getModel().getFirstUser().get().equals(UsersManager.GUEST)) {
                    this.getModel().setFirstUser(UsersManagerSingleton.getInstance().login(username, password).get());
                } else if (this.getModel().getSecondUser().isPresent()
                        && this.getModel().getSecondUser().get().equals(UsersManager.GUEST)) {
                    this.getModel().setSecondUser(UsersManagerSingleton.getInstance().login(username, password).get());
                }

                return Optional.of(UsersManagerSingleton.getInstance().login(username, password).get());
            }
        } catch (IOException e) {

            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.empty();

    }

    @Override
    public Optional<User> register(final String username, final String password) {
        try {
            return UsersManagerSingleton.getInstance().register(username, password);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();

        }
    }

    @Override
    public void logGuestUser() {
        this.getModel().setFirstUser(UsersManager.GUEST);
        this.getModel().setSecondUser(UsersManager.GUEST);
    }

}
