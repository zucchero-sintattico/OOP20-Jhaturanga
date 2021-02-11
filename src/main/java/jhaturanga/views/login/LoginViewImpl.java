package jhaturanga.views.login;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.login.LoginController;
import jhaturanga.controllers.login.LoginControllerImpl;

public final class LoginViewImpl implements LoginView {

    private LoginController controller;

    // declaration of element from fxml

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

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
        final URL url = new File("res/pages/register.fxml").toURI().toURL();
        final Parent realTimeView = FXMLLoader.load(url);
        final Scene realTimeScene = new Scene(realTimeView);
        final Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(realTimeScene);
        window.show();

    }

    @Override
    public Controller getController() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setController(final Controller controller) {
        // TODO Auto-generated method stub

    }

    @Override
    public Stage getStage() {
        // TODO Auto-generated method stub
        return null;
    }

    @FXML
    @Override
    public void login(final Event event) {
        System.out.println(this.controller.login(userNameTextField.getText(), passwordTextField.getText()));
    }

    @Override
    public void register(final Event event) {
        this.controller.register(userNameTextField.getText(), passwordTextField.getText());
    }

}
