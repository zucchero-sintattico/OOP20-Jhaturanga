package jhaturanga.controllers.login;

import java.io.IOException;
import java.util.Optional;

import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.model.user.management.UsersManagerJsonImpl;
import jhaturanga.views.View;
import jhaturanga.views.login.LoginView;

public final class LoginControllerImpl implements LoginController {

    private UsersManager userManager;
    private final LoginView view;

    public LoginControllerImpl(final LoginView view) throws IOException {

        this.userManager = new UsersManagerJsonImpl();

        this.view = view;
    }

    @Override
    public View getView() {
        return this.view;
    }

    @Override
    public Optional<User> login(final String username, final String password) {
        try {
            return this.userManager.login(username, password);
        } catch (IOException e) {

            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> register(final String username, final String password) {
        return this.register(username, password);
    }

}
