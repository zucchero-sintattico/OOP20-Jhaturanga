package jhaturanga.views;

import jhaturanga.controllers.Controller;

public class BasicView implements View {

    private Controller controller;

    @Override
    public final Controller getController() {
        return this.controller;
    }

    @Override
    public final void setController(final Controller controller) {
        this.controller = controller;
    }

}
