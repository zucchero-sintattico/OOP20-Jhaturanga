package jhaturanga.views;

import javafx.stage.Stage;
import jhaturanga.controllers.Controller;
import jhaturanga.pages.Page;

public abstract class AbstractView<T extends Page> implements View<T> {

    private Controller<T> controller;
    private Stage stage;

    @Override
    public final Controller<T> getController() {
        return this.controller;
    }

    @Override
    public final void setController(final Controller<T> controller) {
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
