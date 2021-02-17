package jhaturanga.controllers;

import jhaturanga.model.Model;

public class AbstractController<V> implements Controller<V> {

    private V view;
    private Model model;

    @Override
    public final V getView() {
        return this.view;
    }

    @Override
    public final void setView(final V view) {
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
