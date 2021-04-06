package jhaturanga.controllers.login;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

import jhaturanga.controllers.BasicController;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.model.user.management.UsersManagerSingleton;
import jhaturanga.model.user.validators.StringValidatorImpl;
import jhaturanga.model.user.validators.StringValidatorImpl.ValidationResult;
import jhaturanga.model.user.validators.StringValidators;

public final class LoginControllerImpl extends BasicController implements LoginController {

    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MAX_USERNAME_LENGTH = 32;
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 16;

    private final Function<String, ValidationResult> passwordValidator;
    private final Function<String, ValidationResult> usernameValidator;
    private final boolean firstUser;

    public LoginControllerImpl() {
        this(true);
    }

    public LoginControllerImpl(final boolean firstUser) {

        this.firstUser = firstUser;
        this.passwordValidator = new StringValidatorImpl().add(StringValidators.NOT_EMPTY)
                .add(StringValidators.LONGER_THAN.apply(MIN_PASSWORD_LENGTH))
                .add(StringValidators.SHORTER_THAN.apply(MAX_PASSWORD_LENGTH)).build();

        this.usernameValidator = new StringValidatorImpl().add(StringValidators.NOT_EMPTY)
                .add(StringValidators.LONGER_THAN.apply(MIN_USERNAME_LENGTH))
                .add(StringValidators.SHORTER_THAN.apply(MAX_USERNAME_LENGTH))
                .add(StringValidators.DIFFERENT_FROM.apply(UsersManager.GUEST.getUsername())).build();
    }

    private boolean loginUser(final User user) {
        if (this.firstUser) {
            this.getApplicationInstance().setFirstUser(user);
            if (this.getApplicationInstance().getSecondUser().isEmpty()) {
                this.getApplicationInstance().setSecondUser(UsersManager.GUEST);
            }
            return true;
        } else {
            if (!user.equals(this.getApplicationInstance().getFirstUser().get())) {
                this.getApplicationInstance().setSecondUser(user);
                return true;
            } else {
                return false;
            }

        }

    }

    @Override
    public boolean login(final String username, final String password) {

        try {
            final Optional<User> user = UsersManagerSingleton.getInstance().login(username, password);
            if (user.isPresent()) {
                return this.loginUser(user.get());
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
                return this.loginUser(user.get());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void loginAsGuest() {
        this.loginUser(UsersManager.GUEST);
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
