package jhaturanga.views.login;

import static jhaturanga.commons.validator.ValidatorBuilder.ValidationResult.CORRECT;

import java.io.IOException;
import java.util.function.Function;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jhaturanga.commons.validator.ValidatorBuilder.ValidationResult;
import jhaturanga.commons.validator.ValidatorBuilderImpl;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.login.LoginController;
import jhaturanga.controllers.login.LoginControllerImpl;
import jhaturanga.model.user.management.UsersManager;
import jhaturanga.views.PageLoader;

public final class LoginViewImpl implements LoginView {

    private Stage stage;

    private LoginController controller;
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

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public void setController(final Controller controller) {
        this.controller = (LoginController) controller;
    }

    @Override
    public void setStage(final Stage stage) {
        this.stage = stage;

    }

    @Override
    public Stage getStage() {
        return this.stage;
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

    @FXML
    public void logAsGuest(final Event event) throws IOException {
<<<<<<< HEAD
        PageLoader.switchPage(this.getStage(), "mainMenu");
=======
        PageLoader.switchPage(this.getStage(), "chessboard");
>>>>>>> develop
    }

}
