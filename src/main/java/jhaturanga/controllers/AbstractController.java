package jhaturanga.controllers;

import jhaturanga.model.Model;
import jhaturanga.views.View;

public abstract class AbstractController implements Controller {

    private View view;
    private Model model;

    @Override
    public final void setView(final View view) {
        this.view = view;
    }

    @Override
    public final View getView() {
        return this.view;
    }

    @Override
    public final Model getModel() {
        return this.model;
    }

    @Override
    public final void setModel(final Model model) {
        this.model = model;
    }

}
