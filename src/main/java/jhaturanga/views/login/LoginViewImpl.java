package jhaturanga.views.login;

import static jhaturanga.commons.validator.ValidatorBuilder.ValidationResult.CORRECT;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jhaturanga.commons.validator.ValidatorBuilder.ValidationResult;
import jhaturanga.commons.validator.ValidatorBuilderImpl;
import jhaturanga.controllers.login.LoginController;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.pages.PageLoader;
import jhaturanga.pages.Pages;
import jhaturanga.views.AbstractView;

public final class LoginViewImpl extends AbstractView implements LoginView {

    private Function<String, ValidationResult> passwordValidator;
    private Function<String, ValidationResult> usernameValidator;

    // declaration of element from fxml

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
        this.passwordValidator = new ValidatorBuilderImpl().notEmpty().notShortedThan(3).notLongerThan(16).build();

        this.usernameValidator = new ValidatorBuilderImpl().notEmpty()
                // .notShortedThan(5)
                .notLongerThan(32).forbid(UsersManager.GUEST.getUsername()).build();
    }

    @FXML
    public void switchRegisterView(final Event event) throws IOException {
        PageLoader.switchPageWithSameController(this.getStage(), Pages.REGISTER, this.getController());
    }

    @FXML
    @Override
    public void login(final Event event) {

        errorText.setText("");
        final ValidationResult passwordResult = this.passwordValidator.apply(passwordTextField.getText());

        if (passwordResult == CORRECT) {
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
        errorText.setText("");
        final ValidationResult passwordResult = this.passwordValidator.apply(passwordTextField.getText());
        final ValidationResult usernameResult = this.usernameValidator.apply(userNameTextField.getText());

        if (usernameResult == CORRECT) {
            if (passwordResult == CORRECT) {
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
        // TODO Auto-generated method stub

    }

}
