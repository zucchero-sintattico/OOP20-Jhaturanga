package jhaturanga.views.login;

import static jhaturanga.commons.validator.StringValidatorImpl.ValidationResult.CORRECT;
import static jhaturanga.commons.validator.StringValidatorImpl.ValidationResult.EMPTY;
import static jhaturanga.commons.validator.StringValidatorImpl.ValidationResult.FORBIDDEN;
import static jhaturanga.commons.validator.StringValidatorImpl.ValidationResult.TOO_LONG;
import static jhaturanga.commons.validator.StringValidatorImpl.ValidationResult.TOO_SHORT;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jhaturanga.commons.validator.StringValidatorImpl;
import jhaturanga.commons.validator.StringValidatorImpl.ValidationResult;
import jhaturanga.controllers.login.LoginController;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class LoginViewImpl extends AbstractView implements LoginView {

    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_PASSWORD_LENGTH = 16;
    private static final int MAX_USERNAME_LENGTH = 32;

    private Function<String, ValidationResult> passwordValidator;
    private Function<String, ValidationResult> usernameValidator;

    // declaration of element from Fxml

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Text errorText;

    @Override
    public LoginController getLoginController() {
        return (LoginController) this.getController();
    }

    @FXML
    public void initialize() {
        this.passwordValidator = new StringValidatorImpl()
                .add(s -> s.length() != 0 ? CORRECT : EMPTY)
                .add(s -> s.length() <= MAX_PASSWORD_LENGTH ? CORRECT : TOO_LONG)
                .build();

        this.usernameValidator = new StringValidatorImpl()
                .add(s -> s.length() > MIN_USERNAME_LENGTH ? CORRECT : TOO_SHORT)
                .add(s -> s.length() <= MAX_USERNAME_LENGTH ? CORRECT : TOO_LONG)
                .add(s -> !s.equals(UsersManager.GUEST.getUsername()) ? CORRECT : FORBIDDEN)
                .build();
    }

    @FXML
    public void switchRegisterView(final Event event) throws IOException {
        PageLoader.switchPageWithSameController(this.getStage(), Pages.REGISTER, this.getController());
    }

    @FXML
    public void backToHome(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }

    @Override
    public void login(final Event event) {
        this.errorText.setText("");
        final ValidationResult passwordResult = this.passwordValidator.apply(passwordTextField.getText());

        if (passwordResult.equals(ValidationResult.CORRECT)) {
            if (!this.getLoginController().login(userNameTextField.getText(), passwordTextField.getText())
                    .equals(Optional.empty())) {
                try {
                    PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                errorText.setText("username o password errate");
            }
        } else {
            errorText.setText(passwordResult.getMessage() + " password");

        }

    }

    @Override
    public void register(final Event event) {
        this.errorText.setText("");
        final ValidationResult passwordResult = this.passwordValidator.apply(passwordTextField.getText());
        final ValidationResult usernameResult = this.usernameValidator.apply(userNameTextField.getText());

        if (usernameResult.equals(ValidationResult.CORRECT)) {
            if (passwordResult.equals(ValidationResult.CORRECT)) {
                this.getLoginController().register(userNameTextField.getText(), passwordTextField.getText());
                try {
                    PageLoader.switchPage(this.getStage(), Pages.LOGIN, this.getController().getModel());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                errorText.setText(passwordResult.getMessage() + " password");
            }
        } else {
            errorText.setText(usernameResult.getMessage() + " username");
        }

    }

    @FXML
    public void backToLogin(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.LOGIN, this.getController().getModel());

    }

    @FXML
    public void logAsGuest(final Event event) throws IOException {
        this.getLoginController().logGuestUser();
        this.getLoginController().logGuestUser();
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }

    @FXML
    public void backToHomeButtonClick(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
    }

    @Override
    public void init() {
        this.userNameTextField.setPromptText("Username");
        this.passwordTextField.setPromptText("Password");
    }

}
