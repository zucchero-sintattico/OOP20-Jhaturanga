package jhaturanga.views.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jhaturanga.views.AbstractView;

public final class LoginViewImpl extends AbstractView implements LoginView {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @Override
    public void init() {

    }

    @FXML
    public void onLoginClick(final ActionEvent event) {
        final String username = this.usernameField.getText();
        final String password = this.passwordField.getText();
        System.out.println("Login with : username = " + username + " - password = " + password);
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
