package jhaturanga.views.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import jhaturanga.commons.validator.StringValidatorImpl.ValidationResult;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class LoginViewImpl extends AbstractJavaFXView implements LoginView {

    @FXML
    private TextField usernameField;

    @FXML
    private Text usernameValidationInfo;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text passwordValidationInfo;

    @FXML
    private Text loginResultInfo;

    @FXML
    private Button logAsGuestButton;

    @Override
    public void init() {
//        this.getStage().setMinHeight(this.getStage().getHeight());
//        this.getStage().setMinWidth(this.getStage().getWidth());
        this.logAsGuestButton.requestFocus();
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

        if (this.validateCredentials(username, password)) {
            if (this.getLoginController().login(username, password)) {
                PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
            } else {
                this.loginResultInfo.setText("Username or Password incorrect");
            }
        }

    }

    @FXML
    public void onSignUpClick(final ActionEvent event) {
        final String username = this.usernameField.getText();
        final String password = this.passwordField.getText();

        if (this.validateCredentials(username, password)) {
            if (this.getLoginController().register(username, password)) {
                PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());
            } else {
                this.loginResultInfo.setText("Somethings went wrong...");
            }
        }
    }

    @FXML
    public void onLogAsGuestClick(final ActionEvent event) {
        this.getLoginController().loginAsGuest();
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getController().getApplicationInstance());

    }

}
