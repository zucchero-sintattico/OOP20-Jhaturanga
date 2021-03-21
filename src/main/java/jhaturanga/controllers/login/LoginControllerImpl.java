package jhaturanga.controllers.login;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

import jhaturanga.commons.datastorage.UsersDataStorageJsonStrategy;
import jhaturanga.commons.validator.StringValidatorImpl;
import jhaturanga.commons.validator.StringValidatorImpl.ValidationResult;
import jhaturanga.commons.validator.StringValidators;
import jhaturanga.controllers.AbstractController;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.model.user.management.UsersManagerImpl;

public final class LoginControllerImpl extends AbstractController implements LoginController {

    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_PASSWORD_LENGTH = 16;
    private static final int MAX_USERNAME_LENGTH = 32;

    private UsersManager userManager;
    private Function<String, ValidationResult> passwordValidator;
    private Function<String, ValidationResult> usernameValidator;

    public LoginControllerImpl() {
        try {
            this.userManager = new UsersManagerImpl(new UsersDataStorageJsonStrategy());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.passwordValidator = new StringValidatorImpl().add(StringValidators.NOT_EMPTY)
                .add(StringValidators.SHORTER_THAN.apply(MAX_PASSWORD_LENGTH)).build();

        this.usernameValidator = new StringValidatorImpl().add(StringValidators.LONGER_THAN.apply(MIN_USERNAME_LENGTH))
                .add(StringValidators.SHORTER_THAN.apply(MAX_USERNAME_LENGTH))
                .add(StringValidators.DIFFERENT_FROM.apply(UsersManager.GUEST.getUsername())).build();
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

    @Override
    public ValidationResult validatePassword(final String password) {
        return this.passwordValidator.apply(password);
    }

    @Override
    public ValidationResult validateUsername(final String username) {
        return this.usernameValidator.apply(username);
    }

}
