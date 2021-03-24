package jhaturanga.controllers.login;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

import jhaturanga.commons.validator.StringValidatorImpl;
import jhaturanga.commons.validator.StringValidatorImpl.ValidationResult;
import jhaturanga.commons.validator.StringValidators;
import jhaturanga.controllers.AbstractController;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.model.user.management.UsersManagerSingleton;

public final class LoginControllerImpl extends AbstractController implements LoginController {

    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MAX_USERNAME_LENGTH = 32;
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 16;

    private final Function<String, ValidationResult> passwordValidator;
    private final Function<String, ValidationResult> usernameValidator;

    public LoginControllerImpl() {

        this.passwordValidator = new StringValidatorImpl().add(StringValidators.NOT_EMPTY)
                .add(StringValidators.LONGER_THAN.apply(MIN_PASSWORD_LENGTH))
                .add(StringValidators.SHORTER_THAN.apply(MAX_PASSWORD_LENGTH)).build();

        this.usernameValidator = new StringValidatorImpl().add(StringValidators.NOT_EMPTY)
                .add(StringValidators.LONGER_THAN.apply(MIN_USERNAME_LENGTH))
                .add(StringValidators.SHORTER_THAN.apply(MAX_USERNAME_LENGTH))
                .add(StringValidators.DIFFERENT_FROM.apply(UsersManager.GUEST.getUsername())).build();
    }

    // TODO: REFACTOR
    private void loginUser(final User user) {
        if (this.getApplicationInstance().getFirstUser().isEmpty()
                || this.getApplicationInstance().getFirstUser().get().equals(UsersManager.GUEST)) {
            this.getApplicationInstance().setFirstUser(user);
        } else if (this.getApplicationInstance().getSecondUser().isPresent()
                && this.getApplicationInstance().getSecondUser().get().equals(UsersManager.GUEST)) {
            this.getApplicationInstance().setSecondUser(user);
        }
    }

    @Override
    public boolean login(final String username, final String password) {

        try {
            final Optional<User> user = UsersManagerSingleton.getInstance().login(username, password);
            if (user.isPresent()) {
                this.loginUser(user.get());
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public boolean register(final String username, final String password) {
        try {
            final Optional<User> user = UsersManagerSingleton.getInstance().register(username, password);
            if (user.isPresent()) {
                this.loginUser(user.get());
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void loginAsGuest() {
        this.getApplicationInstance().setFirstUser(UsersManager.GUEST);
        this.getApplicationInstance().setSecondUser(UsersManager.GUEST);
    }

    @Override
    public ValidationResult validatePassword(final String password) {
        return this.passwordValidator.apply(password);
    }

    @Override
    public ValidationResult validateUsername(final String username) {
        return this.usernameValidator.apply(username);
    }

}
