package jhaturanga.views.login;

import javafx.event.Event;
import jhaturanga.views.View;

/**
 * The view of the login page.
 */
public interface LoginView extends View {

    /**
     * This function is called when the user want to try the login. For example it
     * should be called when the user click on "login" button
     * 
     * @param event - the event that trigger the login call
     */
    void login(Event event);

    /**
     * This function is called when the user want to try the login. For example it
     * should be called when the user click on "login" button
     * 
     * @param event - the event that trigger the register call
     */
    void register(Event event);
}
