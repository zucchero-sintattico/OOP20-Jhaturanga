package jhaturanga.controllers.login;

import java.io.IOException;
import java.util.Optional;

import jhaturanga.commons.datastorage.UsersDataStorageJsonStrategy;
import jhaturanga.controllers.AbstractController;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.model.user.management.UsersManagerImpl;

public final class LoginControllerImpl extends AbstractController implements LoginController {

    private final UsersManager userManager;

    public LoginControllerImpl() throws IOException {
        this.userManager = new UsersManagerImpl(new UsersDataStorageJsonStrategy());
    }

    @Override
    public Optional<User> login(final String username, final String password) {
        try {
            final Optional<User> user = this.userManager.login(username, password);

            if (user.isPresent()) {
                this.getModel().addLoggedUser(user.get());
            }

            return user;
        } catch (IOException e) {

            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> register(final String username, final String password) {
        try {
            final Optional<User> user = this.userManager.register(username, password);

            if (user.isPresent()) {
                this.getModel().addLoggedUser(user.get());
            }

            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();

        }
    }

    @Override
    public void logGuestUser() {
        this.getModel().addLoggedUser(UsersManager.GUEST);

    }

}
