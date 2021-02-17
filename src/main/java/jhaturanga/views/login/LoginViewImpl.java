package jhaturanga.views.login;

ValidatorBuilder.ValidationResult;

import static jhaturanga.commons.validator.Validat

import java.io.IOException;
import java.util.function.Function;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;port jhaturanga.views.AbstractView;
import jhaturanga.commons.validator
import jhaturanga.commons.validator.ValidatorBuilderImpl;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.login.LoginController;
import jhaturanga.controllers.login.LoginControllerImpl;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.pages.LoginPage;
import jhaturanga.pages.PageLoader;
import jhaturanga.views.AbstractView;

public final class LoginViewImpl extends AbstractView<LoginPage> implements LoginView {

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
    public void init() {
        this.setController(new LoginControllerImpl());
        
        this.getController().
    }

    /*
     * TODO cambiare il metodo di switch delle finestre, e implementere quindi set e
     * get controller
     */

    @FXML
    void initialize() {
        this.passwordValidator = new ValidatorBuilderImpl().notEmpty().notShortedThan(3).notLongerThan(16).build();

        this.usernameValidator = new ValidatorBuilderImpl().notEmpty()
                // .notShortedThan(5)
                .notLongerThan(32).forbid(UsersManager.GUEST.getUsername()).build();

        try {
            this.controller = new LoginControllerImpl(this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void switchRegisterView(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), "register");
    }

    @FXML
    @Override
    public void login(final Event event) {
        errorText.setText("");
        final ValidationResult passwordResult = this.passwordValidator.apply(passwordTextField.getText());

        if (passwordResult == CORRECT) {
            this.controller.login(userNameTextField.getText(), passwordTextField.getText());
            System.out.println("accesso consentito");
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
                this.controller.register(userNameTextField.getText(), passwordTextField.getText());
                try {
                    PageLoader.switchPage(this.getStage(), "login");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
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
        PageLoader.switchPage(this.getStage(), "login");

    }

    @FXML
    public void settingButton(final Event event) throws IOException {
        PageLoader.switchPage(this.getStage(), "settings");
    }

}
