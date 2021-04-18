package jhaturanga.views;

import javafx.stage.Stage;

/**
 * The template for a view used by JavaFX.
 */
public interface JavaFXView extends View {

    /**
     * Get the stage attached to the view.
     * 
     * @return the primary stage of the window
     */
    Stage getStage();

    /**
     * Set the stage attached to the view.
     * 
     * @Override
     * 
     * @param stage - the stage to be setted
     * 
     */
    void setStage(Stage stage);

    /**
     * The initialize method for setup the view before showing it.
     */
    void init();
}
