package jhaturanga.views;

import javafx.stage.Stage;

public abstract class AbstractJavaFXView extends BasicView implements JavaFXView {

    private Stage stage;

    @Override
    public final Stage getStage() {
        return this.stage;
    }

    @Override
    public final void setStage(final Stage stage) {
        this.stage = stage;
    }

}
