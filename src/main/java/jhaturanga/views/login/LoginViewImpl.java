package jhaturanga.views.login;

import java.io.IOException;
import java.util.Optional;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.login.LoginController;
import jhaturanga.controllers.login.LoginControllerImpl;
import jhaturanga.views.PageLoader;

public final class LoginViewImpl implements LoginView {

    private LoginController controller;

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
        try {
            this.controller = new LoginControllerImpl(this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void switchRegisterView(final Event event) throws IOException {
        PageLoader.switchPage(getStage(event), "register");
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public void setController(final Controller controller) {
    }

    @Override
    public Stage getStage(final Event event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    @FXML
    @Override
    public void login(final Event event) {
        if (this.controller.login(userNameTextField.getText(), passwordTextField.getText()).equals(Optional.empty())) {
            errorText.setText("Username o Password Errati");
        } else {
            System.out.println("accesso consentito");
        }
    }

    @Override
    public void register(final Event event) {
        this.controller.register(userNameTextField.getText(), passwordTextField.getText());
        try {
            PageLoader.switchPage(getStage(event), "login");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
