package jhaturanga.views.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jhaturanga.commons.validator.StringValidatorImpl.ValidationResult;
import jhaturanga.views.AbstractView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

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

    private boolean validateCredentials(final String username, final String password) {

        final ValidationResult usernameResult = this.getLoginController().validateUsername(username);
        final ValidationResult passwordResult = this.getLoginController().validatePassword(password);

        this.usernameValidationInfo
                .setText(!usernameResult.equals(ValidationResult.CORRECT) ? usernameResult.getMessage() : "");
        this.passwordValidationInfo
                .setText(!passwordResult.equals(ValidationResult.CORRECT) ? passwordResult.getMessage() : "");

        return usernameResult.equals(ValidationResult.CORRECT) && passwordResult.equals(ValidationResult.CORRECT);
    }

    @FXML
    public void onLoginClick(final ActionEvent event) {

        final String username = this.usernameField.getText();
        final String password = this.passwordField.getText();

        if (this.validateCredentials(username, password)
                && this.getLoginController().login(username, password).isPresent()) {
            PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
        }

    }

    @FXML
    public void onSignUpClick(final ActionEvent event) {
        final String username = this.usernameField.getText();
        final String password = this.passwordField.getText();

        if (this.validateCredentials(username, password)
                && this.getLoginController().register(username, password).isPresent()) {
            PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getModel());
        }
    }

    @FXML
    public void onLogAsGuestClick(final ActionEvent event) {
        System.out.println("logasguest");
    }

}
