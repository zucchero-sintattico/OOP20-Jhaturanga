package jhaturanga.views.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jhaturanga.commons.validator.StringValidatorImpl.ValidationResult;
import jhaturanga.views.AbstractView;

public final class LoginViewImpl extends AbstractView implements LoginView {

    @FXML
    private TextField usernameField;

    @FXML
    private Text usernameValidationInfo;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text passwordValidationInfo;

    @Override
    public void init() {

    }

    @FXML
    public void onLoginClick(final ActionEvent event) {
        final String username = this.usernameField.getText();
        final String password = this.passwordField.getText();

        final ValidationResult usernameResult = this.getLoginController().validateUsername(username);
        final ValidationResult passwordResult = this.getLoginController().validatePassword(password);
        if (!usernameResult.equals(ValidationResult.CORRECT) || !passwordResult.equals(ValidationResult.CORRECT)) {
            this.usernameValidationInfo
                    .setText(!usernameResult.equals(ValidationResult.CORRECT) ? usernameResult.getMessage() : "");
            this.passwordValidationInfo
                    .setText(!passwordResult.equals(ValidationResult.CORRECT) ? passwordResult.getMessage() : "");
        } else {
            // Make the login here.
            System.out.println("Login with : username = " + username + " - password = " + password);
        }

    }

    @FXML
    public void onSignUpClick(final ActionEvent event) {
        final String username = this.usernameField.getText();
        final String password = this.passwordField.getText();
        System.out.println("SignUp with : username = " + username + " - password = " + password);
    }

    @FXML
    public void onLogAsGuestClick(final ActionEvent event) {
        System.out.println("logasguest");
    }

}
