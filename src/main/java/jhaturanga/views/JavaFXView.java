package jhaturanga.views;

import javafx.stage.Stage;

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
     * 
     */
    void init();
}
