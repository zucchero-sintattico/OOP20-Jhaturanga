package jhaturanga.views;

import javafx.stage.Stage;
import jhaturanga.controllers.Controller;

public abstract class AbstractView implements View {

    private Stage stage;
    private Controller controller;

    @Override
    public final Controller getController() {
        return this.controller;
    }

    @Override
    public final void setController(final Controller controller) {
        this.controller = controller;
    }

    @Override
    public final Stage getStage() {
        return this.stage;
    }

    @Override
    public final void setStage(final Stage stage) {
        this.stage = stage;
    }

}
