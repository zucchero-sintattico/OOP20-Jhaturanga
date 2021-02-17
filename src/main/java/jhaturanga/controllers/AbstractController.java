package jhaturanga.controllers;

import jhaturanga.model.Model;
import jhaturanga.pages.Page;
import jhaturanga.views.View;

public class AbstractController<T extends Page> implements Controller<T> {

    private View<T> view;
    private Model model;

    @Override
    public final View<T> getView() {
        return this.view;
    }

    @Override
    public final void setView(final View<T> view) {
        this.view = view;
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
