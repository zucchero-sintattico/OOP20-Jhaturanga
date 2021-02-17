package jhaturanga.pages;

import jhaturanga.controllers.Controller;
import jhaturanga.controllers.login.LoginController;

public final class LoginPage implements Page {

    @Override
    public String getName() {
        return "Login";
    }

    @Override
    public Class<? extends Controller<? extends Page>> getControllerClass() {
        return LoginController.class;
    }

}
