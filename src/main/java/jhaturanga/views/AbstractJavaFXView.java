package jhaturanga.views;

import javafx.stage.Stage;

/**
 * Basic abstract implementation of the JavaFX view.
 *
 */
public abstract class AbstractJavaFXView extends BasicView implements JavaFXView {

    private Stage stage;

    /**
     * {@inheritDoc}
     */
    @Override
    public final Stage getStage() {
        return this.stage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setStage(final Stage stage) {
        this.stage = stage;
    }

}
