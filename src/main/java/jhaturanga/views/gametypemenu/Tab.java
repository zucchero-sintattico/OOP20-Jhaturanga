package jhaturanga.views.gametypemenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface Tab {

    /**
     * 
     * @param text show in the button
     */
    void setButtonText(String text);

    /**
     * 
     * @param text show in the description of tab
     */
    void setDescription(String text);

    /**
     * 
     * @param event when click button
     */
    void setButtonAction(EventHandler<ActionEvent> event);

}
