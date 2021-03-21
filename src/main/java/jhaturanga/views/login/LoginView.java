package jhaturanga.views.login;

import jhaturanga.controllers.login.LoginController;
import jhaturanga.views.View;

public interface LoginView extends View {

    /**
     * Get the login controller.
     * 
     * @return the login controller
     */
    default LoginController getLoginController() {
        return (LoginController) this.getController();
    }
}
