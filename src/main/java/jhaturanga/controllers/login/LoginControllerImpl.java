package jhaturanga.controllers.login;

import java.io.IOException;
import java.util.Optional;

import jhaturanga.commons.datastorage.UsersDataStorageJsonImpl;
import jhaturanga.model.Model;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManagerFacade;
import jhaturanga.model.user.management.UsersManagerImpl;
import jhaturanga.views.View;
import jhaturanga.views.login.LoginView;

public final class LoginControllerImpl implements LoginController {

    private final UsersManagerFacade userManager;
    private final LoginView view;

    public LoginControllerImpl(final LoginView view) throws IOException {
        this.userManager = new UsersManagerImpl(new UsersDataStorageJsonImpl());
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
        try {
            return this.userManager.register(username, password);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();

        }
    }

    @Override
    public void setView(final View view) {
        // TODO Auto-generated method stub

    }

    @Override
    public Model getModel() {
        return null;
    }

}
