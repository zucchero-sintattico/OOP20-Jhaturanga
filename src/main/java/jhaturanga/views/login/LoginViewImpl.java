package jhaturanga.views.login;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.login.LoginController;
import jhaturanga.controllers.login.LoginControllerImpl;

public final class LoginViewImpl implements LoginView {

    private LoginController controller;

    @FXML
    void initialize() {
        try {
            this.controller = new LoginControllerImpl(this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        // TODO Auto-generated method stub

    }

    @Override
    public void register(final Event event) {
        // TODO Auto-generated method stub

    }

}
