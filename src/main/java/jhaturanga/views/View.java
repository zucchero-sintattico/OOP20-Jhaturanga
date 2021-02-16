package jhaturanga.views;

import javafx.stage.Stage;
import jhaturanga.controllers.Controller;

/**
 * The generic template for a View.
 *
 */
public interface View {

    /**
     * Get the actual controller of this view.
     * 
     * @return the instance of the controller attached to this view.
     */
    Controller getController();

    /**
     * Set the controller for this view.
     * 
     * @param controller - the controller to be attached to this view.
     */
    void setController(Controller controller);

    /**
     * Get the stage attached to the view.
     * 
     * @return the primary stage of the window
     */
    Stage getStage();

    /**
     * Set the stage attached to the view.
     * 
     * @param stage - the stage to be setted
     * 
     */
    void setStage(Stage stage);

}
