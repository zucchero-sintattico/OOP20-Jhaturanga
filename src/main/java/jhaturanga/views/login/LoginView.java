package jhaturanga.views.login;

import jhaturanga.controllers.login.LoginController;
import jhaturanga.views.JavaFXView;

public interface LoginView extends JavaFXView {

    /**
     * Get the login controller.
     * 
     * @return the login controller
     */
    default LoginController getLoginController() {
        return (LoginController) this.getController();
    }
}
