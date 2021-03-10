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
            if (this.userManager.login(username, password).isPresent()) {
                if (this.getModel().getFirstUser().isPresent()
                        && this.getModel().getFirstUser().get().equals(UsersManager.GUEST)) {
                    this.getModel().setFirstUser(this.userManager.login(username, password).get());
                } else if (this.getModel().getSecondUser().isPresent()
                        && this.getModel().getSecondUser().get().equals(UsersManager.GUEST)) {
                    this.getModel().setSecondUser(this.userManager.login(username, password).get());
                }

                return Optional.of(this.userManager.login(username, password).get());
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
            return this.userManager.register(username, password);

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
