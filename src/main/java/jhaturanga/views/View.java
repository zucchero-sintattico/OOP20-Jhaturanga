package jhaturanga.views;

import javafx.stage.Stage;
import jhaturanga.controllers.Controller;
import jhaturanga.pages.Page;

/**
 * The generic template for a View.
 *
 * @param <T> the page of the view
 */
public interface View<T extends Page> {

    /**
     * Get the actual controller of this view.
     * 
     * @return the instance of the controller attached to this view.
     */
    Controller<T> getController();

    /**
     * Set the controller for this view.
     * 
     * @param controller - the controller to be attached to this view.
     */
    void setController(Controller<T> controller);

    /**
     * The view init Auto create the controller.
     * 
     */
    void init();

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
