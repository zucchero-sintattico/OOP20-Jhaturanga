package jhaturanga.controllers.login;

import java.io.IOException;
import java.util.function.Function;

import jhaturanga.controllers.BasicController;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.model.user.management.UsersManagerSingleton;
import jhaturanga.model.user.validators.StringValidatorImpl;
import jhaturanga.model.user.validators.StringValidatorImpl.ValidationResult;
import jhaturanga.model.user.validators.StringValidators;

/**
 * Basic implementation of the LoginController.
 */
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
                .add(StringValidators.SHORTER_THAN.apply(MAX_PASSWORD_LENGTH)).create();

        this.usernameValidator = new StringValidatorImpl().add(StringValidators.NOT_EMPTY)
                .add(StringValidators.LONGER_THAN.apply(MIN_USERNAME_LENGTH))
                .add(StringValidators.SHORTER_THAN.apply(MAX_USERNAME_LENGTH))
                .add(StringValidators.DIFFERENT_FROM.apply(UsersManager.GUEST.getUsername())).create();
    }

    /**
     * Effectively login the user.
     * 
     * @param user
     * @return true if the user was logged, false otherwise.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean login(final String username, final String password) {
        try {
            return UsersManagerSingleton.getInstance().login(username, password).map(this::loginUser).orElse(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean register(final String username, final String password) {
        try {
            return UsersManagerSingleton.getInstance().register(username, password).map(this::loginUser).orElse(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loginAsGuest() {
        this.loginUser(UsersManager.GUEST);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validatePassword(final String password) {
        return this.passwordValidator.apply(password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValidationResult validateUsername(final String username) {
        return this.usernameValidator.apply(username);
    }

}
